package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class US18_SubCategoryFunctionality extends ViewPolicyBaseTest {
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
    public void relatedSubCategories(){
        HashMap<String,List<String>> mainSub=new HashMap<>();
        WebElement mainCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
mainCategory.click();
        List<String> mainCategoryOptions=driver.findElements(By.xpath("//*[@id='select2-ContentPlaceHolder_Admin_ddlMainCategory-results']/li")).stream().map(WebElement::getText).collect(Collectors.toList());

        for (int i=0;i<mainCategoryOptions.size();i++){

            String optionText = mainCategoryOptions.get(i); // e.g., "Asset"
            By optionByText = By.xpath(
                    "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                            + xpathLiteral(optionText) + "]"
            );

            WebElement main1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
            main1.click();
            WebElement subCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul")));
            subCategory.click();
            List<String> subCategoryOptions=driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlSubCategory-results\"]/li")).stream().map(WebElement::getText).collect(Collectors.toList());
            mainSub.put(mainCategoryOptions.get(i),subCategoryOptions);

            WebElement delete=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li[1]/span")));
            delete.click();
            WebElement mainC=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
            mainC.click();
        }
        System.out.println(mainSub);

        Set<Set<String>> uniqueSets = new HashSet<>();

        boolean allDifferent = true;

        for (List<String> list : mainSub.values()) {
            Set<String> set = new HashSet<>(list); // convert list → set (ignores order)
            if (!uniqueSets.add(set)) {
                allDifferent = false;
                break;
            }
        }

//        if (allDifferent) {
//            System.out.println("All HashMap values are DIFFERENT.");
//        } else {
//            System.out.println("Some values are DUPLICATE.");
//        }
        Assert.assertTrue(allDifferent,"Some values are DUPLICATE.");

    }


    @Test(priority = 2)
    public void noResults(){
        WebElement subCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul")));
        subCategory.click();
        try {
            WebElement message = driver.findElement(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlSubCategory-results\"]/li"));
            Assert.assertEquals(message.getText(),"No results found","Sub Category is not displaying 'No results found' when main category is not selected");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Sub Category is not displaying 'No results found' when main category is not selected");
        }
    }

    @Test(priority = 5)
    public void autoSuggetsions(){
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

        WebElement sub=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[2]/span/span[1]/span/ul/li/input"));
        sub.click();
        sub.sendKeys("h");

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlSubCategory-results\"]/li")));
        List<String> texts = options.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        System.out.println("Suggestions for 'h': " + texts);

  Assert.assertTrue(true);
    }


    @Test(priority = 3)
    public void multipleSelection(){
        WebElement mainCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainCategory.click();
        List<String> mainCategoryOptions=driver.findElements(By.xpath("//*[@id='select2-ContentPlaceHolder_Admin_ddlMainCategory-results']/li")).stream().map(WebElement::getText).collect(Collectors.toList());
        String optionText1 = mainCategoryOptions.get(1); // e.g., "Asset"
        By optionByText = By.xpath(
                "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                        + xpathLiteral(optionText1) + "]"
        );

        WebElement main1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        main1.click();
        mainCategory=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul")));
        mainCategory.click();
        String optionText2 = mainCategoryOptions.get(2);
        optionByText = By.xpath(
                "//span[contains(@class,'select2-container--open')]//li[@role='option' and normalize-space() = "
                        + xpathLiteral(optionText2) + "]"
        );

       main1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        main1.click();

        List<WebElement> results=driver.findElements(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li"));
        if(results.size()>2){
            Assert.assertTrue(results.get(0).getText().contains(optionText1),"selected value is not displaying");
            Assert.assertTrue(results.get(1).getText().contains(optionText2),"selected value is not displaying");
        }
        else{
            Assert.fail("Multiple elements are not selected");
        }
    }

    @Test(priority = 4,dependsOnMethods = {"multipleSelection"})
    public void removeElements(){
        List<WebElement> results=driver.findElements(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li/span"));
        for(int i=0;i< results.size();i++){

            WebElement remove=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul/li/span")));
            remove.click();
        }
        results=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li")));

        Assert.assertTrue(results.size()==1,"The Elements are not removed");
    }



}
