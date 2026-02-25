package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US37_TableDataUI extends ModifyPolicyBaseTest{
    int col_count;
    @Test(priority = 1, description = "Task 1: Validate Table Presence and Column Count")
    public void validateTableStructure() {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*/table[@class=\"table table-bordered table-hover shadow-sm\"]")));
        Assert.assertTrue(table.isDisplayed(), "The Table is not Displayed.");
        System.out.println("The Table is Displayed.");

        List<WebElement> header_list = driver.findElements(By.tagName("th"));
        col_count = header_list.size();

        Assert.assertEquals(col_count, 9, "The Columns are incorrect! Found: " + col_count);
        System.out.println("The Column Count is: " + col_count);
    }

    @Test(priority = 2, dependsOnMethods = "validateTableStructure", description = "Task 2: Validate Cell Data and Action Buttons")
    public void validateCellContent() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int emptyCellCount = 0;
        int buttonPresenceCount = 0;

        List<WebElement> row_count = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr"));

        // Iterate through rows (skipping header)
        for (int i = 2; i <= row_count.size() - 1; i++) {
            for (int j = 1; j < col_count; j++) {
                String cellXpath = String.format("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[%d]/td[%d]", i, j);
                WebElement cell = driver.findElement(By.xpath(cellXpath));
                String value = cell.getText();

                // Validation for empty/null values
                if (value == null || value.trim().isEmpty() || "null".equalsIgnoreCase(value.trim())) {
                    emptyCellCount++;
                    System.out.printf("Empty/blank/null found at row %d, col %d%n", i, j);
                }
            }

            // Check for buttons in the last column of each row
            List<WebElement> updatebtn = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[" + i + "]/td[9]//a"));
            buttonPresenceCount += updatebtn.size();

            // Scroll to ensure elements are handled during iteration
            js.executeScript("window.scrollBy(0, 100);");
        }

        // Final Assertions for Task 2
        Assert.assertEquals(emptyCellCount, 0, "Some Cells contain empty or null values! Count: " + emptyCellCount);
        Assert.assertTrue(buttonPresenceCount > 0, "No action buttons found in the last column.");

        System.out.println("The Cells are not empty and the action column is validated.");
    }

}
