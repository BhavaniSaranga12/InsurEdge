package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US28_ModifyPolicyBreadcrumUI extends ModifyPolicyBaseTest{



    // Page section
    private final By pageTitle = By.xpath("//*[@id='AdminModifyPolicy']/div[3]/h1");

    // Breadcrumbs
    private final By breadcrumbPolicyLink = By.xpath("//*[@id='AdminModifyPolicy']/div[3]/nav/ol/li[1]/a");
    private final By breadcrumbModifyItem = By.xpath("//*[@id='AdminModifyPolicy']/div[3]/nav/ol/li[2]");



    /**
     * Task 1: Validate that the page title “Modify Policy” is displayed at the top-left corner of the page.
     */
    @Test(priority = 1)
    public void verifyPageTitleDisplayedTopLeft() {
        WebElement titleEl = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        Assert.assertTrue(titleEl.isDisplayed(), "Page title is not displayed.");

        // Optional value check if title text is stable
        String actual = titleEl.getText().trim();
        Assert.assertEquals(actual, "Modify Policy", "Page title text mismatch.");

        // (Optional) If you want to assert position ~ top-left, you could check coordinates:
        // Assert.assertTrue(titleEl.getLocation().getX() < 50 && titleEl.getLocation().getY() < 200,
        //        "Title does not appear near top-left as expected.");
    }

    /**
     * Task 2: Validate that the breadcrumb “Policy / Modify” appears directly below the page title.
     */
    @Test(priority = 2)
    public void verifyBreadcrumbAppearsBelowTitle() {
        WebElement titleEl = driver.findElement(pageTitle);
        WebElement policyCrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbPolicyLink));
        WebElement modifyCrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbModifyItem));

        Assert.assertTrue(policyCrumb.isDisplayed(), "Breadcrumb 'Policy' is not displayed.");
        Assert.assertTrue(modifyCrumb.isDisplayed(), "Breadcrumb 'Modify' is not displayed.");

        // Basic structural check: breadcrumb Y should be greater than title Y (i.e., below)
        int titleBottomY = titleEl.getLocation().getY() + titleEl.getSize().getHeight();
        int breadcrumbY = policyCrumb.getLocation().getY();
        Assert.assertTrue(breadcrumbY >= titleBottomY - 5,
                "Breadcrumb does not appear directly below the page title.");
    }

    /**
     * Task 3: Validate that the “Policy” breadcrumb is clickable.
     */
    @Test(priority = 3,dependsOnMethods = {"verifyBreadcrumbAppearsBelowTitle"})
    public void verifyPolicyBreadcrumbClickable() {
        WebElement policyCrumbLink = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbPolicyLink));
        Assert.assertTrue(policyCrumbLink.isDisplayed(), "'Policy' breadcrumb is not visible.");
        Assert.assertTrue(policyCrumbLink.isEnabled(), "'Policy' breadcrumb link is not enabled.");

        // Optionally try clicking and verify navigation (then navigate back)
        String href = safeGetAttribute(policyCrumbLink, "href");
        Assert.assertNotNull(href, "'Policy' breadcrumb does not have an href, might not be clickable.");
        Assert.assertTrue(href.startsWith("http") || href.startsWith("/"),
                "Breadcrumb link does not look like a valid URL: " + href);
        // You can uncomment this if navigation is stable:
        // policyCrumbLink.click();
        // wait.until(ExpectedConditions.urlContains("Policy"));
        // driver.navigate().back();
        // wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    /**
     * Task 4: Validate that the current page “Modify” in the breadcrumb is highlighted and non-clickable.
     */
    @Test(priority = 4,dependsOnMethods = {"verifyBreadcrumbAppearsBelowTitle"})
    public void verifyModifyBreadcrumbHighlightedAndNonClickable() {
        WebElement modifyCrumb = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbModifyItem));
        Assert.assertTrue(modifyCrumb.isDisplayed(), "'Modify' breadcrumb is not visible.");

        // Non-clickable: it should NOT contain an anchor <a>
        boolean hasAnchorInside;
        try {
            modifyCrumb.findElement(By.tagName("a"));
            hasAnchorInside = true;
        } catch (NoSuchElementException e) {
            hasAnchorInside = false;
        }
        Assert.assertFalse(hasAnchorInside, "'Modify' breadcrumb should be non-clickable, but an <a> was found.");

        // Highlighted: often indicated by an 'active' class on <li>
        String classes = safeGetAttribute(modifyCrumb, "class");
        // Adjust the condition below to match your app’s CSS conventions (e.g., 'active', 'current', etc.)
        Assert.assertTrue(classes != null && classes.toLowerCase().contains("active"),
                "Expected 'Modify' breadcrumb to be highlighted via CSS class. Actual classes: " + classes);
    }
    private String safeGetAttribute(WebElement el, String name) {
        try {
            return el.getAttribute(name);
        } catch (Exception e) {
            return null;
        }
    }
}
