package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class US52_PaginationFunctionality extends ModifyPolicyBaseTest {

    By tableFirstElementId = By.xpath("//tbody//tr[2]//td[1]");
    By page2 = By.xpath("//a[contains(@href,'Page$2')]");
    By mainCategoryDropdown = By.xpath("//select[@id='ContentPlaceHolder_Admin_ddlMainCategory']");
    By subCategoryDropdown = By.xpath("//select[@id='ContentPlaceHolder_Admin_ddlSubCategory']");
    By policyDropdown = By.xpath("/html/body/main/form/div[4]/div/div[1]/div[3]/select");
    By policyStatus = By.xpath("//select[@id='ContentPlaceHolder_Admin_ddlStatus']");
    By resetButton = By.xpath("//*[@id='ContentPlaceHolder_Admin_btnReset']");
    By searchbtn = By.xpath("//input[contains(@id,'btnSearch')]");

    private String getFirstRowId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tableFirstElementId)).getText();
    }

    @Test(priority = 1)
    void paginationValidation() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");

        String firstId = getFirstRowId();
        wait.until(ExpectedConditions.elementToBeClickable(page2)).click();
        String secondId = getFirstRowId();

        Assert.assertNotEquals(firstId, secondId,
                "First Row id should change on next page, but it is same");
    }

    static int pageCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        int pages = 0;
        try {
            WebElement tbody = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody")));
            List<WebElement> tr = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr"));
            List<WebElement> td = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td"));

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");

            if (tbody.isDisplayed()) {
                if (tr.size() > 6) {
                    if (td.size() > 10) {
                        wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td[11]"))).click();
                        List<WebElement> nexttd = driver.findElements(By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td"));
                        int last = nexttd.size() - 1;
                        pages = Integer.parseInt(nexttd.get(last).getText());
                    } else {
                        pages = td.size();
                    }
                } else {
                    pages = 1;
                }
            }
        } catch (Exception e) {
            pages = 0;
        }
        return pages;
    }

    @Test(priority = 2)
    void pageCountValidation() {
        int prevPageNumber = pageCount();
        List<Integer> pageNumbers = new ArrayList<>();
        pageNumbers.add(prevPageNumber);

        Select select = new Select(driver.findElement(mainCategoryDropdown));
        List<WebElement> options = select.getOptions();

        for (int i = 2; i < options.size(); i++) {
            select.selectByIndex(i);
            Select select2 = new Select(driver.findElement(subCategoryDropdown));
            List<WebElement> options2 = select2.getOptions();

            for (int j = 1; j < options2.size(); j++) {
                select2.selectByIndex(j);
                Select select3 = new Select(driver.findElement(policyDropdown));
                List<WebElement> options3 = select3.getOptions();

                for (int k = 1; k < options3.size(); k++) {
                    select3.selectByIndex(k);
                    Select select4 = new Select(driver.findElement(policyStatus));
                    List<WebElement> options4 = select4.getOptions();

                    for (int m = 1; m < options4.size(); m++) {
                        select4.selectByIndex(m);
                        driver.findElement(searchbtn).click();
                        pageNumbers.add(pageCount());
                    }
                }
            }
        }
        System.out.println(pageNumbers);
    }

    @Test(priority = 3)
    void ellipsisValidation() {
        driver.findElement(resetButton).click();
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");

        List<WebElement> nexttd = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td")));
        int last = nexttd.size() - 1;

        Assert.assertEquals(nexttd.get(last).getText(), "...", "Last pagination element should be ellipsis");
    }

    @Test(priority = 4)
    void additionalPagesValidation() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td[11]"))).click();

        String number = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='ContentPlaceHolder_Admin_gvPolicies']/tbody/tr[7]/td/table/tbody/tr/td[2]"))).getText();

        System.out.println("Page number after clicking: " + number);
    }
}
