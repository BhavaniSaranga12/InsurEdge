package org.tests.ViewPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class US05_PolicyStatusUI extends ViewPolicyBaseTest{

    private String xpathLiteral(String s) {
        if (!s.contains("'")) {
            return "'" + s + "'";
        }
        if (!s.contains("\"")) {
            return "\"" + s + "\"";
        }
        // Contains both ' and "
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < s.length(); i++) {
            String ch = String.valueOf(s.charAt(i));
            if ("'".equals(ch)) sb.append("\"").append(ch).append("\"");
            else sb.append("'").append(ch).append("'");
            if (i < s.length() - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }


    @Test(priority = 1)
    public void labelValidation(){
        boolean isPolicyStatusPresent = driver.findElements(By.xpath("//label[normalize-space(.)='Policy Status']")).size()>0;

        Assert.assertTrue(isPolicyStatusPresent,"Policy Status label is NOT present.");

    }

    @Test(priority = 2)
    public void textboxIsEmpty(){

        WebElement input=driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/input"));

        Assert.assertTrue(input.isEnabled() && input.getText().isEmpty(),"Input is not clickable & Input is not empty");
    }


    @Test(priority = 3)
    public void multipleSelection(){
        WebElement input= driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul"));
        input.click();

        List<String> statusOptions=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlPolicyStatus-results\"]/li"))).stream().map(WebElement::getText).collect(Collectors.toList());
        String optionText1 = statusOptions.get(1);
        By optionByText = By.xpath(
                "//ul[contains(@class,'select2-results__options')]//li[@role='option' and normalize-space() = "
                        +xpathLiteral(optionText1)+ "]"
        );

        WebElement status1=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        status1.click();
        input= driver.findElement(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul"));
        input.click();
        String optionText2 = statusOptions.get(0);
        optionByText = By.xpath(
                "//ul[contains(@class,'select2-results__options')]//li[@role='option' and normalize-space() = "
                        +xpathLiteral(optionText2)+ "]"
        );

        WebElement status2=wait.until(ExpectedConditions.elementToBeClickable((optionByText)));
        status2.click();

        List<WebElement> results=driver.findElements(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li"));
        if(results.size()>2){
            Assert.assertTrue(results.get(0).getText().contains(optionText2),"selected value is not displaying");
            Assert.assertTrue(results.get(1).getText().contains(optionText1),"selected value is not displaying");
        }
        else{
            Assert.fail("Multiple elements are not selected");
        }

    }

    @Test(priority = 4,dependsOnMethods = {"multipleSelection"})
    public void removeSelection(){
        List<WebElement> results=driver.findElements(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li/span"));
        System.out.println(results.size());
        for(int i=0;i< results.size();i++){

            WebElement remove=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li[1]/span")));
            remove.click();
        }
         results=wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"form1\"]/div[3]/div[4]/span/span[1]/span/ul/li")));

        Assert.assertTrue(results.size()==1,"The Elements are not removed");
    }

}
