package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Set;

public class US56_CancelFunctionality extends ModifyPolicyBaseTest{
    JavascriptExecutor js;
    String parentWindow;
    SoftAssert sa=new SoftAssert();

    // Variables for cross-method validation
    String policy_num, main_cat, sub_cat, policy_name, policy_stat;
    String main_cat2, sub_cat2, policy_name2, policy_stat2;

    @Test(priority = 1)
    public void validateUpdateWindowSelection() {
        // Capture initial table data
        policy_num = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[1]")).getText();
        main_cat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[2]")).getText();
        sub_cat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[3]")).getText();
        policy_name = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[4]")).getText();
        policy_stat = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[2]/td[8]")).getText();

        parentWindow = driver.getWindowHandle();

        WebElement updateBtn = driver.findElement(
                By.xpath("//a[@id='ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0']")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", updateBtn);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0\"]"))).click();

        // Switch to child window
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        driver.manage().window().maximize();

        Assert.assertTrue(driver.getCurrentUrl().endsWith(policy_num), "Task-1 Failed: URL does not match policy number.");
    }

    @Test(priority = 2, dependsOnMethods = "validateUpdateWindowSelection")
    public void validateCancelButtonAndRedirection() {
        // Capture child window data for later comparison
        main_cat2 = driver.findElement(By.id("txtMainCategory")).getAttribute("value").trim();
        sub_cat2 = driver.findElement(By.id("txtSubCategory")).getAttribute("value").trim();
        policy_name2 = driver.findElement(By.id("txtPolicyName")).getAttribute("value").trim();
        policy_stat2 = driver.findElement(By.xpath("//*[@id=\"ddlStatus\"]")).getAttribute("value").trim();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",driver.findElement(By.id("btnCancel")) );

        WebElement cancel_Btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCancel")));

        Assert.assertTrue(cancel_Btn.isEnabled(), "Task-2 Failed: Cancel button is not enabled.");
        cancel_Btn.click();

        // Switch back to parent and verify URL
        driver.switchTo().window(parentWindow);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://qeaskillhub.cognizant.com/AdminModifyPolicy", "Task-3 Failed: Not redirected to Modify Policy page.");
    }

    @Test(priority = 3, dependsOnMethods = "validateCancelButtonAndRedirection")
    public void verifyDataStability() {
        // Task-4: Compare child window data with initial table data
        sa.assertEquals(main_cat2, main_cat, "Main Category mismatch!");
        sa.assertEquals(sub_cat2, sub_cat, "Sub Category mismatch!");
        sa.assertEquals(policy_name2, policy_name, "Policy Name mismatch!");
        sa.assertEquals(policy_stat2, policy_stat, "Policy Status mismatch!");

        System.out.println("Data stability verified: Cancel action had no effect on data.");
        sa.assertAll();
    }

}
