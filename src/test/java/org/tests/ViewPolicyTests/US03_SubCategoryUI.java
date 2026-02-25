package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US03_SubCategoryUI extends ViewPolicyBaseTest{

    @Test(priority = 1)
    void subCategoryLabelValidation()
    {
        String categoryText=driver.findElement(By.xpath("//div[@class='row mb-3']//div[2]//label")).getText();
        Assert.assertTrue(categoryText.equals("Sub Category"),"The label “Sub Category” is not displayed on the page");

    }

    @Test(priority = 2)
    void emptyTextboxValidation()
    {
        String subText=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li[1]")).getText();
        Assert.assertTrue(subText.isEmpty(),"The textbox is not empty by when page loads");
    }

    @Test(priority = 3)
    void multiselectTextboxValidation()
    {
        WebElement mainSelect2Container = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainSelect2Container.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> eleList1 = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        String ele1 = eleList1.get(0).getText();
        wait.until(ExpectedConditions.elementToBeClickable(eleList1.get(0))).click();

        WebElement subCategoryBox = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul"))
        );
        subCategoryBox.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        List<WebElement> eleList2 = driver.findElements(By.xpath("//li[@class=\"select2-results__option select2-results__option--highlighted\"]"));
        String ele2 = eleList2.get(0).getText();
        wait.until(ExpectedConditions.elementToBeClickable(eleList2.get(0))).click();

//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul"))).click();
//		String ele3 = eleList2.get(1).getText();
//		wait.until(ExpectedConditions.elementToBeClickable(eleList2.get(1))).click();
//		System.out.println("The Selected Element is: "+ele3);
    }

    @Test(priority = 4,dependsOnMethods = {"multiselectTextboxValidation"})
    void crossbtnvalidation() {
        WebElement rmv=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li[1]/span"));
        Assert.assertTrue(rmv.isDisplayed());
    }

}
