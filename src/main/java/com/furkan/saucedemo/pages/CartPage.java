package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sepet (cart) sayfasi.
 */
public class CartPage extends BasePage {

    private final By cartItems        = By.cssSelector("[data-test='inventory-item']");
    private final By itemNames        = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton   = By.cssSelector("[data-test='checkout']");
    private final By continueShopping = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Sepetteki urun sayisi. */
    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    /** Sepetteki urun adlarini liste olarak doner. */
    public List<String> getItemNames() {
        return findAll(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /** Ismi verilen urunu sepetten cikarir. */
    public CartPage removeItem(String productName) {
        By removeButton = By.xpath(
                "//div[@data-test='inventory-item' and .//div[@data-test='inventory-item-name'"
                        + " and text()='" + productName + "']]//button");
        click(removeButton);
        return this;
    }

    /** "Continue Shopping" ile urunlere geri doner. */
    public ProductsPage continueShopping() {
        click(continueShopping);
        return new ProductsPage(driver);
    }

    /** Checkout butonuna tiklayip bilgi giris sayfasini doner. */
    public CheckoutInfoPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }
}
