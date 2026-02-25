package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class US19_PolicyNameFunctionality extends ViewPolicyBaseTest {

    private String xpathLiteral(String s) {
        if (!s.contains("'")) {
            return "'" + s + "'";
        } else if (!s.contains("\"")) {
            return "\"" + s + "\"";
        } else {
            // Contains both ' and ", use concat()
            StringBuilder sb = new StringBuilder("concat(");
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                String part = String.valueOf(chars[i]);
                if ("'".equals(part)) {
                    sb.append("\"").append(part).append("\"");
                } else {
                    sb.append("'").append(part).append("'");
                }
                if (i < chars.length - 1) sb.append(", ");
            }
            sb.append(")");
            return sb.toString();
        }
    }



    @Test(priority = 1)
    public void selectableOptions() throws InterruptedException {

        WebElement mainCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainCategory.click();
        List<String> mainCategoryOptions=driver.findElements(By.xpath("//*[@id='select2-ContentPlaceHolder_Admin_ddlMainCategory-results']/li")).stream().map(WebElement::getText).collect(Collectors.toList());

        String optionText = mainCategoryOptions.get(1); // e.g., "Asset"
        By optionByText = By.xpath(
                "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                        + xpathLiteral(optionText) + "]"
        );

        WebElement main1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        main1.click();
        WebElement subCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul")));
        subCategory.click();
        List<String> subCategoryOptions=driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlSubCategory-results\"]/li")).stream().map(WebElement::getText).collect(Collectors.toList());
        optionText = subCategoryOptions.get(0); // e.g., "Asset"
        optionByText = By.xpath(
                "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                        + xpathLiteral(optionText) + "]"
        );
        WebElement sub1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        sub1.click();

        WebElement policy=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul")));
        policy.click();
        List<String> policynameOptions1=driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlPolicyName-results\"]/li")).stream().map(WebElement::getText).collect(Collectors.toList());
        WebElement delete=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li[1]/span")));
        delete.click();

        subCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul")));
        subCategory.click();
        List<String> subCategoryOptions2=driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlSubCategory-results\"]/li")).stream().map(WebElement::getText).collect(Collectors.toList());
        optionText = subCategoryOptions2.get(1); // e.g., "Asset"
        optionByText = By.xpath(
                "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                        + xpathLiteral(optionText) + "]"
        );
        WebElement sub2=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        sub2.click();

        WebElement policy2=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[3]/span/span[1]/span/ul")));
        policy2.click();
        List<String> policynameOptions2=driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlPolicyName-results\"]/li")).stream().map(WebElement::getText).collect(Collectors.toList());


        Assert.assertTrue(!policynameOptions1.equals(policynameOptions2),"Policy names are changing according to the selection of main and sub categories");





    }

}
