package com.furkan.saucedemo.base;

import com.furkan.saucedemo.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Tum Page Object'lerin miras aldigi TEMEL sinif.
 *
 * Ortak isleri (tiklama, yazma, metin okuma, bekleme) burada TEK yerde toplariz.
 * Boylece her sayfada ayni bekleme kodunu tekrar yazmayiz (DRY prensibi).
 *
 * ONEMLI: Hicbir yerde Thread.sleep() KULLANMIYORUZ.
 * Onun yerine "explicit wait" (WebDriverWait) ile elementin hazir olmasini bekliyoruz.
 * Bu, testleri hem daha HIZLI hem daha GUVENILIR (flaky olmayan) yapar.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /** Element gorunur olana kadar bekleyip doner. */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Element tiklanabilir olana kadar bekleyip doner. */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Elemente guvenli tiklama (once tiklanabilir olmasini bekler). */
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    /** Input alanini temizleyip metin yazar. */
    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** Elementin metnini doner. */
    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    /**
     * Element sayfada gorunur mu? Yoksa exception yerine false doner.
     * (Not: element yoksa 'explicit.wait.seconds' kadar bekleyip false doner.)
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Locator'a uyan TUM elementleri doner. */
    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    /** Dropdown'dan "value" attribute'una gore secim yapar. */
    protected void selectByValue(By locator, String value) {
        Select select = new Select(waitForVisible(locator));
        select.selectByValue(value);
    }

    /** Mevcut URL'i doner. */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
