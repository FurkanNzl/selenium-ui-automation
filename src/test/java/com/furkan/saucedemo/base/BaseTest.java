package com.furkan.saucedemo.base;

import com.furkan.saucedemo.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Tum test siniflarinin miras aldigi TEMEL test sinifi.
 *
 * @BeforeMethod: HER test metodundan ONCE calisir  -> tarayiciyi acar.
 * @AfterMethod:  HER test metodundan SONRA calisir -> tarayiciyi kapatir.
 *
 * Boylece her test TEMIZ bir tarayicida, birbirinden BAGIMSIZ calisir.
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
