package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US22_ResetButtonFunctionality extends ViewPolicyBaseTest {

    By mainCategoryInput = By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li/input");                // mainCategoryInput tag


    By subCategoryInput = By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li/input");                // mainCategoryInput tag

    By policyNameButton = By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul/li/input");            // policy name textbox input tag


    By policyStatusButton = By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/input");

    By searchbtn = By.xpath("//input[contains(@id,\"btnSearch\")]");

    By resetButtonView = By.xpath("//*[@id=\"ContentPlaceHolder_Admin_btnReset\"]");

    public void addValues() {
        wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();
        List<WebElement> ls = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(ls.get(0))).click();
        //for sub category
        wait.until(ExpectedConditions.elementToBeClickable(subCategoryInput)).click();
        List<WebElement> ls2 = driver.findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(ls2.get(0))).click();
        //for policy name
        wait.until(ExpectedConditions.elementToBeClickable(policyNameButton)).click();
        List<WebElement> ls3 = driver.findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(ls3.get(0))).click();
        //for policy status
        wait.until(ExpectedConditions.elementToBeClickable(policyStatusButton)).click();
        List<WebElement> ls4 = driver.findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(ls4.get(0))).click();

        driver.findElement(searchbtn).click();
    }

    @Test(priority = 1)
    void resetButtonValidation() {
        addValues();

        driver.findElement(resetButtonView).click();
    }

    @Test(priority = 2)
    void defaultStateValidation() {

        // Click reset button
        driver.findElement(resetButtonView).click();

        // Re-locate elements after reset
        String mainText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='form1']/div[3]/div[1]/span/span[1]/span/ul/li[1]"))).getText();

        String subText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='form1']/div[3]/div[2]/span/span[1]/span/ul/li[1]"))).getText();

        String nameText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='form1']/div[3]/div[3]/span/span[1]/span/ul/li[1]"))).getText();

        String statusText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='form1']/div[3]/div[4]/span/span[1]/span/ul/li[1]"))).getText();

        System.out.println("Main: " + mainText);
        System.out.println("Sub: " + subText);
        System.out.println("Name: " + nameText);
        System.out.println("Status: " + statusText);

        // Validation
        if (mainText.isEmpty() && subText.isEmpty() && nameText.isEmpty() && statusText.isEmpty()) {
            Assert.assertTrue(true, "All dropdowns are empty after reset.");
        } else {
            Assert.fail("One or more dropdowns are not empty after reset.");
        }
    }


    @Test(priority = 3)
    void tableValidation() {

        // Reset first
        driver.findElement(resetButtonView).click();

        // Wait until table rows are present
        List<WebElement> initialRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr")));
        int tableRows = initialRows.size();
        System.out.println("Initial rows: " + tableRows);

        // Add values (your custom method)
        addValues();

        // Reset again
        driver.findElement(resetButtonView).click();

        // Wait again for table rows to reload
        List<WebElement> updatedRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr")));
        int updatedTableRows = updatedRows.size();
        System.out.println("Updated rows: " + updatedTableRows);

        // Assert that row count is unchanged
        Assert.assertEquals(tableRows, updatedTableRows,
                "Row count mismatch after reset — table did not return to default state.");
    }
}