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
 * End-to-end purchase scenario:
 * Login -> add products -> cart -> checkout info -> overview -> complete order.
 *
 * This test shows how Page Objects are "chained" together:
 * each method returns the Page Object of the next page.
 */
public class CheckoutE2ETest extends BaseTest {

    @Test(description = "User should be able to select products and complete an order end to end")
    public void completePurchaseFlow() {
        // 1) Login
        LoginPage loginPage = new LoginPage(driver).open();
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "Login failed.");

        // 2) Add products to the cart
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(productsPage.getCartCount(), 2, "Cart should contain 2 products.");

        // 3) Go to the cart and verify
        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 2, "Cart page should show 2 products.");

        // 4) Checkout - enter information
        CheckoutInfoPage infoPage = cartPage.clickCheckout();
        CheckoutOverviewPage overviewPage =
                infoPage.fillInformation("Furkan", "Test", "34000").clickContinue();

        // 5) Verify the overview page
        Assert.assertEquals(overviewPage.getItemCount(), 2, "Overview page should show 2 products.");
        Assert.assertTrue(overviewPage.getTotalText().contains("Total"),
                "Order total is not displayed.");

        // 6) Complete the order and verify confirmation
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertTrue(completePage.isOrderComplete(), "Order was not completed!");
        Assert.assertEquals(completePage.getConfirmationMessage(), "Thank you for your order!",
                "Confirmation message does not match.");
    }
}
