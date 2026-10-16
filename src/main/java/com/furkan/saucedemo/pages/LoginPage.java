package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import com.furkan.saucedemo.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SauceDemo giris (login) sayfasi.  URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    // --- Locator'lar ---
    // SauceDemo, otomasyon icin ozel "data-test" attribute'lari sunar.
    // Bunlar tasarim/CSS degisse bile sabit kalir -> en guvenilir locator turu.
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Login sayfasini acar. */
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

    /** Login butonuna tiklar ve Urunler sayfasini doner. */
    public ProductsPage clickLogin() {
        click(loginButton);
        return new ProductsPage(driver);
    }

    /** Tek adimda giris yapar (BASARILI senaryolar icin pratik). */
    public ProductsPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    /**
     * Giris bilgilerini doldurup butona basar ama sayfa gecisi VARSAYMADAN
     * bu sayfada kalir. Basarisiz (negatif) senaryolarda kullan.
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
