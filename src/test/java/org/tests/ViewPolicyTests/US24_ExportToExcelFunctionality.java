package org.tests.ViewPolicyTests;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class US24_ExportToExcelFunctionality extends ViewPolicyBaseTest {
    private static final DataFormatter FORMATTER = new DataFormatter();

    private static String normalize(String s, boolean unescapeHtml) {
        if (s == null) return "";
        if (unescapeHtml) {
            s = s.replace("&quot;", "\"")
                    .replace("&apos;", "'")
                    .replace("&amp;",  "&")
                    .replace("&lt;",   "<")
                    .replace("&gt;",   ">")
                    .replace("&#39;",  "'")
                    .replace("&#x27;", "'");
        }
        return s.replace('\u00A0', ' ')
                .trim()
                .replaceAll("\\s+", " ");
    }


    @Test(priority = 1)
    public void testExportToExcelDownload() throws InterruptedException, FileNotFoundException,FileNotFoundException, IOException {
        File folder = new File("C:\\Users\\2461307\\Downloads");
        long beforeDownload = Arrays.stream(folder.listFiles())
                .mapToLong(File::lastModified)
                .max()
                .orElse(0);
        System.out.println(beforeDownload);


        WebElement exportBtn = driver.findElement(By.id("ContentPlaceHolder_Admin_btnExportExcel"));
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
        Assert.assertTrue(latestFile.getName().endsWith(".xlsx"), "Downloaded file is not in .xlsx format");
        Assert.assertTrue(latestFile.getName().contains("Filtered_Policies"), "Downloaded file name is not matching");

        List<List<String>> tabledata=new ArrayList<>();
        List<WebElement> rows=driver.findElements(By.tagName("tr"));
        for(WebElement row:rows){
            List<WebElement> cols=row.findElements(By.xpath("./th|./td"));
            //System.out.println(cols.size());
            List<String> rowdata=new ArrayList<>();
            for(WebElement col:cols){
                rowdata.add(col.getText());
            }
            tabledata.add(rowdata);
        }
        //System.out.println(tabledata.get(1));
        FileInputStream fis = new FileInputStream(latestFile);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // first sheet

        List<List<String>> excelData = new ArrayList<>();

        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                //rowData.add(cell.toString().trim()); // convert cell to string

                String val = FORMATTER.formatCellValue(cell);         // display value
                rowData.add(normalize(val, /*unescapeHtml=*/true));

            }
            excelData.add(rowData);
        }
        workbook.close();

        Assert.assertEquals(excelData.size(), tabledata.size(), "Row count mismatch");

        for (int i = 0; i < tabledata.size(); i++) {
            List<String> uiRow = tabledata.get(i);
            List<String> excelRow = excelData.get(i);

            Assert.assertEquals(excelRow.size(), uiRow.size(), "Column count mismatch at row " + i);

            for (int j = 0; j < uiRow.size(); j++) {
                Assert.assertEquals(excelRow.get(j), uiRow.get(j),
                        "Mismatch at row " + i + ", column " + j);
                System.out.println(excelRow.get(j)+" "+uiRow.get(j));
            }
        }


    }



}
