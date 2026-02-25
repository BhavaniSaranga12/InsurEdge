package org.tests.ViewPolicyTests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

public class US12_ExportToExcelUI extends ViewPolicyBaseTest{



    @Test(priority = 1)
    public  void exportToExcelEnabled() throws InterruptedException {
        WebElement mainCategoryInput= driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li/input"));				// mainCategoryInput tag
        WebElement subCategoryInput=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li/input"));
        WebElement policyNameButton=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul/li/input"));
        WebElement policyStatusButton=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/input"));
        WebElement searchbtn=driver.findElement(By.xpath("//input[contains(@id,\"btnSearch\")]"));

        //for main category
        wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();
        List<WebElement> ls= driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        for (int i = 0; i < ls.size(); i++) {
            WebElement option = driver.findElements(By.xpath("//li[@class='select2-results__option']")).get(i);
            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
            // for sub category
            wait.until(ExpectedConditions.elementToBeClickable(subCategoryInput)).click();
            List<WebElement> ls2 = driver.findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
            for (int j = 0; j < ls2.size(); j++) {
                wait.until(ExpectedConditions.elementToBeClickable(ls2.get(j))).click();
                // for policy name
                wait.until(ExpectedConditions.elementToBeClickable(policyNameButton)).click();
                List<WebElement> ls3 = driver
                        .findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
                for (int k = 0; k < ls3.size(); k++) {
                    wait.until(ExpectedConditions.elementToBeClickable(ls3.get(k))).click();
                    // for policy status
                    wait.until(ExpectedConditions.elementToBeClickable(policyStatusButton)).click();
                    List<WebElement> ls4 = driver
                            .findElements(By.xpath("//li[contains(@class,\"select2-results__option\")]"));
                    for (int s = 0; s < ls4.size(); s++) {
                        wait.until(ExpectedConditions.elementToBeClickable(ls4.get(s))).click();

                        searchbtn.click();

                        List<WebElement> trows=driver.findElements(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr"));
                        if (trows.size() > 2) {
                            continue;
                        } else {
                            boolean Exe = driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_btnExportExcel\"]")).isEnabled();
                            if (Exe)
                                System.out.println("The Export to Excel button is Enabled when no records are available in the table.");
                            else
                                System.out.println("The Export to Excel button is NOT disabled when no records are available in the table.");
                            driver.close();
                            return;
                        }


                    }
                }
            }
        }

    }


}
