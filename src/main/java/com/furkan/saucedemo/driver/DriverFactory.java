package com.furkan.saucedemo.driver;

import com.furkan.saucedemo.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * WebDriver nesnesini olusturan ve yoneten sinif.
 *
 * ThreadLocal kullaniyoruz: ileride testleri PARALEL calistirirsan
 * her thread (her test) KENDI driver'ina sahip olur, birbirine karismaz.
 *
 * NOT: Selenium 4.6+ ile gelen "Selenium Manager" dogru driver'i
 * (chromedriver/geckodriver) OTOMATIK indirir. Yani elle driver indirmene
 * veya WebDriverManager eklemene GEREK YOK. Sadece tarayici kurulu olsun.
 */
public class DriverFactory {

    // Her thread icin ayri WebDriver tutar.
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * config.properties'teki "browser" degerine gore driver olusturur.
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

        // Implicit wait KULLANMIYORUZ (explicit wait ile karisirsa sorun cikar).
        // Sadece sayfa yuklenme timeout'u koyuyoruz - kucuk bir guvenlik agi.
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.set(webDriver);
    }

    /** Aktif thread'in driver'ini doner. Testlerde bunu kullan. */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /** Tarayiciyi kapatir ve ThreadLocal'i temizler (bellek sizintisini onler). */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
