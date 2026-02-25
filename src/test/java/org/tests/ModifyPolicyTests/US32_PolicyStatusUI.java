package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US32_PolicyStatusUI extends ModifyPolicyBaseTest {
WebElement dropdown;
    @Test(priority = 1, description = "Step 1: Validate Policy Status Label Display")
    public void validateLabelDisplay() {
        WebElement policy_stat = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"AdminModifyPolicy\"]/div[4]/div/div[1]/div[4]/label")));

        String labelText = policy_stat.getText();

        // Assertions ensure the test fails if the element isn't there
        Assert.assertTrue(policy_stat.isDisplayed(), "Policy Status Title is not displayed!");
        System.out.println("The Title is Displayed: " + labelText);
    }

    @Test(priority = 2, description = "Step 2: Validate Dropdown functionality and Default Value")
    public void validateDropdownInitialState() {
        dropdown = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]"));

        // Validate Clickable
        Assert.assertTrue(dropdown.isEnabled(), "The Dropdown is not enabled/clickable.");
        System.out.println("The Dropdown is clickable.");

        Select sel = new Select(dropdown);
        WebElement selected = sel.getFirstSelectedOption();
        String selectedText = selected.getText();

        // Validate Default Selection
        Assert.assertEquals(selectedText, "All", "The auto-selected option is NOT 'All'.");
        System.out.println("The Dropdown has auto-selected option as: " + selectedText);
    }

    @Test(priority = 3, dependsOnMethods = "validateDropdownInitialState", description = "Step 3: Validate Option Count and Selection")
    public void validateDropdownSelection() throws InterruptedException {
        Select sel = new Select(dropdown);

        // Validate Options List
        List<WebElement> list = sel.getOptions();
        int s = list.size();
        System.out.println("The Dropdown has " + s + " options.");
        Assert.assertTrue(s > 0, "Dropdown has no options available.");

        // Select by Index
        int n = 2;
        sel.selectByIndex(n);
        System.out.println("The Option " + (n + 1) + " is selected.");

        // Verify text of the selected option
        WebElement selected_1 = driver.findElement(By.cssSelector("#ContentPlaceHolder_Admin_ddlStatus > option:nth-child(3)"));
        String option_1 = selected_1.getText();

        Assert.assertNotNull(option_1, "Selected option text is null.");
        System.out.println("The Option " + (n + 1) + " is: " + option_1);


    }

}
