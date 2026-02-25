package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US34_ResetUI extends ModifyPolicyBaseTest{

    By searchBtnLocator = By.id("ContentPlaceHolder_Admin_btnSearch");
    By resetBtnLocator = By.id("ContentPlaceHolder_Admin_btnReset");
    By policyStatusLocator = By.id("ContentPlaceHolder_Admin_ddlStatus");

    private String selectByIndexAndReturnText(By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        String selectedText = select.getOptions().get(index).getText();
        select.selectByIndex(index);
        return selectedText;
    }

    @Test(priority = 1)
    public void resetButtonPosition(){
        // Validate Reset Button Position relative to Search Button
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBtnLocator));
        WebElement resetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(resetBtnLocator));

        int searchBtnX = searchBtn.getLocation().getX();
        int resetBtnX = resetBtn.getLocation().getX();

        Assert.assertTrue(resetBtnX > searchBtnX, "Validation Failed: Reset button is NOT to the right of the Search button.");
        System.out.println("Reset button is positioned correctly to the right.");

    }

    @Test(priority = 2)
    public void resetButtonDisplay(){

        WebElement resetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(resetBtnLocator));
        // Validate Grey Reset button displayed
        String buttonColorRgb = resetBtn.getCssValue("background-color");
        String hexColor = Color.fromString(buttonColorRgb).asHex();

        Assert.assertTrue(resetBtn.isDisplayed(), "Reset button is not displayed.");
        Assert.assertTrue(hexColor.toLowerCase().contains("6c75") || hexColor.toLowerCase().contains("7075"),
                "Reset button is not grey. Found: " + hexColor);
        Actions actions = new Actions(driver);
        actions.moveToElement(resetBtn).perform();

        resetBtn = driver.findElement(resetBtnLocator);
        String hoverHex = Color.fromString(resetBtn.getCssValue("background-color")).asHex();
        Assert.assertNotEquals(hexColor, hoverHex, "Color did not change on hover.");



    }

    @Test(priority = 3)
    public void resetEnabled(){
        WebElement resetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(resetBtnLocator));
        // Validate Reset button is enabled
        Assert.assertTrue(resetBtn.isEnabled(), "Reset button is not enabled.");

    }


@Test(priority = 4)
    public void resetButtonDropdownClear(){
        String chosenStatus = selectByIndexAndReturnText(policyStatusLocator, 2);
        System.out.println("Selected Status by Index 2: " + chosenStatus);
        WebElement resetBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(resetBtnLocator));


        // Validate clicking Reset clears dropdown
        resetBtn = wait.until(ExpectedConditions.elementToBeClickable(resetBtnLocator));
        resetBtn.click();
        System.out.println("Reset button clicked.");

        // Re-locate dropdown and check reset state
        WebElement statusDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(policyStatusLocator));
        Select selectStatus = new Select(statusDropdown);

        String selectedValue = selectStatus.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedValue, "All", "Dropdown filter was not reset to default 'All'.");

        System.out.println("Dropdown filter cleared successfully on Modify page.");
    }


}
