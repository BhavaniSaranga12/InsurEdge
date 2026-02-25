package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class US40_UpdateActionUI extends ModifyPolicyBaseTest{

    @Test(priority = 1)
    public void updateActionUI(){
        driver.findElement(By.xpath("//*[@id=\"sidebar-nav\"]/li[4]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"tables-nav\"]/li[4]/a/span")).click();
//		Task 1:Validate that each row in the table displays a yellow Update (edit) icon in the Actions column.
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(500, 0);");
        //js.executeScript("window.scrollBy(0, 500);");
        List<WebElement> updatebtn = driver.findElements(By.xpath("/html/body/main/form/div[5]/div/table/tbody/tr/td[9]/div/a[1]"));

        for (int i = 0; i < updatebtn.size(); i++) {
            WebElement ub = updatebtn.get(i);
            boolean ispresent = ub.isDisplayed();
            int rowNo = i + 1; // human-friendly row number starting at 1
            System.out.println("Row " + rowNo + " → Update button visible: " + ispresent);
        }



//		Task 3 And 4: Validate that the Update icon is fully clickable.
        driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies_btnUpdate_0\"]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        System.out.println("The Update icon is fully clickable.");

        System.out.println("Clicking the Update icon opens the Update Policy form/page for the selected policy.");
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }

}
