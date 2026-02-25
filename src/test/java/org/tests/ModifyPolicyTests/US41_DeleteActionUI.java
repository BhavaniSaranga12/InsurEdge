package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class US41_DeleteActionUI extends ModifyPolicyBaseTest{


    @Test(priority = 1)
    public void deleteActionPosition(){
        // Validate Icons and capture X-axis positions
        // Locate Update (Yellow) and Delete (Red) buttons
        WebElement updateBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0")));
        WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ContentPlaceHolder_Admin_gvPolicies_btnDelete_0")));

        // Get X-coordinates (Location on the X-axis)
        Point updateLocation = updateBtn.getLocation();
        Point deleteLocation = deleteBtn.getLocation();

        int xUpdate = updateLocation.getX();
        int xDelete = deleteLocation.getX();
        System.out.println("X-Axis Position - Update Button (Yellow): " + xUpdate);
        System.out.println("X-Axis Position - Delete Button (Red): " + xDelete);

        // Verify Red icon is to the right of the Yellow icon (xDelete should be > xUpdate)
        Assert.assertTrue(xDelete > xUpdate, "Delete button is not positioned to the right of the Update button.");


    }

    @Test(priority = 2)
    public void deleteActionEnabled(){
        WebElement deleteBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ContentPlaceHolder_Admin_gvPolicies_btnDelete_0")));

        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
        boolean isClickable = deleteBtn.isEnabled() && deleteBtn.isDisplayed();
        Assert.assertTrue(isClickable, "Red Delete button is not clickable.");
        System.out.println("Clickability Status: The Red Delete button is fully clickable.");
    }


    }
