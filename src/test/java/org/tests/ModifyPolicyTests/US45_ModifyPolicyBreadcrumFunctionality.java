package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US45_ModifyPolicyBreadcrumFunctionality extends ModifyPolicyBaseTest{

    By modifyButton=By.xpath("//html//body//main//form//div[3]//nav//ol//li[1]//a[contains(@href,\"AdminDashboard.aspx\")]");


    By dashboardHeader=By.xpath("//*[@id=\"main\"]/div/h1");
    @Test(priority = 1)
   public void linkClickable()
    {
        Assert.assertTrue(driver.findElement(modifyButton).isEnabled(),"the breadcrum link is not clickable");
    }

    @Test(priority = 2)
    public void policyPageRedirect()
    {
        driver.findElement(modifyButton).click();
        Assert.assertTrue(driver.findElement(dashboardHeader).isDisplayed(),"it is not directed to the dashboard");
    }

}
