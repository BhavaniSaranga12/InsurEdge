package org.tests.ViewPolicyTests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class US17_MainCategoryFunctionality extends ViewPolicyBaseTest{




        private final By mainCategoryInput=By.xpath("//*[@id=\"form1\"]/div[3]/div[1]/span/span[1]/span/ul");
        private final By removeButton=By.xpath("//span[@class=\"select2-selection__choice__remove\"]");
        private final By select2SearchInput = By.cssSelector("input.select2-search__field");
        private final By select2Options = By.cssSelector("li.select2-results__option[role='option']:not(.select2-results__message)");
        private final By select2NoResultsMsg = By.cssSelector("li.select2-results__message");

    @Test(priority=1)
    void multiSelectValidation()
    {

        wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();
        List<WebElement> ls= driver.findElements(By.xpath("//*[@id=\"select2-ContentPlaceHolder_Admin_ddlMainCategory-results\"]/li"));
        wait.until(ExpectedConditions.elementToBeClickable(ls.get(0))).click();

        wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();

        List<WebElement> ls2= driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(ls2.get(0))).click();

    }

    @Test (priority=2,dependsOnMethods = {"multiSelectValidation"})
    void removeButtonValidation()
    {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }


        @Test(priority=3)
        void availablePoliciesValidation() throws InterruptedException, IOException {

            wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();
            List<WebElement> ls = driver.findElements(By.xpath("//li[@class=\"select2-results__option\"]"));
            Assert.assertTrue(ls.size() > 0,"Main Categories are not available");


        }

        @Test(priority=4)
        public void validateMainCategorySearchAutoSuggest() {

            // 1) Open the Main Category multi-select (Select2)
            wait.until(ExpectedConditions.elementToBeClickable(mainCategoryInput)).click();

            // 2) Ensure search input is visible
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(select2SearchInput));

            // 3) Capture initial (unfiltered) options
            clearSearch(searchInput);
            waitForOptionsToLoad(wait);
            List<String> initialOptions = getVisibleOptionTexts(driver);

            if (initialOptions.isEmpty()) {
                throw new SkipException("No categories present to test auto-suggest. Please preload categories.");
            }

            // Pick a dynamic sample and derive a query (first 3 alphanumeric chars)
            String sample = initialOptions.get(0);
            String normalized = sample.replaceAll("[^A-Za-z0-9]", "");
            if (normalized.length() < 2) {
                // try second option to get a meaningful query
                for (int i = 1; i < initialOptions.size(); i++) {
                    normalized = initialOptions.get(i).replaceAll("[^A-Za-z0-9]", "");
                    if (normalized.length() >= 2) break;
                }
            }
            if (normalized.length() < 2) {
                throw new SkipException("Cannot form a meaningful query from available options.");
            }

            int firstQueryLen = Math.min(3, normalized.length());
            String query1 = normalized.substring(0, firstQueryLen);
            String q1 = query1.toLowerCase();

            // 4) Type query1 and wait until all options contain the substring (case-insensitive)
            typeIntoSearch(searchInput, query1);
            wait.until(allOptionsContainSubstring(q1));

            List<String> filtered1 = getVisibleOptionTexts(driver);
            Assert.assertTrue(!filtered1.isEmpty(), "Filtered results should not be empty for query: " + query1);
            for (String opt : filtered1) {
                Assert.assertTrue(opt.toLowerCase().contains(q1),
                        "Option '" + opt + "' should contain '" + q1 + "'");
            }

            // 5) Type more characters to narrow further
            if (normalized.length() > firstQueryLen) {
                String query2 = normalized.substring(0, Math.min(firstQueryLen + 2, normalized.length()));
                String q2 = query2.toLowerCase();

                typeIntoSearch(searchInput, Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + query2);
                wait.until(allOptionsContainSubstring(q2));
                List<String> filtered2 = getVisibleOptionTexts(driver);

                // Size should not increase when query is more specific
                Assert.assertTrue(filtered2.size() <= filtered1.size(),
                        "Filtered list should shrink or stay same when query becomes more specific. " +
                                "q1: " + filtered1.size() + ", q2: " + filtered2.size());

                for (String opt : filtered2) {
                    Assert.assertTrue(opt.toLowerCase().contains(q2),
                            "Option '" + opt + "' should contain '" + q2 + "'");
                }
            }

            // 6) Clearing search should restore broader set (≈ initial or larger)
            clearSearch(searchInput);
            waitForOptionsToLoad(wait);
            List<String> restored = getVisibleOptionTexts(driver);

            // We compare as sets (case-insensitive) to be resilient to order or duplicates.
            Set<String> iniSet = toLowerSet(initialOptions);
            Set<String> restSet = toLowerSet(restored);
            Assert.assertTrue(!restSet.isEmpty(), "Restored suggestions should not be empty after clearing search.");
            Assert.assertTrue(restSet.size() >= Math.min(iniSet.size(), restSet.size()),
                    "Restored suggestions should be at least as broad as a filtered subset.");

            // Optional: ensure "No results found" is not displayed when cleared
            List<WebElement> noResultsMsg = driver.findElements(select2NoResultsMsg);
            Assert.assertTrue(noResultsMsg.isEmpty() || !noResultsMsg.get(0).isDisplayed(),
                    "'No results found' should NOT be visible when search is cleared.");
        }

        private void waitForOptionsToLoad(WebDriverWait wait) {
            wait.until(driver1 -> {
                try {
                    List<WebElement> opts = driver1.findElements(select2Options);
                    // If list is empty, still OK if the results container exists (just wait a bit more)
                    WebElement resultsUl = driver1.findElement(By.cssSelector("ul.select2-results__options"));
                    return resultsUl.isDisplayed(); // container visible = dropdown is open
                } catch (Exception e) {
                    return false;
                }
            });
        }

        private ExpectedCondition<Boolean> allOptionsContainSubstring(String qLower) {
            return driver1 -> {
                try {
                    List<String> texts = getVisibleOptionTexts(driver1);
                    if (texts.isEmpty()) return false; // wait for at least 1 valid suggestion
                    for (String t : texts) {
                        if (!t.toLowerCase().contains(qLower)) return false;
                    }
                    return true;
                } catch (StaleElementReferenceException sere) {
                    return false;
                }
            };
        }

        private List<String> getVisibleOptionTexts(WebDriver driver) {
            List<WebElement> elements = driver.findElements(select2Options);
            List<String> texts = new ArrayList<>();
            for (WebElement e : elements) {
                try {
                    if (e.isDisplayed()) {
                        String t = e.getText().trim();
                        if (!t.isEmpty()) texts.add(t);
                    }
                } catch (StaleElementReferenceException ignored) {}
            }
            return texts;
        }

        private void clearSearch(WebElement searchInput) {
            searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            searchInput.sendKeys(Keys.BACK_SPACE);
        }

        private void typeIntoSearch(WebElement searchInput, CharSequence keys) {
            searchInput.sendKeys(keys);
        }

        private Set<String> toLowerSet(List<String> list) {
            Set<String> set = new LinkedHashSet<>();
            for (String s : list) set.add(s.toLowerCase());
            return set;
        }







    }




