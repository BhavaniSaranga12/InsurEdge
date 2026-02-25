package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

public class US42_UpdatePolicyFormUI extends ModifyPolicyBaseTest{
    String parentWindow;
    @BeforeClass
            public void navigateToUpdate(){
        WebElement updateBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0"));
        updateBtn.click();
parentWindow= driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String win : allWindows) {
            if (!win.equals(parentWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }

        driver.manage().window().maximize();
    }

    @AfterClass
    public void closeUpdate(){
        driver.close();
        driver.switchTo().window(parentWindow);
    }



    @Test(priority = 1)
    public void validateTitle() {
        WebElement title = driver.findElement(By.xpath("//h3[@class='mb-3 text-primary']"));
        Assert.assertEquals(title.getText(), "Update Policy", "Form title mismatch!");
    }

    @Test(priority = 2)
    public void validateFieldOrder() {
        String[] expectedOrder = {
                "Main Category:",
                "Sub Category:",
                "Policy Name:",
                "Sum Assured:",
                "Premium:",
                "Tenure (Years):",
                "Status:"
        };

        List<WebElement> labels = driver.findElements(By.xpath("//label[@class='form-label']"));
        for (int i = 0; i < expectedOrder.length; i++) {
            Assert.assertEquals(labels.get(i).getText().trim(), expectedOrder[i], "Field order mismatch at index " + i);
        }
    }

    @Test(priority = 3)
    public void validateLabelsAboveFields() {
        List<WebElement> labels = driver.findElements(By.xpath("//label[@class='form-label']"));

        for (WebElement label : labels) {
            String forAttr = label.getAttribute("for");
            WebElement input;
            if (forAttr != null && !forAttr.isEmpty()) {
                input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(forAttr)));
            } else {
                input = label.findElement(By.xpath("following-sibling::input | following-sibling::select"));
            }

            int labelY = label.getLocation().getY();
            int inputY = input.getLocation().getY();

            Assert.assertTrue(labelY < inputY, "Label is not above its field: " + label.getText());
        }
    }

    @Test(priority = 4)
    public void validateButtons() {
        WebElement updateBtn = driver.findElement(By.id("btnUpdate"));
        WebElement cancelBtn = driver.findElement(By.id("btnCancel"));
        SoftAssert sa=new SoftAssert();
        sa.assertTrue(updateBtn.isDisplayed(), "Update button not displayed");
        sa.assertTrue(cancelBtn.isDisplayed(), "Cancel button not displayed");
        sa.assertTrue(updateBtn.isEnabled(), "Update button not enabled");
        sa.assertTrue(cancelBtn.isEnabled(), "Cancel button not enabled");
        sa.assertAll();
    }
}
