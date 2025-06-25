package com.anhtester.helpers;

import com.anhtester.drivers.DriverManager;
import com.anhtester.utils.DateUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Base64;

public class CaptureHelpers {
    /**
     * Hàm static để chụp ảnh màn hình và lưu vào đường dẫn file được chỉ định.
     *
     * @param fileName Đường dẫn file nơi muốn lưu ảnh chụp màn hình (ví dụ: "screenshots/image.png").
     */
    public static void captureScreenshot(String fileName) {
        try {
            // Ép kiểu driver thành TakesScreenshot để lấy ảnh màn hình
            File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

            SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/screenshots");
            String filePath = SystemHelpers.getCurrentDir() + "exports/screenshots/" + fileName + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".png";

            // Tạo đối tượng Path cho file đích
            Path targetPath = new File(filePath).toPath();

            // Sao chép file từ nguồn sang đích, thay thế file nếu đã tồn tại
            Files.copy(srcFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Chụp ảnh màn hình thành công, lưu tại: " + targetPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Lỗi trong quá trình lưu file ảnh: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi trong quá trình chụp ảnh màn hình: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Bắt đầu ghi video
    public static void startRecording() {
        if (DriverManager.getDriver() != null) {
            ((AndroidDriver) DriverManager.getDriver()).startRecordingScreen(
                    new AndroidStartScreenRecordingOptions()
                            .withBitRate(4000000) // default: 4000000
                            .withVideoSize("1080x2400") // 720 x 1600, 1080 x 2400 pixels
                            .withTimeLimit(Duration.ofMinutes(10))); // 10 minutes max video length
            System.out.println("Bắt đầu ghi video cho " + DriverManager.getDriver().getCapabilities().getCapability("deviceName"));
        }
    }

    // Dừng ghi video và lưu file
    public static void stopRecording(String videoFileName) {
        if (DriverManager.getDriver() != null) {
            try {
                String base64Video = ((CanRecordScreen) ((AndroidDriver) DriverManager.getDriver())).stopRecordingScreen();
                System.out.println("Base64 video length: " + (base64Video != null ? base64Video.length() : "null"));
                if (base64Video != null && !base64Video.isEmpty()) {
                    byte[] videoBytes = Base64.getDecoder().decode(base64Video);
                    System.out.println("Video bytes length: " + videoBytes.length);
                    File videoFile = new File(videoFileName);
                    try (FileOutputStream fos = new FileOutputStream(videoFile)) {
                        fos.write(videoBytes);
                    }
                    System.out.println("Video được lưu tại: " + videoFile.getAbsolutePath() + " (Size: " + videoFile.length() + " bytes)");
                } else {
                    System.out.println("Không có dữ liệu video để lưu.");
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi dừng ghi video: " + e.getMessage());
            }
        }
    }
}
