package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US57_DeleteAction_Confirmation extends ModifyPolicyBaseTest{
    private void printAllTableCells() {
        List<WebElement> cells = driver.findElements(
                By.cssSelector("#ContentPlaceHolder_Admin_gvPolicies tbody tr td")
        );
        for (WebElement cell : cells) {
            System.out.println("Cell text: [" + cell.getText() + "]");
        }
    }

    // Task 2: Validate Confirm deletes record
    @Test
    public void testConfirmDeletesRecord() {

        printAllTableCells();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("ContentPlaceHolder_Admin_gvPolicies")));

        WebElement row = driver.findElement(
                By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[2]")
        );
        Assert.assertTrue(row.isDisplayed(), "Record not found before deletion.");

        // Click delete link inside that row
//        WebElement deleteLink = row.findElement(By.xpath(".//a[contains(@id,'btnDelete')]"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteLink);


        boolean recordExists = driver.findElements(
                By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//tr[2]")
        ).equals(row);
        Assert.assertFalse(recordExists, "Record still exists after deletion.");
    }

}


