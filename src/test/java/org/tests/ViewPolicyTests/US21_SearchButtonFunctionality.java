package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US21_SearchButtonFunctionality extends ViewPolicyBaseTest {

    String ele1, ele2, ele3, ele4;
    String out1, out2, out3, out4;
    boolean listGenerated = false;


    @Test(priority = 1, description = "Task-1: Filter Selection and Search Action")
    public void applyFiltersAndSearch() throws InterruptedException {
        // 1. Main Category
        WebElement mainCat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainCat.click();
        List<WebElement> list1 = driver.findElements(By.xpath("//li[@role=\"option\"]"));
        ele1 = list1.get(8).getText();
        list1.get(8).click();

        // 2. Sub Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> list2 = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]"));
        ele2 = list2.get(0).getText();
        list2.get(0).click();

        // 3. Policy Name
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='select2-ContentPlaceHolder_Admin_ddlPolicyName-results']/li")));
        List<WebElement> list3 = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]"));
        ele3 = list3.get(0).getText();
        list3.get(0).click();

        // 4. Policy Status
        driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlPolicyStatus-results\"]")));
        List<WebElement> list4 = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]"));
        ele4 = list4.get(0).getText();
        list4.get(0).click();

        // Click Search
        driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 2, dependsOnMethods = "applyFiltersAndSearch", description = "Task-2: Capture Table Data")
    public void captureTableData() {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody")));
        List<WebElement> table_list = table.findElements(By.tagName("tr"));

        WebElement messageRow = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]"));
        String messageText = messageRow.getText().trim();

        if(table_list.size() > 1 && messageText.equals(ele1)) {
            listGenerated = true;
            System.out.println("The list is generated.");

            out1 = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]")).getText();
            out2 = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[3]")).getText();
            out3 = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[4]")).getText();
            out4 = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[8]")).getText();
        } else {
            System.out.println("There is no record for the input values.");
            Assert.fail("No records found in table.");
        }
    }

    @Test(priority = 3, dependsOnMethods = "captureTableData", description = "Task-3: Validate Search Results Against Filters")
    public void validateSearchResults() {
        if(listGenerated) {
            // Assertions provide a clear report of which specific field failed
            Assert.assertTrue(out1.equalsIgnoreCase(ele1), "Main category mismatch! Filter: " + ele1 + " Table: " + out1);
            Assert.assertTrue(out2.equalsIgnoreCase(ele2), "Sub category mismatch!");
            Assert.assertTrue(out3.equalsIgnoreCase(ele3), "Policy name mismatch!");
            Assert.assertTrue(out4.equalsIgnoreCase(ele4), "Policy status mismatch!");

            System.out.println("The Table Details are matching with the filter values.");
        }
    }

}
