package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;


public class US15_ScrollToTopUI extends ViewPolicyBaseTest{
    @Test(priority = 1)
    public void scrollToTop(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // 4. Locate the Scroll Up Button
        // We target the anchor tag <a> that has the 'back-to-top' class AND contains the icon
        By scrollBtnLocator = By.cssSelector("a.back-to-top i.bi-arrow-up-short");

        List<WebElement> scrollUpIcons = driver.findElements(scrollBtnLocator);

        // Logical Check 1: Button Presence in DOM
        Assert.assertFalse(scrollUpIcons.isEmpty(), "Scroll Up icon with class 'bi-arrow-up-short' not found!");

        WebElement scrollUpIcon = scrollUpIcons.get(0);

        // Logical Check 2: Button Visibility
        // Using wait to ensure any transition animations have finished
        boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(scrollUpIcon)).isDisplayed();

        if (isDisplayed) {
            System.out.println("Scroll Up Button is visible and located properly.");
        } else {
            System.out.println("Scroll Up Button found in DOM but is currently hidden.");
        }

        Assert.assertTrue(isDisplayed, "Scroll Up Button should be visible after scrolling down!");
    }


}
