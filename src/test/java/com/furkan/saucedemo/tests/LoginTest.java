package com.furkan.saucedemo.tests;

import com.furkan.saucedemo.base.BaseTest;
import com.furkan.saucedemo.pages.LoginPage;
import com.furkan.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Login scenarios.
 *
 * Uses TestNG's @DataProvider to run the SAME test with DIFFERENT data
 * (data-driven testing). 4 rows of data = 4 separate test runs.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid user should log in successfully and land on the Products page")
    public void validLoginShouldSucceed() {
        LoginPage loginPage = new LoginPage(driver).open();
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(productsPage.isLoaded(),
                "Products page did not load after login!");
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Expected 6 products on the Products page!");
    }

    /**
     * Data set for invalid login scenarios.
     * Each row: { username, password, expectedErrorFragment }
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
          description = "Invalid login attempts should show the correct error message")
    public void invalidLoginShouldShowError(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.attemptLogin(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message not displayed! Input: '" + username + "' / '" + password + "'");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Unexpected error message. Expected: '" + expectedError
                        + "' | Actual: '" + loginPage.getErrorMessage() + "'");
    }
}
