package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US02_MainCategoryUI extends ViewPolicyBaseTest{

    @Test(priority = 1)
    public void mainCategoryLabelValidation()
    {
        String categoryText=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/label")).getText();
        Assert.assertTrue(categoryText.equals("Main Category"),"The label “Main Category” is not displayed on the page");

    }

    @Test(priority = 2)

    void emptyTextboxValidation()
    {
        String subText=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li[1]")).getText();
        Assert.assertTrue(subText.isEmpty(),"The textbox is not empty by default when the page loads.");
    }

    @Test(priority = 3)
    void multiselectTextboxValidation() throws InterruptedException
    {
        WebElement mainSelect2Container = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainSelect2Container.click();
        List<WebElement> eleList1 = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        for(WebElement w:eleList1)
        {
            System.out.println(w.getText());
        }
        String ele1 = eleList1.get(1).getText();
        wait.until(ExpectedConditions.elementToBeClickable(eleList1.get(1))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li[2]/input"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));


        List<WebElement> eleList2 = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(eleList2.get(2))).click();

    }

    @Test(priority = 4,dependsOnMethods = {"multiselectTextboxValidation"})
    void crossbtnvalidation() {
        WebElement rmv=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li[1]/span"));
        Assert.assertTrue(rmv.isDisplayed());
    }
}
