package org.tests.ModifyPolicyTests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Set;

public class US55_UpdateButtonFunctionality extends ModifyPolicyBaseTest{

    JavascriptExecutor js;
    String parentWindow;
    @BeforeMethod
    public void navigateToUpdate(Method method){
        js=(JavascriptExecutor) driver;
        if (method.getName().equals("testRejectNonNumericInput") || method.getName().equals("testSuccessMessageAfterSave"))
        {
            WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0")));
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

    }




    @Test(priority = 1)
    public void testRejectNonNumericInput() {
        WebElement sumAssured = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtSumAssured")));
        WebElement premium = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtPremium")));
        WebElement tenure = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtTenure")));

        sumAssured.clear();
        js.executeScript("arguments[0].value='abc';", sumAssured);

        premium.clear();
        js.executeScript("arguments[0].value='xyz';", premium);

        tenure.clear();
        js.executeScript("arguments[0].value='@@@';", tenure);

        WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='btnUpdate' and @class='btn btn-primary']")));
        js.executeScript("arguments[0].scrollIntoView(true);", updateBtn);
        js.executeScript("arguments[0].click();", updateBtn);


        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assert.assertTrue(alertText.contains("Policy updated successfully!"), "Popup text validation failed");
        alert.accept();
        driver.switchTo().window(parentWindow);
    }


    @Test(priority = 2)
    public void testSuccessMessageAfterSave() {

        WebElement sumAssured = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtSumAssured")));
        WebElement premium = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtPremium")));
        WebElement tenure = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txtTenure")));
        System.out.println(sumAssured.getAttribute("value"));
        System.out.println(premium.getAttribute("value"));
        System.out.println(tenure.getAttribute("value"));

        sumAssured.clear();
        js.executeScript("arguments[0].value='300000';", sumAssured);

        premium.clear();
        js.executeScript("arguments[0].value='3000';", premium);

        tenure.clear();
        js.executeScript("arguments[0].value='3';", tenure);

        System.out.println(sumAssured.getAttribute("value"));
        System.out.println(premium.getAttribute("value"));
        System.out.println(tenure.getAttribute("value"));
//        WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//input[@id='btnUpdate' and @class='btn btn-primary']")));
//        js.executeScript("arguments[0].scrollIntoView(true);", updateBtn);
//        js.executeScript("arguments[0].click();", updateBtn);

        WebElement updateBtn = driver.findElement(
                By.xpath("//input[@id='btnUpdate' and @class='btn btn-primary']")
        );
        js.executeScript("arguments[0].scrollIntoView(true);", updateBtn);
        wait.until(ExpectedConditions.elementToBeClickable(updateBtn));
        js.executeScript("arguments[0].click();", updateBtn);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println(alertText);

        Assert.assertTrue(alertText.contains("Policy updated successfully!"), "Popup text validation failed");
        alert.accept();
        System.out.println("Alert is accepted");
        driver.switchTo().window(parentWindow);





    }

    @Test(priority = 3)
    public void testRedirectionToModifyPolicyPage() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/AdminModifyPolicy"), "User should be redirected to Modify Policy page");
    }


    @Test(priority = 4)
    public void testUpdatedValuesVisibleInMainTable() throws InterruptedException {


        WebElement updatedRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[2]")));
        System.out.println(updatedRow.getText());
        System.out.println(driver.findElement(By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[2]/td[1]")).getText());


        String sumAssured = updatedRow.findElement(By.xpath("td[5]")).getText();
        String premium = updatedRow.findElement(By.xpath("td[6]")).getText();
        String tenure = updatedRow.findElement(By.xpath("td[7]")).getText();
        System.out.println(updatedRow.getText());


        Assert.assertEquals(sumAssured, "300000");
        Assert.assertEquals(premium, "3000");
        Assert.assertEquals(tenure, "3");
    }
}
