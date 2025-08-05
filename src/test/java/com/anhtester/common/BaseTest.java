package com.anhtester.common;

import com.anhtester.constants.ConfigData;
import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.SystemHelpers;
import com.anhtester.keywords.MobileUI;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public class BaseTest {

   private AppiumDriverLocalService service;
   private String HOST = "127.0.0.1";
   private String PORT = "4723";
   private int TIMEOUT_SERVICE = 60;

   /**
    * Chạy Appium server với host và port được chỉ định.
    *
    * @param host Địa chỉ host của Appium server
    * @param port Port của Appium server
    */
   public void runAppiumServer(String host, String port) {
      System.out.println("HOST: " + host);
      System.out.println("PORT: " + port);

      //Set host and port
      if (host == null || host.isEmpty()) {
         host = HOST;
      } else {
         HOST = host;
      }

      if (port == null || port.isEmpty()) {
         port = PORT;
      } else {
         PORT = port;
      }

      TIMEOUT_SERVICE = Integer.parseInt(ConfigData.TIMEOUT_SERVICE);

      //Kill process on port
      SystemHelpers.killProcessOnPort(PORT);

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
         System.out.println("Failed to start Appium server on LOCAL.");
      }

   }

   /**
    * Thiết lập (khởi tạo và lưu trữ) AppiumDriver cho luồng hiện tại.
    *
    * @param platformName         Tên platform (Android/iOS)
    * @param platformVersion      Phiên bản platform
    * @param deviceName           Tên thiết bị
    * @param udid                 UDID của thiết bị (quan trọng cho parallel)
    * @param automationName       Tên automation engine (UiAutomator2/XCUITest)
    * @param appPackage           Package của app Android
    * @param appActivity          Activity của app Android
    * @param noReset              Không reset app trước khi chạy
    * @param fullReset            Reset app trước khi chạy
    * @param autoGrantPermissions Tự động cấp quyền cho app
    * @param host                 Địa chỉ host của Appium server
    * @param port                 Port của Appium server
    * @param bundleId             Bundle ID của app iOS
    * @param wdaLocalPort         Port WDA (iOS parallel)
    * @param systemPort           Port System (Android parallel)
    */
   @BeforeMethod(alwaysRun = true)
   @Parameters({"platformName", "platformVersion", "deviceName", "udid", "automationName", "appPackage", "appActivity", "noReset", "fullReset", "autoGrantPermissions", "host", "port", "bundleId", "wdaLocalPort", "systemPort"})
   public void setUpDriver(String platformName, String platformVersion, String deviceName, @Optional String udid, @Optional String automationName, @Optional String appPackage, @Optional String appActivity, boolean noReset, boolean fullReset, boolean autoGrantPermissions, String host, String port, @Optional String bundleId, @Optional String wdaLocalPort, @Optional String systemPort) {
      //Khởi động Appium server dưới máy local
      if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
         System.out.println("Khởi động Appium server LOCAL: " + host + ":" + port);
         runAppiumServer(host, port);
      } else {
         System.out.println("Chạy Appium server từ xa hoặc đã bật sẵn.");
      }

      //Print tất cả các thông số
      System.out.println("platformName: " + platformName);
      System.out.println("platformVersion: " + platformVersion);
      System.out.println("deviceName: " + deviceName);
      System.out.println("udid: " + udid);
      System.out.println("automationName: " + automationName);
      System.out.println("appPackage: " + appPackage);
      System.out.println("appActivity: " + appActivity);
      System.out.println("noReset: " + noReset);
      System.out.println("fullReset: " + fullReset);
      System.out.println("autoGrantPermissions: " + autoGrantPermissions);
      System.out.println("host: " + host);
      System.out.println("port: " + port);
      System.out.println("bundleId: " + bundleId);
      System.out.println("wdaLocalPort: " + wdaLocalPort);
      System.out.println("systemPort: " + systemPort);

      AppiumDriver driver = null;

      try {
         if (platformName.equalsIgnoreCase("Android")) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName(platformName);
            options.setPlatformVersion(platformVersion);
            options.setDeviceName(deviceName);
            if (udid != null && !udid.isEmpty()) {
               options.setUdid(udid);
            }
            if (appPackage != null && !appPackage.isEmpty()) {
               options.setAppPackage(appPackage);
            }
            if (appActivity != null && !appActivity.isEmpty()) {
               options.setAppActivity(appActivity);
            }
            // options.setApp("/path/to/your/app.apk");
            options.setAutomationName(Objects.requireNonNullElse(automationName, "UiAutomator2"));
            options.setNoReset(noReset);
            options.setFullReset(fullReset);

            if (systemPort != null && !systemPort.isEmpty()) {
               options.setSystemPort(Integer.parseInt(systemPort));
            }
            
            driver = new AndroidDriver(new URL("http://" + host + ":" + port), options);
            System.out.println("Khởi tạo AndroidDriver cho thread: " + Thread.currentThread().getId() + " trên thiết bị: " + deviceName);

         } else if (platformName.equalsIgnoreCase("iOS")) {
            XCUITestOptions options = new XCUITestOptions();
            options.setPlatformName(platformName);
            options.setPlatformVersion(platformVersion);
            options.setDeviceName(deviceName);
            // options.setApp("/path/to/your/app.app or .ipa");

            if (bundleId != null && !bundleId.isEmpty()) {
               options.setBundleId(bundleId);
            }
            options.setAutomationName(Objects.requireNonNullElse(automationName, "XCUITest"));
            options.setNoReset(noReset);
            options.setFullReset(fullReset);

            if (wdaLocalPort != null && !wdaLocalPort.isEmpty()) {
               options.setWdaLocalPort(Integer.parseInt(wdaLocalPort));
            }
            // options.setXcodeOrgId("YOUR_TEAM_ID");
            // options.setXcodeSigningId("iPhone Developer");

            driver = new IOSDriver(new URL("http://" + host + ":" + port), options);
            System.out.println("Khởi tạo IOSDriver cho thread: " + Thread.currentThread().getId() + " trên thiết bị: " + deviceName);

         } else {
            throw new IllegalArgumentException("Platform không hợp lệ: " + platformName);
         }

         // Lưu driver vào ThreadLocal
         DriverManager.setDriver(driver);

      } catch (Exception e) {
         System.err.println("❌Lỗi nghiêm trọng khi khởi tạo driver cho thread " + Thread.currentThread().getId() + " trên device " + deviceName + ": " + e.getMessage());
         // Có thể ném lại lỗi để TestNG biết test setup thất bại
         throw new RuntimeException("❌Không thể khởi tạo Appium driver ", e);
      }

   }

   @AfterMethod(alwaysRun = true)
   public void tearDownDriver() {
      if (DriverManager.getDriver() != null) {
         DriverManager.quitDriver();
         System.out.println("##### Driver quit and removed.");
      }

      //Dừng Appium server LOCAL nếu đã khởi động
      if (ConfigData.APPIUM_DRIVER_LOCAL_SERVICE.trim().equalsIgnoreCase("true")) {
         stopAppiumServer();
      }
   }

   /**
    * Stop Appium server.
    */
   public void stopAppiumServer() {
      if (service != null && service.isRunning()) {
         service.stop();
         System.out.println("##### Appium server stopped on " + HOST + ":" + PORT);
      }
      //Kill process on port
      SystemHelpers.killProcessOnPort(PORT);
   }

   /**
    * Tải xuống dữ liệu từ server. Chỉ dành cho Taurus App.
    *
    * @param dataNumber Số thứ tự của dữ liệu cần tải xuống
    */
   public void downloadDataFromServer(int dataNumber) {
      //Navigate to config to download database demo
      DriverManager.getDriver().findElement(AppiumBy.accessibilityId("Config")).click();
      DriverManager.getDriver().findElement(AppiumBy.accessibilityId("Server database")).click();
      MobileUI.sleep(2);
      DriverManager.getDriver().findElement(AppiumBy.xpath("//android.view.View[contains(@content-desc,'Data " + dataNumber + "')]/android.widget.Button")).click();
      DriverManager.getDriver().findElement(AppiumBy.accessibilityId("Replace")).click();
      MobileUI.sleep(1);

      //Handle Alert Message, check displayed hoặc getText/getAttribute để kiểm tra nội dung message
      if (DriverManager.getDriver().findElement(AppiumBy.accessibilityId("Downloaded")).isDisplayed()) {
         System.out.println("Database demo downloaded.");
      } else {
         System.out.println("Warning!! Can not download Database demo.");
      }
      MobileUI.sleep(2);
      DriverManager.getDriver().findElement(AppiumBy.accessibilityId("Back")).click();
   }
}