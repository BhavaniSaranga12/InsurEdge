package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class US10_TableDataUI extends ViewPolicyBaseTest{

    @Test(priority = 1)
    public void verifyData() {
        WebElement table = driver.findElement(By.id("ContentPlaceHolder_Admin_gvPolicies"));
        List<WebElement> rows = table.findElements(By.xpath("//table[@id='ContentPlaceHolder_Admin_gvPolicies']//tr"));

        String[] expectedHeaders = {
                "ID", "Main Category", "Sub Category", "Policy Name",
                "Sum Assured", "Premium", "Tenure (Years)", "Status", "Created On"
        };

        boolean dataComplete = true;

        for (int r = 0; r < rows.size(); r++) {
            List<WebElement> cells = rows.get(r).findElements(By.tagName("td"));
            for (int c = 0; c < cells.size(); c++) {
                String cellText = cells.get(c).getText().trim();
                if (cellText.isEmpty()) {
                    dataComplete = false;
                    System.out.println("Empty cell at Row " + (r + 1) + ", Column " + expectedHeaders[c]);
                }
            }
        }


        Assert.assertTrue(dataComplete,"All columns does contain complete data. Null fields found.");
    }


}
