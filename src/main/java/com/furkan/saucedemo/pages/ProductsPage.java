package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Products (inventory) page. Reached after a successful login.
 */
public class ProductsPage extends BasePage {

    private final By pageTitle      = By.cssSelector("[data-test='title']");
    private final By inventoryItems = By.cssSelector("[data-test='inventory-item']");
    private final By cartBadge      = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink       = By.cssSelector("[data-test='shopping-cart-link']");
    private final By sortDropdown   = By.cssSelector("[data-test='product-sort-container']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /** Is the Products page loaded? (Is the "Products" title visible?) */
    public boolean isLoaded() {
        return isDisplayed(pageTitle) && getText(pageTitle).equalsIgnoreCase("Products");
    }

    /** Total number of products on the page (6 on SauceDemo). */
    public int getProductCount() {
        return findAll(inventoryItems).size();
    }

    /**
     * Adds the given product to the cart.
     * Finds that product's "Add to cart" button from its name using XPath
     * (an example of a relative/dynamic locator).
     */
    public ProductsPage addProductToCart(String productName) {
        By addButton = By.xpath(
                "//div[@data-test='inventory-item' and .//div[@data-test='inventory-item-name'"
                        + " and text()='" + productName + "']]//button");
        click(addButton);
        return this;
    }

    /** Number on the cart badge. Returns 0 if there is no badge (empty cart). */
    public int getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }

    /**
     * Selects a sort option from the dropdown by value.
     * Options: az (A-Z), za (Z-A), lohi (price low to high), hilo (price high to low).
     */
    public ProductsPage sortBy(String value) {
        selectByValue(sortDropdown, value);
        return this;
    }

    /** Clicks the cart icon and returns the Cart page. */
    public CartPage goToCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}
