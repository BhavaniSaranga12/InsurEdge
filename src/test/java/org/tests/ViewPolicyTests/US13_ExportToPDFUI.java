package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US13_ExportToPDFUI extends ViewPolicyBaseTest {

    @Test(priority = 1)
    public void verifyButtonPosition() {
        WebElement excelBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportExcel"));

        WebElement pdfBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportPDF"));

        int excelX = excelBtn.getLocation().getX();
        int pdfX = pdfBtn.getLocation().getX();

        Assert.assertTrue(pdfX > excelX, "Export to PDF button should be displayed to the right of Export to Excel button");
    }

    @Test(priority = 2)
    public void verifyButtonColor() {
        WebElement pdfBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportPDF"));

        String bgColor = pdfBtn.getCssValue("background-color");
        String hexColor = Color.fromString(bgColor).asHex();

        Assert.assertEquals(hexColor, "#dc3545", "Export to PDF button should have red background");
    }

    @Test(priority = 3)
    public void verifyButtonEnabledWithRecords() {

        WebElement table = driver.findElement(By.id("ContentPlaceHolder_Admin_gvPolicies"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        WebElement pdfBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportPDF"));

        Assert.assertTrue(rows.size() > 1, "Table should have records");
        Assert.assertTrue(pdfBtn.isEnabled(), "Export to PDF button should be enabled when records exist");
    }

    @Test(priority = 4)
    public void verifyButtonDisabledWithoutRecords() throws InterruptedException {

        // 1) Main Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        driver.findElements(By.xpath("//li[@role=\"option\"]")).get(0).click();

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

        // Capture data for Method 3
        List<WebElement> rec = driver.findElements(By.tagName("tr"));
        int recordCount = rec.size();

        WebElement messageRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[1]")));
        String messageText = messageRow.getText().trim();

        WebElement pdfBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportPDF"));
        boolean isEnabled = pdfBtn.isEnabled();

        // Refactored If-Else logic with TestNG Assertions
        if (recordCount > 1 && messageText.equals("&nbsp;")) {
            if (isEnabled) {
                System.out.println("The Export to CSV button is Enabled.");
            } else {
                System.out.println("The Export to CSV button is not Enabled.");
                Assert.fail("CSV button should be enabled for existing records.");
            }
        } else {
            if (isEnabled) {
                System.out.println("The Button should not be Enabled and it is a Defect.");
                Assert.fail("Defect: Export button enabled without records.");
            } else {
                System.out.println("The Button is not Enabled (Correct Behavior).");
            }
        }

    }

}





