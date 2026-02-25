package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US49_PolicyStatusFunctionality extends ModifyPolicyBaseTest {

    By policyDropdown=By.xpath("/html/body/main/form/div[4]/div/div[1]/div[3]/select");  		// policy name dropdown

    By policyStatus=By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]");

    @Test(priority = 1)
    void defaultValue()
    {
        String s=driver.findElement(policyDropdown).getText();
        Assert.assertEquals(s, " All","ALL is not selected by default");
    }

    @Test(priority = 2)
    void dropdownsOptions()
    {
        Select select=new Select(driver.findElement(policyStatus));
        List<WebElement> options=select.getOptions();
        for(WebElement element: options)
        {
            if(!element.getText().equalsIgnoreCase("All") ||element.getText().equalsIgnoreCase("Approved") || element.getText().equalsIgnoreCase("Pending") || element.getText().equalsIgnoreCase("Rejected"))
            {
                Assert.assertFalse(false,"the expected options are not present");
            }
        }
    }

    @Test(priority = 3)
    void oneSelectionValidation()
    {

        driver.findElement(policyStatus).click();
        Select sel=new Select(driver.findElement(policyStatus));
        sel.selectByIndex(0);
        sel.selectByIndex(1);
        sel.selectByIndex(2);
        String s=driver.findElement(policyStatus).getText();
        System.out.println(s);
    }

}
