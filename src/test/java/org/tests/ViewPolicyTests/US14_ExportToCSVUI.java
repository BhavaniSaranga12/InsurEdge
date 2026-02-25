package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US14_ExportToCSVUI extends ViewPolicyBaseTest{

    int recordCount;
    String messageText;
    String ele1;

    @Test(priority = 1, description = "Apply filters and perform search")
    public void performSearch() throws InterruptedException {
        // Navigate
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"sidebar-nav\"]/li[4]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tables-nav\"]/li[3]/a/span"))).click();

        // 1) Main Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> list1 = driver.findElements(By.xpath("//li[@role=\"option\"]"));
        ele1 = list1.get(0).getText(); // Storing the text for validation later
        list1.get(0).click();

        // 2) Sub Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]")).get(0).click();
        Thread.sleep(1000);

        // 3) Policy Name
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='select2-ContentPlaceHolder_Admin_ddlPolicyName-results']/li"))).click();
        Thread.sleep(1000);

        // 4) Policy Status
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select2-ContentPlaceHolder_Admin_ddlPolicyStatus-results")));
        driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]")).get(0).click();

        // 5) Search Action
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_btnSearch")));
        searchBtn.click();

        // Capture data for validation method
        List<WebElement> rec = driver.findElements(By.tagName("tr"));
        recordCount = rec.size();

        WebElement messageRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]")));
        messageText = messageRow.getText().trim();
    }

    @Test(priority = 2, dependsOnMethods = "performSearch", description = "Validate Export to CSV button UI")
    public void validateCSVButton() {
        WebElement csvBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportCSV"));
        boolean isEnabled = csvBtn.isEnabled();

        // Comparison logic using class-level variables
        if (recordCount > 1 && messageText.equals(ele1)) {
            if (isEnabled) {
                System.out.println("The Export to CSV button is Enabled.");
            } else {
                Assert.fail("CSV button should be enabled for existing records.");
            }
        } else {
            if (isEnabled) {
                Assert.fail("Defect: Export button enabled without records.");
            } else {
                System.out.println("The Button is not Enabled (Correct Behavior).");
            }
        }
    }
}
