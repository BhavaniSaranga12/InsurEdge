package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US31_PolicyNameUI extends ModifyPolicyBaseTest{

    By policyDropdown=By.xpath("/html/body/main/form/div[4]/div/div[1]/div[3]/select");
    By policyName=By.xpath("//label[normalize-space()=\"Policy Name:\"]");				// Policy Name label

    @Test(priority=1)
    void labelValidation()
    {

        String policyLabel=driver.findElement(policyName).getText();
        Assert.assertEquals(policyLabel, "Policy Name:","Policy name label is not present");
    }

    @Test(priority=2)
    void dropdownValidation()
    {
        Assert.assertTrue(driver.findElement(policyDropdown).isDisplayed(),"Policy name is not displayed");
    }

    @Test(priority=3)
    void defaultValue()
    {
        String s=driver.findElement(policyDropdown).getText();
        Assert.assertEquals(s, " All","All is not selected by default");
    }

    @Test(priority=4)
    void clickableValidation()
    {
        Assert.assertTrue(driver.findElement(policyDropdown).isEnabled(),"Policy name is not enabled");
    }
}
