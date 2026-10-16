package com.furkan.saucedemo.tests;

import com.furkan.saucedemo.base.BaseTest;
import com.furkan.saucedemo.pages.CartPage;
import com.furkan.saucedemo.pages.CheckoutCompletePage;
import com.furkan.saucedemo.pages.CheckoutInfoPage;
import com.furkan.saucedemo.pages.CheckoutOverviewPage;
import com.furkan.saucedemo.pages.LoginPage;
import com.furkan.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Uctan uca (end-to-end) satin alma senaryosu:
 * Giris -> urun ekle -> sepet -> checkout bilgileri -> ozet -> siparisi tamamla.
 *
 * Bu test, Page Object'lerin birbirine nasil "zincir" halinde baglandigini gosterir:
 * her metot bir sonraki sayfanin Page Object'ini doner.
 */
public class CheckoutE2ETest extends BaseTest {

    @Test(description = "Kullanici urun secip siparisi bastan sona tamamlayabilmeli")
    public void completePurchaseFlow() {
        // 1) Giris
        LoginPage loginPage = new LoginPage(driver).open();
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "Giris basarisiz.");

        // 2) Urunleri sepete ekle
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(productsPage.getCartCount(), 2, "Sepette 2 urun olmali.");

        // 3) Sepete git ve dogrula
        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 2, "Sepet sayfasinda 2 urun olmali.");

        // 4) Checkout - bilgileri gir
        CheckoutInfoPage infoPage = cartPage.clickCheckout();
        CheckoutOverviewPage overviewPage =
                infoPage.fillInformation("Furkan", "Test", "34000").clickContinue();

        // 5) Ozet sayfasini dogrula
        Assert.assertEquals(overviewPage.getItemCount(), 2, "Ozet sayfasinda 2 urun olmali.");
        Assert.assertTrue(overviewPage.getTotalText().contains("Total"),
                "Toplam tutar bilgisi gorunmuyor.");

        // 6) Siparisi tamamla ve onayi dogrula
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertTrue(completePage.isOrderComplete(), "Siparis tamamlanmadi!");
        Assert.assertEquals(completePage.getConfirmationMessage(), "Thank you for your order!",
                "Onay mesaji beklenenden farkli.");
    }
}
