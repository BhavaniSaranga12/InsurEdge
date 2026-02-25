package org.tests.ModifyPolicyTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class US50_SearchFunctionality extends ModifyPolicyBaseTest {



    @Test(priority = 1)
    public void search(){

        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
        List<String> options=sel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        options.remove("All");

        for(int i=0;i<options.size();i++){
            WebElement mainC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]"));
            mainC.click();
            sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            sel.selectByVisibleText(options.get(i));
            Select subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
            List<String> suboptions=subSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
            suboptions.remove("All");
            for (int j=0;j<suboptions.size();j++){
                WebElement subC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]"));
                subC.click();
                subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
                subSel.selectByVisibleText(suboptions.get(j));


                Select policyNameSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
                List<String> policynames=policyNameSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                policynames.remove("All");
                for (int k=0;k<policynames.size();k++){
                    WebElement policyName=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]"));
                    policyName.click();
                    policyNameSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
                    policyNameSel.selectByVisibleText(policynames.get(k));

                    Select policyStatusSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]")));
                    List<String> policyStatus=policyStatusSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                    policyStatus.remove("All");

                    for (int l=0;l< policyStatus.size();l++){
                        WebElement policyStat=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]"));
                        policyStat.click();
                        policyStatusSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]")));
                        policyStatusSel.selectByVisibleText(policyStatus.get(l));

                        WebElement search=driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch"));
                        search.click();
                        try {
                            List<WebElement> table = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr")));
                            if(table.size()==7){
                                int lastindex= table.size()-1;
                                table.remove(lastindex);
                            }
                            table.remove(0);
                            System.out.println(table.size());
                            for(WebElement ele:table){
                                List<String> cols=ele.findElements(By.tagName("td")).stream().map(WebElement::getText).collect(Collectors.toList());
                                System.out.println("table is present");
                                Assert.assertTrue(cols.get(1).equals(options.get(i)) && cols.get(2).equals(suboptions.get(j)) && cols.get(3).equals(policynames.get(k)) && cols.get(7).equals(policyStatus.get(l)),"Noo ");
                              return;
                            }


                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }

        }


    }


    @Test(priority = 2)
    public void searchMessage(){

        Select sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
        List<String> options=sel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        options.remove("All");

        for(int i=0;i<options.size();i++){
            WebElement mainC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]"));
            mainC.click();
            sel=new Select(driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlMainCategory\"]")));
            sel.selectByVisibleText(options.get(i));
            Select subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
            List<String> suboptions=subSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
            suboptions.remove("All");
            for (int j=0;j<suboptions.size();j++){
                WebElement subC=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]"));
                subC.click();
                subSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlSubCategory\"]")));
                subSel.selectByVisibleText(suboptions.get(j));


                Select policyNameSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
                List<String> policynames=policyNameSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                policynames.remove("All");
                for (int k=0;k<policynames.size();k++){
                    WebElement policyName=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]"));
                    policyName.click();
                    policyNameSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlPolicyName\"]")));
                    policyNameSel.selectByVisibleText(policynames.get(k));

                    Select policyStatusSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]")));
                    List<String> policyStatus=policyStatusSel.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
                    policyStatus.remove("All");

                    for (int l=0;l< policyStatus.size();l++){
                        WebElement policyStat=driver.findElement(By.xpath("//select[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]"));
                        policyStat.click();
                        policyStatusSel=new Select(driver.findElement(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_ddlStatus\"]")));
                        policyStatusSel.selectByVisibleText(policyStatus.get(l));

                        WebElement search=driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch"));
                        search.click();
                        try {
                            List<WebElement> table = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"ContentPlaceHolder_Admin_gvPolicies\"]/tbody/tr")));
                            if(table.size()==7){
                                int lastindex= table.size()-1;
                                table.remove(lastindex);
                            }
                            table.remove(0);
                            System.out.println(table.size());
                            for(WebElement ele:table){
                                List<String> cols=ele.findElements(By.tagName("td")).stream().map(WebElement::getText).collect(Collectors.toList());
                                System.out.println("table is present");

                            }


                        } catch (Exception e) {
                            System.out.println(e);
                            Assert.fail("No recors/data found message is not present when no data is returned");
                            return;
                        }
                    }
                }
            }

        }


    }
}




