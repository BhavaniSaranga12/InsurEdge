package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US06_SearchUI extends ViewPolicyBaseTest{

    @Test(priority = 1)
    public void searchButtonEnabled() throws InterruptedException {

        WebElement search_button = driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch"));
        boolean isEnabled = search_button.isEnabled();
        Assert.assertTrue(isEnabled, "The search button is not enabled.");

        System.out.println("The search button is enabled. Proceeding to click.");
        search_button.click();

    }

}
