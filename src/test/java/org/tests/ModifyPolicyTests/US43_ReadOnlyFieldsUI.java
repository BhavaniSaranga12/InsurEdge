package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

public class US43_ReadOnlyFieldsUI extends ModifyPolicyBaseTest {


    @Test(priority = 1)
    public void testFields(){
        SoftAssert sa=new SoftAssert();
        WebElement table=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ContentPlaceHolder_Admin_gvPolicies")));
        String currentTab=driver.getWindowHandle();
        driver.findElement(By.id("ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0")).click();
        Set<String> allTabs=driver.getWindowHandles();

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String tab : allTabs) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                driver.manage().window().maximize();
                break;
            }}

        WebElement mainCategory= driver.findElement(By.name("txtMainCategory"));
        WebElement subCategory= driver.findElement(By.name("txtSubCategory"));
        WebElement policyName= driver.findElement(By.name("txtPolicyName"));
        WebElement sumAssured = driver.findElement(By.name("txtSumAssured"));
        WebElement premium = driver.findElement(By.name("txtPremium"));
        WebElement tenure = driver.findElement(By.name("txtTenure"));
        WebElement status= driver.findElement(By.name("ddlStatus"));

        sa.assertNotNull(mainCategory.getAttribute("readonly"),"Main Category is not read-only");
        sa.assertNotNull(subCategory.getAttribute("readonly"),"Sub Category is not read-only");
        sa.assertNotNull(policyName.getAttribute("readonly"),"Policy Name is not read-only");
        sa.assertTrue(sumAssured.isEnabled(),"Sum Assured is not Clickable");
        sa.assertTrue(premium.isEnabled(),"Premium is not Clickable");
        sa.assertTrue(tenure.isEnabled(),"Tenure is not Clickable");
        sa.assertTrue(status.isEnabled(),"Status is not Clickable");


        sa.assertNotNull(mainCategory.getAttribute("value"),"Main Category does not have the value");
        sa.assertNotNull(subCategory.getAttribute("value"),"Sub Category does not have the value");
        sa.assertNotNull(policyName.getAttribute("value"),"Policy Name does not have the value");
        sa.assertNotNull(sumAssured.getAttribute("value"),"Sum Assured does not have the value");
        sa.assertNotNull(premium.getAttribute("value"),"Premium does not have the value");
        sa.assertNotNull(tenure.getAttribute("value"),"Tenure does not have the value");
        //Assert.assertTrue(status.isSelected(),"Status is not selected");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",status);
        status.click();
        List<WebElement> options= driver.findElements(By.xpath("//*[@id=\"ddlStatus\"]/option"));
        boolean selection=false;
        for(WebElement option:options){
            if(option.isSelected()){
                selection=true;
                break;
            }
        }
        Assert.assertTrue(selection,"Status is not selected");
        Assert.assertTrue(options.size()>0,"Options are not visible");
        driver.close();
        driver.switchTo().window(currentTab);
        sa.assertAll();
    }
}
