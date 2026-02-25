package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class US53_ScrollToTopFunctionality extends ModifyPolicyBaseTest{

    @Test(priority = 1)
    public void verifyScrollToTopFunctionality() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to bottom dynamically
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebElement scrollToTopBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'back-to-top')] | /html/body/a"))
        );

        Number posBefore = (Number) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
        Assert.assertTrue(posBefore.doubleValue() > 0, "Page did not scroll down.");

        scrollToTopBtn.click();

        // Explicit wait until scroll position is near top
       wait.until(d -> {
            Number posAfter = (Number) ((JavascriptExecutor) d)
                    .executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
            return posAfter.doubleValue() <= 5;
        });

        Number posFinal = (Number) js.executeScript("return window.pageYOffset || document.documentElement.scrollTop;");
        System.out.println("Final Scroll Position: " + posFinal.doubleValue());
        Assert.assertTrue(posFinal.doubleValue() <= 5,
                "Expected scroll position near top (<=5), but was " + posFinal);
    }



}
