package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.tests.BaseTest;

public class ViewPolicyBaseTest extends BaseTest {
    @BeforeClass(alwaysRun = true)
    public void navigateToViewPolicy(){
        WebElement policyBtn = wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//aside[@id='sidebar']//ul//li[4]//a")) );
        policyBtn.click();
        WebElement viewBtn = wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tables-nav']/li[3]")) );
        viewBtn.click();
    }
}
