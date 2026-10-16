package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout - Adim 3: Siparis tamamlandi onay sayfasi.
 */
public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.cssSelector("[data-test='complete-header']"); // "Thank you for your order!"
    private final By completeText   = By.cssSelector("[data-test='complete-text']");
    private final By backHomeButton = By.cssSelector("[data-test='back-to-products']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    /** Onay basligini doner ("Thank you for your order!"). */
    public String getConfirmationMessage() {
        return getText(completeHeader);
    }

    /** Siparis basariyla tamamlandi mi? */
    public boolean isOrderComplete() {
        return isDisplayed(completeHeader);
    }

    /** Ana sayfaya (urunlere) doner. */
    public ProductsPage clickBackHome() {
        click(backHomeButton);
        return new ProductsPage(driver);
    }
}
