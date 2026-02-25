package org.tests.ViewPolicyTests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class US20_PolicyStatusFunctionality extends ViewPolicyBaseTest {
    String policyStatusInputXPath = "//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/input";
    By policyStatusContainer = By.xpath("//*[@id='form1']/div[3]/div[4]/span/span[1]/span/ul");


    @Test(priority = 1)
    public void verifyStatusOptions() {
        // Task 1: Open Policy Status and verify values
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[4]/span/span[1]/span/ul"))).click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("select2-results__option")));
        List<String> expected = Arrays.asList("Approved", "Pending", "Rejected");

        for (WebElement opt : options) {
            Assert.assertTrue(expected.contains(opt.getText()), "Unexpected status found: " + opt.getText());
        }
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    }

    @Test(priority = 2)
    public void verifyAutoSuggestion() {
        // Task 2: Validate typing and suggestions using your specific XPath
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[4]/span/span[1]/span/ul"))).click();
        WebElement input = driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/input"));
        input.sendKeys("App");

        WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-results__option")));
        Assert.assertEquals(suggestion.getText(), "Approved", "Auto-suggestion failed!");
        input.clear();
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    }

    @Test(priority = 3)
    public void verifyMultiSelection() {
        // Task 3: Select all status values
        String[] statuses = {"Approved", "Pending", "Rejected"};

        for (String status : statuses) {
            wait.until(ExpectedConditions.elementToBeClickable(policyStatusContainer)).click();
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(policyStatusInputXPath)));
            input.sendKeys(status + Keys.ENTER);
        }

        List<WebElement> selections = driver.findElements(By.xpath("//*[@id='form1']/div[3]/div[4]//li[@class='select2-selection__choice']"));
        Assert.assertEquals(selections.size(), 3, "Policy Status selection count mismatch!");
    }

    @Test(priority = 4, dependsOnMethods = "verifyMultiSelection")
    public void verifyRemoval() {
        JavascriptExecutor js=(JavascriptExecutor) driver;
        // Task 4: Remove items individually
        while (true) {
            // Re-find remove buttons inside the loop to prevent StaleElementReferenceException
            List<WebElement> removeButtons = driver.findElements(By.xpath("//*[@id='form1']/div[3]/div[4]//span[contains(@class,'remove')]"));

            if (removeButtons.isEmpty()) {
                break;
            }

            WebElement btn = removeButtons.get(0);
            try {
                js.executeScript("arguments[0].click();", btn);
                // Wait for the specific element to disappear from DOM before next iteration
                wait.until(ExpectedConditions.stalenessOf(btn));
            } catch (StaleElementReferenceException e) {
                // If element went stale before JS could click, simply retry the loop
                continue;
            }
        }

        // Final Verification
        List<WebElement> remaining = driver.findElements(By.xpath("//*[@id='form1']/div[3]/div[4]//li[@class='select2-selection__choice']"));
        Assert.assertEquals(remaining.size(), 0, "Policy Status items were not cleared!");
    }
}
