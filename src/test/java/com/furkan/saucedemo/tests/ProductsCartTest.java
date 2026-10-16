package com.furkan.saucedemo.tests;

import com.furkan.saucedemo.base.BaseTest;
import com.furkan.saucedemo.pages.CartPage;
import com.furkan.saucedemo.pages.LoginPage;
import com.furkan.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Urun listeleme ve sepet senaryolari.
 *
 * Buradaki testler "giris yapilmis" olmayi gerektirir. O yuzden her testten once
 * (BaseTest'in setUp'indan SONRA) giris yapip Urunler sayfasina geliyoruz.
 */
public class ProductsCartTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true)
    public void loginFirst() {
        productsPage = new LoginPage(driver).open()
                .loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "On kosul basarisiz: giris yapilamadi.");
    }

    @Test(description = "Sepete urun eklenince sepet rozeti guncellenmeli")
    public void addingProductsUpdatesCartBadge() {
        Assert.assertEquals(productsPage.getCartCount(), 0, "Baslangicta sepet bos olmali.");

        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light");

        Assert.assertEquals(productsPage.getCartCount(), 2, "Sepet rozeti 2 gostermeli.");
    }

    @Test(description = "Sepete eklenen urunler sepet sayfasinda dogru gorunmeli")
    public void addedProductsAppearInCart() {
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Fleece Jacket");

        CartPage cartPage = productsPage.goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 2, "Sepette 2 urun olmali.");
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Backpack"),
                "Backpack sepette gorunmuyor.");
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Fleece Jacket"),
                "Fleece Jacket sepette gorunmuyor.");
    }

    @Test(description = "Sepetten urun cikarilinca rozet azalmali")
    public void removingProductUpdatesBadge() {
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light");
        Assert.assertEquals(productsPage.getCartCount(), 2, "Once 2 urun olmali.");

        CartPage cartPage = productsPage.goToCart();
        cartPage.removeItem("Sauce Labs Bike Light");

        Assert.assertEquals(cartPage.getItemCount(), 1, "Cikarma sonrasi 1 urun kalmali.");
    }

    @Test(description = "Urunler fiyata gore artan siralanabilmeli (dropdown testi)")
    public void productsCanBeSortedByPriceLowToHigh() {
        productsPage.sortBy("lohi"); // price (low to high)
        // Basit tutuyoruz: siralama hatasiz calisti ve urun sayisi degismedi.
        // (Ileride gercek fiyat sirasini da dogrulayan bir versiyon ekleyebiliriz.)
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Siralama sonrasi urun sayisi degismemeli.");
    }
}
