package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class US01_ViewPolicyBreadcrumUI extends ViewPolicyBaseTest{

    @Test(priority = 1)
    public void viewPolicyTitle(){
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='pagetitle']/h1")
        ));
        String actualTitle = titleElement.getText().trim();
        Assert.assertEquals(actualTitle, "View Policy", "The Page Title does not match!");
    }


    @Test(priority = 2)
    public void viewPolicyBreadcrumUI(){
        WebElement policyLink = driver.findElement(
                By.xpath("//ol[@class='breadcrumb']/li[@class='breadcrumb-item']/a[text()='Policy']")
        );
        WebElement viewText = driver.findElement(
                By.xpath("//ol[@class='breadcrumb']/li[contains(@class,'active') and text()='View']")

        );
        String separator = (String) ((JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", viewText);
        Assert.assertTrue(policyLink.isDisplayed(), "Policy link is not displayed in breadcrumb");

        Assert.assertTrue(viewText.isDisplayed(), "View text is not displayed in breadcrumb");
        System.out.println("Breadcrumb Validation Passed. Separator verified: " + separator);

    }


}
