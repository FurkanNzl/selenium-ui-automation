package com.furkan.saucedemo.tests;

import com.furkan.saucedemo.base.BaseTest;
import com.furkan.saucedemo.pages.LoginPage;
import com.furkan.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Giris (login) senaryolari.
 *
 * TestNG'nin @DataProvider ozelligi ile AYNI testi FARKLI verilerle
 * calistiriyoruz (data-driven testing). 4 satir veri = 4 ayri test kosumu.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Gecerli kullanici ile giris basarili olmali, Urunler sayfasi acilmali")
    public void validLoginShouldSucceed() {
        LoginPage loginPage = new LoginPage(driver).open();
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(productsPage.isLoaded(),
                "Giris sonrasi Urunler sayfasi yuklenmedi!");
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Urunler sayfasinda beklenen urun sayisi 6 degil!");
    }

    /**
     * Gecersiz giris senaryolari icin veri seti.
     * Her satir: { kullaniciAdi, sifre, beklenenHataMetniParcasi }
     */
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"standard_user", "wrong_password", "Username and password do not match"},
                {"locked_out_user", "secret_sauce", "Sorry, this user has been locked out"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"}
        };
    }

    @Test(dataProvider = "invalidLoginData",
          description = "Gecersiz giris denemeleri uygun hata mesajini gostermeli")
    public void invalidLoginShouldShowError(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.attemptLogin(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Hata mesaji gorunmuyor! Girdi: '" + username + "' / '" + password + "'");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Beklenen hata cikmadi. Beklenen: '" + expectedError
                        + "' | Gercek: '" + loginPage.getErrorMessage() + "'");
    }
}
