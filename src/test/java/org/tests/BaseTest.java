package org.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    public static WebDriverWait wait;


    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
        wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        // Optional: set a global implicit wait (prefer explicit waits in tests)
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://qeaskillhub.cognizant.com/LoginPage?logout=true");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement username = driver.findElement(By.id("txtUsername"));
        username.sendKeys("admin_user");

        WebElement password = driver.findElement(By.id("txtPassword"));
        password.sendKeys("testadmin");

        WebElement loginBtn = driver.findElement(By.id("BtnLogin"));
        loginBtn.click();
    }





    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        // Quit the driver after each test method
        if (driver != null) {
            driver.quit();
        }
    }

}
