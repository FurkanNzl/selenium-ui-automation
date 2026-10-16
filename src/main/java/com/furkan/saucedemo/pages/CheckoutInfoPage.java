package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout - Adim 1: Musteri bilgileri (ad, soyad, posta kodu).
 */
public class CheckoutInfoPage extends BasePage {

    private final By firstNameInput  = By.id("first-name");
    private final By lastNameInput   = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton  = By.cssSelector("[data-test='continue']");
    private final By cancelButton     = By.cssSelector("[data-test='cancel']");
    private final By errorMessage    = By.cssSelector("[data-test='error']");

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutInfoPage enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    public CheckoutInfoPage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    public CheckoutInfoPage enterPostalCode(String postalCode) {
        type(postalCodeInput, postalCode);
        return this;
    }

    /** Tum bilgileri tek seferde doldurur. */
    public CheckoutInfoPage fillInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    /** Devam edip ozet (overview) sayfasini doner. */
    public CheckoutOverviewPage clickContinue() {
        click(continueButton);
        return new CheckoutOverviewPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
