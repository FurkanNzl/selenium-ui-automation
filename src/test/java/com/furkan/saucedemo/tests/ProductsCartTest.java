package com.furkan.saucedemo.tests;

import com.furkan.saucedemo.base.BaseTest;
import com.furkan.saucedemo.pages.CartPage;
import com.furkan.saucedemo.pages.LoginPage;
import com.furkan.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Product listing and cart scenarios.
 *
 * These tests require a logged-in user, so before each test
 * (AFTER BaseTest's setUp) we log in and land on the Products page.
 */
public class ProductsCartTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true)
    public void loginFirst() {
        productsPage = new LoginPage(driver).open()
                .loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "Precondition failed: could not log in.");
    }

    @Test(description = "Cart badge should update when products are added")
    public void addingProductsUpdatesCartBadge() {
        Assert.assertEquals(productsPage.getCartCount(), 0, "Cart should be empty initially.");

        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light");

        Assert.assertEquals(productsPage.getCartCount(), 2, "Cart badge should show 2.");
    }

    @Test(description = "Added products should appear correctly on the cart page")
    public void addedProductsAppearInCart() {
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Fleece Jacket");

        CartPage cartPage = productsPage.goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 2, "Cart should contain 2 products.");
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Backpack"),
                "Backpack is not in the cart.");
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Fleece Jacket"),
                "Fleece Jacket is not in the cart.");
    }

    @Test(description = "Removing a product should decrease the cart count")
    public void removingProductUpdatesBadge() {
        productsPage.addProductToCart("Sauce Labs Backpack")
                    .addProductToCart("Sauce Labs Bike Light");
        Assert.assertEquals(productsPage.getCartCount(), 2, "Cart should contain 2 products first.");

        CartPage cartPage = productsPage.goToCart();
        cartPage.removeItem("Sauce Labs Bike Light");

        Assert.assertEquals(cartPage.getItemCount(), 1, "1 product should remain after removal.");
    }

    @Test(description = "Products should be sortable by price, low to high (dropdown test)")
    public void productsCanBeSortedByPriceLowToHigh() {
        productsPage.sortBy("lohi"); // price (low to high)
        // Kept simple: sorting ran without errors and the product count is unchanged.
        // (A future version could also verify the actual price order.)
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Product count should not change after sorting.");
    }
}
