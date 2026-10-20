package com.furkan.saucedemo.driver;

import com.furkan.saucedemo.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Creates and manages the WebDriver instance.
 *
 * Uses ThreadLocal: if tests are run in PARALLEL, each thread (each test)
 * gets ITS OWN driver, so they never interfere with each other.
 *
 * NOTE: "Selenium Manager" (Selenium 4.6+) downloads the right driver
 * (chromedriver/geckodriver) AUTOMATICALLY. There is NO need to download
 * drivers manually or add WebDriverManager. Only the browser must be installed.
 */
public class DriverFactory {

    // Holds a separate WebDriver for each thread.
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Creates the driver based on the "browser" value in config.properties.
     */
    public static void initDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        boolean headless = ConfigReader.isHeadless();

        WebDriver webDriver;

        switch (browser) {
            case "firefox":
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) {
                    ffOptions.addArguments("-headless");
                }
                webDriver = new FirefoxDriver(ffOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                webDriver = new ChromeDriver(chromeOptions);
                break;
        }

        // NO implicit wait (mixing it with explicit waits causes problems).
        // Only a page load timeout is set - a small safety net.
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.set(webDriver);
    }

    /** Returns the current thread's driver. Use this in tests. */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /** Quits the browser and clears the ThreadLocal (prevents memory leaks). */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
