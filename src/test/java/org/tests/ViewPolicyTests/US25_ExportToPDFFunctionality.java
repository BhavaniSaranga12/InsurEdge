package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class US25_ExportToPDFFunctionality extends ViewPolicyBaseTest{
    @Test(priority = 1)
    public void testExportToPDFDownload() throws InterruptedException{
        File folder = new File("C:\\Users\\2461307\\Downloads");
        long beforeDownload = Arrays.stream(folder.listFiles())
                .mapToLong(File::lastModified)
                .max()
                .orElse(0);
        System.out.println(beforeDownload);
        WebElement exportBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportPDF"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", exportBtn);


        wait.until(ExpectedConditions.elementToBeClickable(exportBtn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", exportBtn);


        Thread.sleep(5000);

        // 4. Find the newest file after download
        File latestFile = Arrays.stream(folder.listFiles())
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
        System.out.println(latestFile.lastModified());
        System.out.println(latestFile.getName());


        Assert.assertNotNull(latestFile, "No file found in download folder");
        Assert.assertTrue(latestFile.lastModified() > beforeDownload, "No new file was downloaded");
        Assert.assertTrue(latestFile.getName().endsWith(".pdf"), "Downloaded file is not in .pdf format");
        Assert.assertTrue(latestFile.getName().contains("Filtered_Policies"), "Downloaded file name is not matching");

    }
}
