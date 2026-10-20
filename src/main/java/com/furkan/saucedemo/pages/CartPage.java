package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cart page.
 */
public class CartPage extends BasePage {

    private final By cartItems        = By.cssSelector("[data-test='inventory-item']");
    private final By itemNames        = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton   = By.cssSelector("[data-test='checkout']");
    private final By continueShopping = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Number of products in the cart. */
    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    /** Returns the names of products in the cart as a list. */
    public List<String> getItemNames() {
        return findAll(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /** Removes the given product from the cart. */
    public CartPage removeItem(String productName) {
        By removeButton = By.xpath(
                "//div[@data-test='inventory-item' and .//div[@data-test='inventory-item-name'"
                        + " and text()='" + productName + "']]//button");
        click(removeButton);
        return this;
    }

    /** Returns to the products via "Continue Shopping". */
    public ProductsPage continueShopping() {
        click(continueShopping);
        return new ProductsPage(driver);
    }

    /** Clicks Checkout and returns the information page. */
    public CheckoutInfoPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }
}
