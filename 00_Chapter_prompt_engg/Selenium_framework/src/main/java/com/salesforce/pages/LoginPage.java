package com.salesforce.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMeCheckbox;

    @FindBy(xpath = "//div[@id='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) throws Exception {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameInput));
            usernameInput.clear();
            usernameInput.sendKeys(username);
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new Exception("Failed to interact with username input field: " + e.getMessage(), e);
        }
    }

    public void enterPassword(String password) throws Exception {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            passwordInput.clear();
            passwordInput.sendKeys(password);
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new Exception("Failed to interact with password input field: " + e.getMessage(), e);
        }
    }

    public void selectRememberMe() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
            if (!rememberMeCheckbox.isSelected()) {
                rememberMeCheckbox.click();
            }
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new Exception("Failed to interact with remember me checkbox: " + e.getMessage(), e);
        }
    }

    public void clickLogin() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new Exception("Failed to click login button: " + e.getMessage(), e);
        }
    }

    public void doLogin(String user, String pass) throws Exception {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }

    public String getErrorMessage() throws Exception {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText().trim();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            throw new Exception("Failed to retrieve login error message: " + e.getMessage(), e);
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameInput));
            return usernameInput.isDisplayed() && loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
