package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US08_TableHeaderUI extends ViewPolicyBaseTest{


    @Test(priority = 1)
    public void tableDisplay() {
        // Task 1: Check if table is displayed
        WebElement table = driver.findElement(By.id("ContentPlaceHolder_Admin_gvPolicies"));
        Assert.assertTrue(table.isDisplayed(), "Table not visible!");

    }


    @Test(priority = 2,dependsOnMethods = {"tableDisplay"})
    public void tableColumnCount() {
        // Task 2: Check column count
        List<WebElement> headers = driver.findElements(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[1]//th"));
        Assert.assertEquals(headers.size(), 9, "Column count should be 9");

    }

    @Test(priority = 3,dependsOnMethods = {"tableDisplay","tableColumnCount"})
    public void tableColumnNames() {
        // Task 3: Check column names in order
        List<WebElement> headers = driver.findElements(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr[1]//th"));
        String[] expected = {"ID", "Main Category", "Sub Category", "Policy Name", "Sum Assured", "Premium", "Tenure (Years)", "Status", "Created On"};

        for (int i = 0; i < expected.length; i++) {
            String actual = headers.get(i).getText();
            Assert.assertEquals(actual, expected[i], "Column mismatch at index " + i);
        }
    }





}
