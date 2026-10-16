package com.furkan.saucedemo.pages;

import com.furkan.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Urunler (inventory) sayfasi. Basarili giris sonrasi buraya gelinir.
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

    /** Urunler sayfasi yuklendi mi? ("Products" basligi gorunuyor mu) */
    public boolean isLoaded() {
        return isDisplayed(pageTitle) && getText(pageTitle).equalsIgnoreCase("Products");
    }

    /** Sayfadaki toplam urun sayisi (SauceDemo'da 6 olmali). */
    public int getProductCount() {
        return findAll(inventoryItems).size();
    }

    /**
     * Ismi verilen urunu sepete ekler.
     * Urun adindan o urunun "Add to cart" butonuna XPath ile ulasiyoruz
     * (relative/dinamik locator ornegi).
     */
    public ProductsPage addProductToCart(String productName) {
        By addButton = By.xpath(
                "//div[@data-test='inventory-item' and .//div[@data-test='inventory-item-name'"
                        + " and text()='" + productName + "']]//button");
        click(addButton);
        return this;
    }

    /** Sepet rozetindeki sayi. Rozet yoksa (sepet bossa) 0 doner. */
    public int getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }

    /**
     * Siralama dropdown'undan value ile secim yapar.
     * Secenekler: az (A-Z), za (Z-A), lohi (fiyat artan), hilo (fiyat azalan).
     */
    public ProductsPage sortBy(String value) {
        selectByValue(sortDropdown, value);
        return this;
    }

    /** Sepet ikonuna tiklayip Sepet sayfasini doner. */
    public CartPage goToCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}
