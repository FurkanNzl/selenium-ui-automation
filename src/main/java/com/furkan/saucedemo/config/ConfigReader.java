package com.furkan.saucedemo.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Helper class that reads config.properties.
 *
 * Keeps settings such as URL, browser and wait time OUT OF THE CODE.
 * When the environment changes (different URL or browser), there is no need
 * to touch the code; just update config.properties.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    // Static block: loads the file ONCE, when the class is first used.
    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException(
                        "config.properties not found! It must be under src/test/resources.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error reading config.properties: " + e.getMessage(), e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait.seconds", "10"));
    }
}
