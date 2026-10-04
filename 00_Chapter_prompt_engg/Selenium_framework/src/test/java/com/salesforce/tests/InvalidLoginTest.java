package com.salesforce.tests;

import com.salesforce.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class InvalidLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private static final String TARGET_URL = "https://login.salesforce.com/?locale=in";

    @BeforeMethod
    public void setUp() throws Exception {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            driver.get(TARGET_URL);
            loginPage = new LoginPage(driver);
        } catch (Exception e) {
            throw new Exception("SetUp failed in InvalidLoginTest: " + e.getMessage(), e);
        }
    }

    @Test
    public void testInvalidLoginDisplaysErrorBanner() throws Exception {
        try {
            Assert.assertTrue(loginPage.isLoginPageLoaded(), "Salesforce login page failed to load elements.");
            loginPage.doLogin("unauthorized_user@domain.com", "InvalidSecretPass456!");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Authentication error message was not displayed.");
            String actualErrorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(
                actualErrorMessage.contains("Please check your username and password") || actualErrorMessage.length() > 0,
                "Error message validation failed. Actual message: " + actualErrorMessage
            );
        } catch (AssertionError ae) {
            throw ae;
        } catch (Exception e) {
            Assert.fail("Invalid login verification encountered an exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            throw new Exception("TearDown failed in InvalidLoginTest: " + e.getMessage(), e);
        }
    }
}
