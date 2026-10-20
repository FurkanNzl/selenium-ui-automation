package com.furkan.saucedemo.base;

import com.furkan.saucedemo.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BASE test class that all test classes extend.
 *
 * @BeforeMethod: runs BEFORE each test method -> opens the browser.
 * @AfterMethod:  runs AFTER each test method  -> closes the browser.
 *
 * This way every test runs in a CLEAN browser, INDEPENDENT of the others.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
