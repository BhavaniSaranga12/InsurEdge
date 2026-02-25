package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class US30_SubCategoryUI extends ModifyPolicyBaseTest {
    @Test(priority = 1)
    public void validateSubCategoryUI() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement mainCategoryLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='col-md-3']/label[contains(text(),'Main Category:')]")));
        WebElement subCategoryLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='col-md-3']/label[contains(text(),'Sub Category:')]")));

        int mainCategoryX = mainCategoryLabel.getLocation().getX();
        int subCategoryX = subCategoryLabel.getLocation().getX();
        Assert.assertTrue(subCategoryX > mainCategoryX,
                "Sub Category label is not to the right of Main Category label");

        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("ContentPlaceHolder_Admin_ddlSubCategory")));
        int subCategoryY = subCategoryLabel.getLocation().getY();
        int dropdownY = dropdown.getLocation().getY();
        Assert.assertTrue(dropdownY > subCategoryY,
                "Dropdown is not positioned below Sub Category label");

        String defaultValue = getSelectedOptionText(wait, By.id("ContentPlaceHolder_Admin_ddlSubCategory"));
        Assert.assertEquals(defaultValue, "All", "Default value is not 'All'");


        wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_ddlSubCategory"))).click();
        Select select = new Select(driver.findElement(By.id("ContentPlaceHolder_Admin_ddlSubCategory")));
        select.selectByIndex(1); // Select second option (index 1)
        String selectedValue = getSelectedOptionText(wait, By.id("ContentPlaceHolder_Admin_ddlSubCategory"));
        Assert.assertNotEquals(selectedValue, "All", "Dropdown did not allow selecting a new value");


        Assert.assertEquals(selectedValue, getSelectedOptionText(wait, By.id("ContentPlaceHolder_Admin_ddlSubCategory")),
                "Selected value is not displayed inside the dropdown");

        System.out.println("Sub Category UI validations passed successfully!");
    }

    @Test(priority = 2)
    private String getSelectedOptionText(WebDriverWait wait, By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                Select select = new Select(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
                return select.getFirstSelectedOption().getText();
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Element kept going stale after retries");
    }
}
