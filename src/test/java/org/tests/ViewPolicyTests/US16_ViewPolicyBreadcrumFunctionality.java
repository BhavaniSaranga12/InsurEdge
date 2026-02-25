package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US16_ViewPolicyBreadcrumFunctionality extends ViewPolicyBaseTest{

    @Test(priority = 1)
    public void breadcrumNavigationToDashboard(){

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Policy')]"))).click();
        WebElement viewLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='AdminViewPolicy.aspx']")));
        viewLink.click();

        // Locate the "Policy" link in the Breadcrumb
        // Using the structure identified in your screenshot: ol.breadcrumb -> li -> a
        WebElement policyBreadcrumbLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ol[@class='breadcrumb']//a[text()='Policy']")
        ));

        //  Perform Navigation back to Dashboard
        policyBreadcrumbLink.click();

        //  Validate Dashboard page loads successfully
        // We now check for the broader string "AdminDashboard" to avoid TimeoutExceptions
        // caused by URL redirection or clean URL routing.
        boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains("AdminDashboard"));
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(isUrlCorrect, "Navigation failed: The URL does not contain 'AdminDashboard'. Current URL: " + currentUrl);
    }

}
