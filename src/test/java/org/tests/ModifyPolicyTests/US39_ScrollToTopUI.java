package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US39_ScrollToTopUI extends ModifyPolicyBaseTest{

    @Test(description = "Validate Scroll to Top button UI and Functionality")
    public void validateScrollToTop() throws InterruptedException {
        // Scrolling the Page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000);");
        Thread.sleep(5000);

        WebElement srBttnIcon = driver.findElement(By.xpath("/html/body/a/i"));
        WebElement srBttn = driver.findElement(By.xpath("/html/body/a"));

        // 1) Validate Display
        if (srBttnIcon.isDisplayed()) {
            System.out.println("The Scroll Arrow Button is Displayed.");
        } else {
            System.out.println("The Scroll Arrow Button is not Displayed.");
        }
        Assert.assertTrue(srBttnIcon.isDisplayed(), "Scroll button icon should be visible after scrolling down.");

        // 2) Validate Icon Class
        String iconClass = srBttn.getAttribute("class").toLowerCase();
        if (iconClass.contains("arrow") || iconClass.contains("up")) {
            System.out.println("Upward arrow icon is displayed.");
        } else {
            System.out.println("Arrow icon not present.");
        }

        // 3) Clicking the Scroll Button and validating position
        srBttn.click();
        Thread.sleep(1500);
        Long pos = (Long) js.executeScript("return window.scrollY;");

        if (pos == 0) {
            System.out.println("Page scrolled back to top.");
        } else {
            System.out.println("Page did not return to top.");
        }
        Assert.assertEquals(pos.longValue(), 0L, "Page Y-offset should be 0 after clicking scroll-to-top.");

        // 4) Check visibility at top
        Assert.assertTrue(!srBttn.isDisplayed(),"Scroll-to-Top button disappeared.");
//        if (!srBttn.isDisplayed()) {
//            System.out.println("Scroll-to-Top button disappeared.");
//        } else {
//            System.out.println("Button still visible at top.");
//        }
    }
}
