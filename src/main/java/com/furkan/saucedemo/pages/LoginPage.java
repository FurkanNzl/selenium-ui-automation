package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import com.furkan.saucedemo.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SauceDemo login page.  URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    // --- Locators ---
    // SauceDemo provides dedicated "data-test" attributes for automation.
    // They stay stable even if the design/CSS changes -> the most reliable locator type.
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Opens the login page. */
    public LoginPage open() {
        driver.get(ConfigReader.getBaseUrl());
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    /** Clicks the login button and returns the Products page. */
    public ProductsPage clickLogin() {
        click(loginButton);
        return new ProductsPage(driver);
    }

    /** Logs in in one step (handy for SUCCESSFUL scenarios). */
    public ProductsPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    /**
     * Fills in the credentials and clicks the button, but stays on this page
     * WITHOUT assuming navigation. Use for failed (negative) scenarios.
     */
    public LoginPage attemptLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
