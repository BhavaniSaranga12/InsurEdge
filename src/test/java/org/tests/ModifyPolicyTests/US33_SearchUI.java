package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US33_SearchUI extends ModifyPolicyBaseTest {


    By searchbtn=By.xpath("//input[contains(@id,\"btnSearch\")]");
    @Test(priority=1)
    void labelValidation()
    {
        String s=driver.findElement(searchbtn).getAttribute("value");
        Assert.assertEquals(s, "Search","Search label is not present");
    }

    @Test(priority=2, dependsOnMethods="labelValidation")
    void searchEnable()
    {
        Assert.assertTrue(driver.findElement(searchbtn).isEnabled(),"Search button is not enabled");
    }
}
