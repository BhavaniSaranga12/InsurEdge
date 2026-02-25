package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US04_PolicyNameUI extends ViewPolicyBaseTest{
    @Test(priority = 1)
    public void validateUI() {

        WebElement subCategoryLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-md-3']/label[contains(text(),'Sub Category')]")));

        WebElement policyNameLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-md-3']//label[contains(text(),'Policy Name')]")));
        Assert.assertTrue(policyNameLabel.isDisplayed() && policyNameLabel.getLocation().getX() > subCategoryLabel.getLocation().getX(),"Policy Name Label is not displayed");

    }

    @Test(priority = 2)
    public void emptyTextboxValidation(){
        WebElement policyNameTextbox = driver.findElement(By.xpath("//div[@class='col-md-3']//label[contains(text(),'Policy Name')]/following::input[@class='select2-search__field'][1]"));
        Assert.assertTrue(policyNameTextbox.getText().isEmpty(),"Policy Name is not empty by default");

    }


  @Test(priority = 3)
    public void verifyMultiSelectTextbox() {

        // 1) Main Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[1]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
       driver.findElements(By.xpath("//li[@role='option']")).get(1).click();


        // 2) Sub Category
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[2]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        driver.findElements(By.xpath("//li[contains(@class,'select2-results__option--highlighted')]")).get(0).click();

        // 3) Policy Name
        wait.until(ExpectedConditions .elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[3]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        driver.findElements(By.xpath("//ul[@id='select2-ContentPlaceHolder_Admin_ddlPolicyName-results']/li")).get(0).click();
        String pn1=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul/li[1]")).getText();


        wait.until(ExpectedConditions .elementToBeClickable(By.xpath("//*[@id='form1']/div[3]/div[3]/span/span[1]/span/ul"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.select2-results__options")));
        driver.findElements(By.xpath("//ul[@id='select2-ContentPlaceHolder_Admin_ddlPolicyName-results']/li")).get(1).click();
        String pn2= driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul/li[1]")).getText();

        List<WebElement> selectedTags = driver.findElements(
               By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul/li"));


        boolean option1Present = selectedTags.stream().anyMatch(tag -> tag.getText().contains(pn1));
        boolean option2Present = selectedTags.stream().anyMatch(tag -> tag.getText().contains(pn2));

        if (option1Present && option2Present) {
            System.out.println("Both Option1 and Option2 are selected and visible in textbox.");
        } else {
            throw new AssertionError("Not all selected values are visible in textbox.");
        }
    }
  @Test(priority = 4,dependsOnMethods = {"verifyMultiSelectTextbox"})
    public void verifyRemoveIconForTags() {

        List<WebElement> selectedTags = driver.findElements(
                By.cssSelector(".select2-selection__choice"));

        for (WebElement tag : selectedTags) {
            WebElement removeIcon = tag.findElement(
                    By.cssSelector(".select2-selection__choice__remove"));

            if (removeIcon.isDisplayed()) {
                System.out.println("Remove icon is visible for tag: " + tag.getText());
            }
            else {
                throw new AssertionError("Remove icon not visible for tag: " + tag.getText());
            }
        }
    }
}
