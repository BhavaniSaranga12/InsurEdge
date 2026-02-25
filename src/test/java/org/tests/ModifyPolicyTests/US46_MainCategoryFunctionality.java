package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US46_MainCategoryFunctionality extends ModifyPolicyBaseTest{

        @Test(priority=1)
        void selectableOptionsValidation()
        {

            Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            List<WebElement> options=sel.getOptions();
            Assert.assertTrue(options.size()>0,"Main category doesn't have any selectable options");
        }

        @Test(priority=2,dependsOnMethods = {"selectableOptionsValidation"})
        void oneSelectionValidation()
        {

            Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            sel.selectByIndex(0);
            sel.selectByIndex(1);

            List<WebElement> selected=driver.findElements(By.xpath("/html/body/main/form/div[4]/div/div[1]/div[1]/select/option[@selected=\"selected\"]"));
            Assert.assertTrue(selected.size()==1,"User should be able to select only one option");
        }

        @Test(priority=3,dependsOnMethods = {"selectableOptionsValidation"})
        void visibilityValidation()
        {
            Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            sel.selectByIndex(3);
            sel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            String s=sel.getFirstSelectedOption().getText();
            System.out.println(s);
            Assert.assertNotNull(s, "No option was selected.");
        }




}
