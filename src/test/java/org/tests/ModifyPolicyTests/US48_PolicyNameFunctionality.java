package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class US48_PolicyNameFunctionality extends ModifyPolicyBaseTest{

    @Test(priority = 1)
    public void selectableOptions(){
        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
        List<String> options=sel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        options.remove("All");
        WebElement mainC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]"));
        mainC.click();
        sel.selectByVisibleText(options.get(1));


        Select subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
        List<String> suboptions=subSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        suboptions.remove("All");
        WebElement subC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]"));
        subC.click();
        subSel.selectByVisibleText(suboptions.get(0));

        Select policyNameSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
        List<String> policynames1=policyNameSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        policynames1.remove("All");
        WebElement policyName=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]"));
        policyName.click();

        Select sel2=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
        List<String> options2=sel2.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        options2.remove("All");
        WebElement mainC2=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]"));
        mainC2.click();
        sel2.selectByVisibleText(options2.get(0));

        Select subSel2=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
        List<String> suboptions2=subSel2.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        suboptions2.remove("All");
        WebElement subC2=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]"));
        subC2.click();
        subSel2.selectByVisibleText(suboptions2.get(0));

        Select policyNameSel2=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
        List<String> policynames12=policyNameSel2.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        policynames12.remove("All");
        WebElement policyName12=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]"));
        policyName12.click();

        Assert.assertTrue(!policynames1.equals(policynames12),"Policy names are not changing according to the selection of main and sub categories");
    }





    }
