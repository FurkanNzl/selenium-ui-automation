# SauceDemo UI Test Automation

A UI test automation framework for [SauceDemo (Swag Labs)](https://www.saucedemo.com), built with **Selenium 4, Java, TestNG and Maven** following the **Page Object Model (POM)** design pattern.

## Features

- **Page Object Model** architecture (one class per page)
- **Explicit waits** instead of `Thread.sleep` for faster and more reliable tests
- **Data-driven testing** with TestNG `@DataProvider` (same test, multiple data sets)
- **Externalized configuration** (URL, browser, timeouts) kept out of the code
- **ThreadLocal WebDriver**, making the framework ready for parallel execution
- **End-to-end (E2E)** checkout scenario

## Requirements

| Tool   | Version                              |
| ------ | ------------------------------------ |
| JDK    | 17 or higher                         |
| Maven  | 3.8+ (bundled with IntelliJ IDEA)    |
| Chrome | Latest version                       |
| IDE    | IntelliJ IDEA (recommended)          |

> **No need to download a driver.** Selenium Manager (included since Selenium 4.6) automatically downloads the right ChromeDriver for your browser. Just make sure Chrome is installed.

## Getting Started

### 1. Clone the project

```
git clone https://github.com/FurkanNzl/selenium-ui-automation.git
```

Then open the project folder in IntelliJ IDEA via **File > Open**. IntelliJ downloads the Maven dependencies (Selenium, TestNG) automatically; the first import may take a moment.

### 2. Run the tests

**Option A – From the terminal (full suite):**

```
mvn test
```

**Option B – From IntelliJ IDEA:**

- Right-click `testng.xml` → **Run**
- Or right-click any test class or method → **Run**

### 3. View the report

After a run, the TestNG report is generated at:

```
test-output/index.html
```

## Project Structure

```
selenium-ui-automation/
├── pom.xml                      -> Maven configuration and dependencies
├── testng.xml                   -> Test suite definition
└── src/
    ├── main/java/com/furkan/saucedemo/
    │   ├── config/
    │   │   └── ConfigReader.java     -> Reads config.properties
    │   ├── driver/
    │   │   └── DriverFactory.java    -> Creates and quits the WebDriver
    │   ├── base/
    │   │   └── BasePage.java         -> Shared actions (click, type, wait...)
    │   └── pages/                    -> One Page Object per page
    │       ├── LoginPage.java
    │       ├── ProductsPage.java
    │       ├── CartPage.java
    │       ├── CheckoutInfoPage.java
    │       ├── CheckoutOverviewPage.java
    │       └── CheckoutCompletePage.java
    └── test/
        ├── java/com/furkan/saucedemo/
        │   ├── base/
        │   │   └── BaseTest.java     -> Setup and teardown (@BeforeMethod / @AfterMethod)
        │   └── tests/
        │       ├── LoginTest.java          -> Login scenarios (data-driven)
        │       ├── ProductsCartTest.java   -> Product and cart scenarios
        │       └── CheckoutE2ETest.java    -> End-to-end checkout
        └── resources/
            └── config.properties    -> URL, browser, timeouts
```

## Architecture

**1. Page Object Model (`pages/`)**
Each web page has its own class that holds its locators and the actions available on it (log in, add to cart...). When the site changes, only the related Page Object needs updating instead of dozens of tests.

**2. BasePage**
Common actions every page needs (click, type, wait) live here, avoiding duplication (DRY).

**3. DriverFactory**
Browser setup and teardown are kept separate from the tests. Thanks to `ThreadLocal`, the framework is ready for parallel execution.

**4. ConfigReader + config.properties**
Fixed values such as the URL are kept out of the code. Switching environments or browsers only requires editing the properties file.

**5. BaseTest**
A fresh browser is opened before each test and closed after it, so tests run independently and cleanly.

## Configuration

`src/test/resources/config.properties`:

| Key                     | Description          | Example                     |
| ----------------------- | -------------------- | --------------------------- |
| `base.url`              | Site under test      | `https://www.saucedemo.com` |
| `browser`               | Browser              | `chrome` / `firefox`        |
| `headless`              | Run without UI       | `true` / `false`            |
| `explicit.wait.seconds` | Wait timeout (sec)   | `10`                        |

## SauceDemo Test Users

| Username                     | Password       | Notes                              |
| ---------------------------- | -------------- | ---------------------------------- |
| `standard_user`              | `secret_sauce` | Regular user                       |
| `locked_out_user`            | `secret_sauce` | Locked out (login fails)           |
| `problem_user`               | `secret_sauce` | Intentionally broken behavior      |
| `performance_glitch_user`    | `secret_sauce` | Slow page loads                    |
| `error_user` / `visual_user` | `secret_sauce` | Advanced error/visual scenarios    |
