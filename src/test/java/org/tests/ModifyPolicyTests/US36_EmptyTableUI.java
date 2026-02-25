package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class US36_EmptyTableUI extends ModifyPolicyBaseTest {

    By mainCategoryDropdown=By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]");		// main category dropdown
    By subCategoryDropdown=By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]");			//sub category dropdown
    By policyDropdown=By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]");  		// policy name dropdown
    By policyStatus=By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]");			// policy status dropdown
    By tableDiv=By.xpath("//table[contains(@class,\"table table-bordered table-hover shadow-sm\")]");
    By searchbtn=By.xpath("//input[contains(@id,\"btnSearch\")]");

    @Test(priority = 1)
   public  void emptyStateValidation()
    {
        driver.findElement(mainCategoryDropdown).click();
        Select select=new Select(driver.findElement(mainCategoryDropdown));
        List<String> options=select.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        for(int i=1;i<options.size();i++)
        {
            select=new Select(driver.findElement(mainCategoryDropdown));
            select.selectByVisibleText(options.get(i));
            driver.findElement(subCategoryDropdown).click();
            Select select2=new Select(driver.findElement(subCategoryDropdown));
            List<String> options2=select2.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
            for(int j=1;j<options2.size();j++)
            {
                select2=new Select(driver.findElement(subCategoryDropdown));
                select2.selectByVisibleText(options2.get(j));
                Select select3=new Select(driver.findElement(policyDropdown));
                List<String> options3=select3.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                for(int k=1;k<options3.size();k++)
                {
                    select3=new Select(driver.findElement(policyDropdown));
                    select3.selectByVisibleText(options3.get(k));
                    driver.findElement(policyStatus).click();
                    Select select4=new Select(driver.findElement(policyStatus));
                    List<String> options4=select4.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                    for(int m=1;m<options4.size();m++)
                    {
                        select4=new Select(driver.findElement(policyStatus));
                        select4.selectByVisibleText(options4.get(m));

                        driver.findElement(searchbtn).click();
                        //table checking
//						Assert.assertTrue(!p.tableDiv.isDisplayed());
                        Assert.assertTrue(!driver.findElement(tableDiv).isDisplayed(),"No records found message is not displayed");
//                            if(!driver.findElement(tableDiv).isDisplayed())
//                            {
////
//                                System.out.println("Empty table validated sucessfully");
//
//                                break;
//                            }
//
                    }
                }
            }
        }
    }

}
