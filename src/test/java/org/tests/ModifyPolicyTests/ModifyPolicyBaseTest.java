package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.tests.BaseTest;

public class ModifyPolicyBaseTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void navigateToModifyPolicy(){

        WebElement policyBtn = wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//aside[@id='sidebar']//ul//li[4]//a")) );
        policyBtn.click();
        WebElement modifyBtn = wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tables-nav']/li[4]")) );
        modifyBtn.click();
    }
}
