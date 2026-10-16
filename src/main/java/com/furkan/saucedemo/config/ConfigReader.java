package com.furkan.saucedemo.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * config.properties dosyasini okuyan yardimci sinif.
 *
 * URL, tarayici, bekleme suresi gibi ayarlari KODDAN AYIRIR.
 * Boylece ortam degisince (farkli URL, farkli tarayici) kodu degistirmene
 * gerek kalmaz; sadece config.properties'i degistirirsin.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    // static blok: sinif ilk kullanildiginda dosyayi BIR KEZ yukler.
    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException(
                        "config.properties bulunamadi! src/test/resources altinda olmali.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("config.properties okunurken hata: " + e.getMessage(), e);
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
