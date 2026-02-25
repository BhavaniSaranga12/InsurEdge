package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US27_ScrollToTopFunctionality extends ViewPolicyBaseTest {

@Test(priority = 1)
    public void scrollToTop() throws InterruptedException {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

    //  Locate the Scroll Up button properly
    // Targeted selector based on your DOM: a.back-to-top i.bi-arrow-up-short
    By scrollBtnLocator = By.cssSelector("a.back-to-top i.bi-arrow-up-short");
    WebElement scrollUpButton = wait.until(ExpectedConditions.elementToBeClickable(scrollBtnLocator));

    //  FIX: Get scroll position safely (Handling Double vs Long)
    long scrollPositionBefore = ((Number) js.executeScript("return window.pageYOffset;")).longValue();
    System.out.println("Scroll position before clicking: " + scrollPositionBefore);
    //Assert.assertTrue(scrollPositionBefore > 0, "Page should be scrolled down before clicking!");

    // 5. Click the button to go back to top
    scrollUpButton.click();


    // 6. FIX: Verify scroll position is back to top (0) safely
    wait.until(driver -> {
        long pos = ((Number) ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;")).longValue();
        return pos <= 5; });
         long scrollPositionAfter = ((Number) js.executeScript("return window.pageYOffset;")).longValue();
    System.out.println("Scroll position after clicking: " + scrollPositionAfter);
         Assert.assertTrue(scrollPositionAfter <= 5, "The page did not scroll back to the top!");
    }

}
