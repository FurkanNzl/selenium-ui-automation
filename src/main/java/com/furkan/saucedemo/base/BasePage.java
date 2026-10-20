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
 * BASE class that all Page Objects extend.
 *
 * Common actions (click, type, read text, wait) live here in ONE place,
 * so the same wait logic is not repeated on every page (DRY principle).
 *
 * IMPORTANT: Thread.sleep() is NEVER used.
 * Instead, explicit waits (WebDriverWait) wait until the element is ready.
 * This makes tests both FASTER and more RELIABLE (not flaky).
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /** Waits until the element is visible and returns it. */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Waits until the element is clickable and returns it. */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Safe click (waits for the element to be clickable first). */
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    /** Clears the input field and types the text. */
    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** Returns the element's text. */
    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    /**
     * Is the element visible on the page? Returns false instead of throwing.
     * (Note: if the element is missing, waits 'explicit.wait.seconds' then returns false.)
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns ALL elements matching the locator. */
    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    /** Selects a dropdown option by its "value" attribute. */
    protected void selectByValue(By locator, String value) {
        Select select = new Select(waitForVisible(locator));
        select.selectByValue(value);
    }

    /** Returns the current URL. */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
