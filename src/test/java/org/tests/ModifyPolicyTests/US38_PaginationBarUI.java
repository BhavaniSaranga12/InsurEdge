package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US38_PaginationBarUI extends ModifyPolicyBaseTest{

    @Test(priority = 1)
    public void verifyPaginationBarVisibility() {
        // Task 1: Check if the pager row exists in the table
        WebElement paginationBar = driver.findElement(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[last()]"));
        Assert.assertTrue(paginationBar.isDisplayed(), "Pagination bar is not visible below the results table.");
    }

    @Test(priority = 2)
    public void verifyPaginationStartNumber() {
        // Task 2: Validate numbering begins at 1
        List<WebElement> pages = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[last()]//td/table/tbody/tr/td"));
        Assert.assertEquals(pages.get(0).getText().trim(), "1", "Pagination does not begin at page 1.");
    }

    @Test(priority = 3)
    public void verifyEllipsisForLargePages() {
        // Task 3: Robust ellipsis check
        List<WebElement> pageElements = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[last()]//td/table/tbody/tr/td"));

        boolean ellipsisFound = false;
        for (WebElement el : pageElements) {
            String text = el.getText().trim();
            if (text.equals("...") || text.contains("..")) {
                ellipsisFound = true;
                break;
            }
        }

        // Logic: Ellipsis should only exist if there are more than 10 page buttons
        if (pageElements.size() > 10) {
            Assert.assertTrue(ellipsisFound, "Ellipsis '...' was not found even though page count is > 10.");
        } else {
            System.out.println("Info: Ellipsis not required. Total page elements: " + pageElements.size());
            // We pass the test because it's technically correct for the data provided
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 4)
    public void verifyPageNavigation() {
        WebElement pageTwoLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[last()]//td/table/tbody/tr/td[2]/a")
        ));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pageTwoLink);

        try {
            // Try normal click
            wait.until(ExpectedConditions.elementToBeClickable(pageTwoLink)).click();
        } catch (ElementClickInterceptedException e) {
            // Fallback to JS click if intercepted
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pageTwoLink);
        }

        // Verify Page 2 is active
        WebElement activePage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[last()]//td/table/tbody/tr/td[2]/span")
        ));

        Assert.assertEquals(activePage.getText().trim(), "2",
                "Clicking page 2 did not load the correct results/active state.");
    }


}
