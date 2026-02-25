package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US29_MainCategoryUI extends ModifyPolicyBaseTest{
WebElement dropdown;
    @Test(priority = 1, description = "Task: Validate Main Category Title Display")
    public void validateTitle() {
        WebElement main_cat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class=\"form-label\"]")));
        String message = main_cat.getText();

        // Assert is displayed
        Assert.assertTrue(main_cat.isDisplayed(), "The Title is not Displayed.");
        System.out.println("The Title is Displayed: " + message);
    }

    @Test(priority = 2, description = "Task: Validate Dropdown clickability and default option")
    public void validateDropdownStatus() {
        dropdown = driver.findElement(By.xpath("//select[@class='form-select']"));

        // Check Clickable/Enabled
        Assert.assertTrue(dropdown.isEnabled(), "The Dropdown is not clickable.");
        System.out.println("The Dropdown is clickable.");

        Select sel = new Select(dropdown);
        WebElement selected = sel.getFirstSelectedOption();
        String selectedText = selected.getText();

        // Check default selection
        Assert.assertEquals(selectedText, "All", "Default selection is not 'All'");
        System.out.println("The Dropdown has auto-selected option as: " + selectedText);
    }

    @Test(priority = 3, dependsOnMethods = "validateDropdownStatus", description = "Task: Validate Dropdown options and Selection")
    public void validateDropdownSelection() throws InterruptedException {
        Select sel = new Select(dropdown);

        // Validate Option Count
        List<WebElement> list = sel.getOptions();
        int s = list.size();
        System.out.println("The Dropdown has " + s + " options.");
        Assert.assertTrue(s > 0, "Dropdown should have at least one option.");

        // Select Index 2
        int n = 2;
        sel.selectByIndex(n);
        System.out.println("The Option " + (n + 1) + " is selected.");

        // Use CSS to verify the specific child selected
        WebElement selected_1 = driver.findElement(By.cssSelector("#ContentPlaceHolder_Admin_ddlMainCategory > option:nth-child(3)"));
        String option_1 = selected_1.getText();

        Assert.assertNotNull(option_1, "Selected option text is null.");
        System.out.println("The Option " + (n + 1) + " is: " + option_1);

    }
}
