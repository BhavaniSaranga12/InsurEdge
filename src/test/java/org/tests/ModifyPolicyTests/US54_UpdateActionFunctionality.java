package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Set;

public class US54_UpdateActionFunctionality extends ModifyPolicyBaseTest{
    String policy_num, main_cat, sub_cat, policy_name, policy_stat;
    SoftAssert sa=new SoftAssert();

    @Test(priority = 1,  description = "Task-1: Validate Update Action and New Window")
    public void validateUpdateWindow() {
        // Capture initial data from table
        policy_num = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[1]")).getText();
        main_cat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]")).getText();
        sub_cat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[3]")).getText();
        policy_name = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[4]")).getText();
        policy_stat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[8]")).getText();

        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0\"]"))).click();

        // Switch Window
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        driver.manage().window().maximize();

        // Assertion for URL
        Assert.assertTrue(driver.getCurrentUrl().endsWith(policy_num), "The URL does not end with the expected policy number.");
    }

    @Test(priority = 2, dependsOnMethods = "validateUpdateWindow", description = "Task-2 & 3: Verify Auto-populated Fields")
    public void verifyFieldData() {
        String actualMainCat = driver.findElement(By.id("txtMainCategory")).getAttribute("value").trim();
        String actualSubCat = driver.findElement(By.id("txtSubCategory")).getAttribute("value").trim();
        String actualPolicyName = driver.findElement(By.id("txtPolicyName")).getAttribute("value").trim();
        String actualStatus = driver.findElement(By.xpath("//*[@id=\"ddlStatus\"]")).getAttribute("value").trim();

        // Use Assertions instead of If-Else for proper reporting
        sa.assertEquals(actualMainCat.toLowerCase(), main_cat.toLowerCase(), "Main Category mismatch!");
        sa.assertEquals(actualSubCat.toLowerCase(), sub_cat.toLowerCase(), "Sub Category mismatch!");
        sa.assertEquals(actualPolicyName.toLowerCase(), policy_name.toLowerCase(), "Policy Name mismatch!");
        sa.assertEquals(actualStatus.toLowerCase(), policy_stat.toLowerCase(), "Policy Status mismatch!");

        System.out.println("Verification successful for: " + actualPolicyName);
        sa.assertAll();
        Set<String> allWindows = driver.getWindowHandles();
        String currentWindow = driver.getWindowHandle();
        for (String window : allWindows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

    }

}
