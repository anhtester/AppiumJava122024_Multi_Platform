package com.anhtester.drivers;

import io.appium.java_client.android.AndroidDriver;

public class AndroidDriverManager {
    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public static void setDriver(AndroidDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            getDriver().close();
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}