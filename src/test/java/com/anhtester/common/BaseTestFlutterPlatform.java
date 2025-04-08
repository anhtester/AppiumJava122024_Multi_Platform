package com.anhtester.common;

import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.SystemHelpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.ashwith.flutter.FlutterFinder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTestFlutterPlatform {

    private AppiumDriverLocalService service;
    private String HOST = "127.0.0.1";
    private String PORT = "4723";
    private int TIMEOUT_SERVICE = 60;

    public FlutterFinder flutterFinder;

    @BeforeSuite
    public void runAppiumServer() {
        //Kill process on port
        SystemHelpers.killProcessOnPort("4723");

        //Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(HOST);
        builder.usingPort(Integer.parseInt(PORT));
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info"); // Set log level (optional)
        builder.withTimeout(Duration.ofSeconds(TIMEOUT_SERVICE));

        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        if (service.isRunning()) {
            System.out.println("##### Appium server started on " + HOST + ":" + PORT);
        } else {
            System.out.println("Failed to start Appium server.");
        }

    }

    @BeforeTest
    public void setUpDriver() {
        AppiumDriver driver;
        UiAutomator2Options options = new UiAutomator2Options();
        System.out.println("***SERVER ADDRESS: " + HOST);
        System.out.println("***SERVER POST: " + PORT);

        options.setPlatformName("Android");
        options.setPlatformVersion("14");
        options.setAutomationName("Flutter"); //Thay cho UiAutomator2
        options.setDeviceName("Pixel_9_Pro_XL_API_34");
        options.setAppPackage("com.anhtester.mobile_app.taurus");
        options.setAppActivity("com.anhtester.mobile_app.taurus.MainActivity");
        options.setNoReset(false);
        options.setFullReset(false);

        try {
            driver = new AppiumDriver(new URL("http://" + HOST + ":" + PORT), options);
            DriverManager.setDriver(driver);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        flutterFinder = new FlutterFinder(driver);

    }

    @AfterTest
    public void tearDownDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quitDriver();
        }
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("##### Appium server stopped.");
        }
    }

}