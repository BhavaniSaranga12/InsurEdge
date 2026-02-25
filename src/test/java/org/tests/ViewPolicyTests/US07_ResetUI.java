package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US07_ResetUI extends ViewPolicyBaseTest{


    @Test(priority = 1)
    public void resetButtonPosition(){
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder_Admin_btnSearch")));
        WebElement resetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder_Admin_btnReset")));

        // Position Check
        Assert.assertTrue(resetBtn.getLocation().getX() > searchBtn.getLocation().getX(),
                "Reset button is NOT to the right of Search button.");
    }



    @Test(priority = 2)
    public void resetButtonEnabled()  {

        WebElement reset_button = driver.findElement(By.id("ContentPlaceHolder_Admin_btnReset"));
        boolean isEnabled = reset_button.isEnabled();
        Assert.assertTrue(isEnabled, "The reset button is not  enabled.");

        System.out.println("The search button is enabled. Proceeding to click.");
        reset_button.click();

    }
}
