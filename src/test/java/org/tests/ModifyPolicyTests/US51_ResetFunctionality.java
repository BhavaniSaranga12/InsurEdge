package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US51_ResetFunctionality extends ModifyPolicyBaseTest{

    private String selectByIndexAndReturnText(By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);

        // Get the text before selecting so we can return it for validation
        String selectedText = select.getOptions().get(index).getText();

        select.selectByIndex(index);

        // Wait for postback staleness
        wait.until(ExpectedConditions.stalenessOf(element));
        return selectedText;
    }

    @Test(priority = 1)
    public void verifyResetButtonFunctionality() {
        // 1. Select Main Category using Index (e.g., Index 2)
        String chosenMainCat = selectByIndexAndReturnText(By.id("ContentPlaceHolder_Admin_ddlMainCategory"), 2);
        System.out.println("Selected Main Category: " + chosenMainCat);

        // 2. Select Sub Category using Index (e.g., Index 1)
        String chosenSubCat = selectByIndexAndReturnText(By.id("ContentPlaceHolder_Admin_ddlSubCategory"), 1);
        System.out.println("Selected Sub Category: " + chosenSubCat);

        // 3. Select Policy Name (Dynamic Index Selection: Select first available after 'All')
        WebElement policyDD = wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_ddlPolicyName")));
        Select policySelect = new Select(policyDD);
        List<WebElement> policyOptions = policySelect.getOptions();
        int policyIndexToSelect = (policyOptions.size() > 1) ? 1 : 0;
        String chosenPolicy = policyOptions.get(policyIndexToSelect).getText();
        policySelect.selectByIndex(policyIndexToSelect);
        System.out.println("Selected Policy Name: " + chosenPolicy);

        // 4. Select Status using Index (e.g., Index 2 for 'Pending' or 'Approved')
        WebElement statusDD = wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_ddlStatus")));
        Select statusSelect = new Select(statusDD);
        String chosenStatus = statusSelect.getOptions().get(2).getText();
        statusSelect.selectByIndex(2);
        System.out.println("Selected Status: " + chosenStatus);

        // 5. Click Search and verify grid is populated with filtered data
        driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch")).click();

        // Use try-catch to handle cases where no records are found for the specific index combination
        int filteredRowCount = 0;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder_Admin_gvPolicies")));
            List<WebElement> rowsBeforeReset = driver.findElements(By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[td]"));
            filteredRowCount = rowsBeforeReset.size();
            System.out.println("Search successful. Filtered Grid contains: " + filteredRowCount + " rows.");
        } catch (Exception e) {
            System.out.println("No records found for the filtered criteria, proceeding to test Reset button.");
        }

        // 6. Click the Reset Button
        WebElement resetBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_btnReset")));
        resetBtn.click();

        // Wait for stability as Reset triggers a postback/refresh
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ContentPlaceHolder_Admin_ddlMainCategory")));

        // 7. Verify Grid display reverts to unfiltered state
        List<WebElement> rowsAfterReset = driver.findElements(By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[td]"));
        int resetRowCount = rowsAfterReset.size();

        System.out.println("Reset successful. Unfiltered Grid now contains: " + resetRowCount + " rows.");

        // Logic: After reset, row count should be greater than or equal to filtered results
        Assert.assertFalse(rowsAfterReset.isEmpty(), "Grid display is missing or empty after reset.");
        Assert.assertTrue(resetRowCount >= filteredRowCount, "Grid should show full list after reset.");

        // 8. Validation Logic for Dropdowns reverting to "All"
        checkDropdownText("ContentPlaceHolder_Admin_ddlMainCategory", "All", "Main Category");
        checkDropdownText("ContentPlaceHolder_Admin_ddlSubCategory", "All", "Sub Category");
        checkDropdownText("ContentPlaceHolder_Admin_ddlStatus", "All", "Policy Status");
        checkDropdownText("ContentPlaceHolder_Admin_ddlPolicyName", "All", "Policy Name");




        System.out.println("Reset Button Validation Successful.");
    }

    private void checkDropdownText(String elementId, String expectedText, String dropdownName) {
        Select select = new Select(driver.findElement(By.id(elementId)));
        String actualVisibleText = select.getFirstSelectedOption().getText().trim();

        Assert.assertEquals(actualVisibleText, expectedText, dropdownName + " did not reset correctly.");
        System.out.println(dropdownName + " successfully reset to: " + actualVisibleText);
    }

}
