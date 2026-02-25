package org.tests.ViewPolicyTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class US09_EmptyTableUI extends ViewPolicyBaseTest{


    @Test(priority = 1)
    public void verifyNoRecordsMessage() throws InterruptedException {

        // 1. Select Main Category
        WebElement mainSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainSelect.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> eleList1 = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        String val1 = eleList1.get(0).getText();
        eleList1.get(0).click();


        // 2. Select Sub Category
        WebElement subCategorybox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul")));
        subCategorybox.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> eleList2 = driver.findElements(By.xpath("//li[@class=\"select2-results__option select2-results__option--highlighted\"]"));
        String val2 = eleList2.get(0).getText();
        eleList2.get(0).click();


        // 3. Select Policy Name
        WebElement policyNamebox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span")));
        policyNamebox.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='select2-ContentPlaceHolder_Admin_ddlPolicyName-results']/li")));
        List<WebElement> eleList3 = driver.findElements(By.xpath("//li[@class=\"select2-results__option select2-results__option--highlighted\"]"));
        eleList3.get(0).click();

        // 4. Select Policy Status
        WebElement policyStatus = driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul"));
        policyStatus.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlPolicyStatus-results\"]")));
        List<WebElement> eleList4 = driver.findElements(By.xpath("//li[@class=\"select2-results__option select2-results__option--highlighted\"]"));
        eleList4.get(0).click();

        // 5. Click Search and Verify Message
        WebElement search_button = driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch"));
        search_button.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]")));
        String actualMessage = message.getText();

        // Assertion: Instead of if-else, we use Assert to validate the test outcome
        Assert.assertTrue(actualMessage.equals(val1), "Expected a message row after search 'No Records Found'.");
        System.out.println("Search result row text: " + actualMessage);
    }

}
