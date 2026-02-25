package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class US35_TableHeaderUI extends ModifyPolicyBaseTest{

    By searchBtnLocator = By.id("ContentPlaceHolder_Admin_btnSearch"); // Search button from image_b08018.png

    By tableLocator = By.id("ContentPlaceHolder_Admin_gvPolicies");
    By tableHeadersLocator = By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//th");

    @Test(priority = 1)
    public void tableDisplayed(){
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBtnLocator));
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

        // Assert visibility
        Assert.assertTrue(table.isDisplayed(), "Validation Failed: Policy table is not displayed.");
    }


    @Test(priority = 2,dependsOnMethods = {"tableDisplayed"})
    public void tablePosition(){
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBtnLocator));
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

        // Compare Y-coordinates to ensure table is below the search button
        int searchBtnY = searchBtn.getLocation().getY();
        int tableY = table.getLocation().getY();
        Assert.assertTrue(tableY > searchBtnY, "Validation Failed: Table is not displayed below the search section.");
        System.out.println("Success: Table is visible and correctly positioned below the search section.");
    }


    @Test(priority = 3,dependsOnMethods = {"tableDisplayed"})
    public void tableHeaders() {
        //  Validate exactly 9 columns (Fetch, Count, and Print)
        List<WebElement> headers = driver.findElements(tableHeadersLocator);

        // Print and validate the count directly from DOM elements
        int columnCount = headers.size();
        System.out.println("Number of table column headers found in DOM: " + columnCount);
        Assert.assertEquals(columnCount, 9, "Validation Failed: Table does not contain exactly 9 columns.");

        // Validate Header Order with Horizontal Scrolling
        List<String> actualHeaderNames = new ArrayList<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Header mapping based on DOM screenshot
        List<String> expectedHeaders = Arrays.asList(
                "Policy ID", "Main Category", "Sub Category", "Policy Name",
                "Sum Assured", "Premium", "Tenure (Years)", "Status", "Actions"
        );
        for (WebElement header : headers) {
            // Scroll horizontally to the header
            js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'start'});", header);

            String headerText = header.getText().trim();
            // JS fallback for hidden text
            if (headerText.isEmpty()) {
                headerText = (String) js.executeScript("return arguments[0].textContent;", header);
            }
            actualHeaderNames.add(headerText.trim());
        }

        System.out.println("Actual Headers Found: " + actualHeaderNames);
        Assert.assertEquals(actualHeaderNames, expectedHeaders, "Column headers order mismatch.");


    }



}
