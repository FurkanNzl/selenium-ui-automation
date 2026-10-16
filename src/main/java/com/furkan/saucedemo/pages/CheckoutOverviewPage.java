package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout - Adim 2: Siparis ozeti (urunler, ara toplam, vergi, toplam).
 */
public class CheckoutOverviewPage extends BasePage {

    private final By cartItems     = By.cssSelector(".cart_item");
    private final By subtotalLabel = By.cssSelector("[data-test='subtotal-label']"); // "Item total: $..."
    private final By taxLabel      = By.cssSelector("[data-test='tax-label']");       // "Tax: $..."
    private final By totalLabel    = By.cssSelector("[data-test='total-label']");     // "Total: $..."
    private final By finishButton  = By.cssSelector("[data-test='finish']");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public String getSubtotalText() {
        return getText(subtotalLabel);
    }

    public String getTaxText() {
        return getText(taxLabel);
    }

    public String getTotalText() {
        return getText(totalLabel);
    }

    /** Siparisi tamamlar ve onay sayfasini doner. */
    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
