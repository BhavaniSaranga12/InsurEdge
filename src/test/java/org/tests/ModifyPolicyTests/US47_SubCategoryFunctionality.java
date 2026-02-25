package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class US47_SubCategoryFunctionality extends ModifyPolicyBaseTest{


    @Test(priority = 1)
    public void selectableOptions(){
        HashMap<String,List<String>> mainSub=new HashMap<>();
        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
        List<String> options=sel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        options.remove("All");
        for(int i=0;i<options.size();i++){
           WebElement mainC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]"));
           mainC.click();
            sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            sel.selectByVisibleText(options.get(i));
            WebElement subC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]"));
            subC.click();
            Select subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
            List<String> options2=subSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
            mainSub.put(options.get(i),options2);

        }
        System.out.println(mainSub);

        Set<Set<String>> uniqueSets = new HashSet<>();

        boolean allDifferent = true;

        for (List<String> list : mainSub.values()) {
            Set<String> set = new HashSet<>(list); // convert list → set (ignores order)
            if (!uniqueSets.add(set)) {
                allDifferent = false;
                break;
            }
        }

//        if (allDifferent) {
//            System.out.println("All HashMap values are DIFFERENT.");
//        } else {
//            System.out.println("Some values are DUPLICATE.");
//        }
        System.out.println(uniqueSets);
        Assert.assertTrue(allDifferent,"Some values are DUPLICATE.");


    }


    @Test(priority=2,dependsOnMethods = {"selectableOptions"})
    void oneSelectionValidation()
    {

        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
        sel.selectByIndex(0);
        sel.selectByIndex(1);

        List<WebElement> selected=driver.findElements(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]/option[@selected=\"selected\"]"));
//        System.out.println(selected.size());
//        System.out.println(selected.get(0).getText());
        Assert.assertTrue(selected.size()==1,"User should be able to select only one option");
    }

    @Test(priority=3,dependsOnMethods = {"selectableOptions"})
    void visibilityValidation()
    {
        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
        sel.selectByIndex(1);
        sel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
        String s=sel.getFirstSelectedOption().getText();
        System.out.println(s);
        Assert.assertNotNull(s, "No option was selected.");
    }







}
