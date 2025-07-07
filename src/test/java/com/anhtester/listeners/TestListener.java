package com.anhtester.listeners;

import com.anhtester.helpers.CaptureHelpers;
import com.anhtester.helpers.SystemHelpers;
import com.anhtester.keywords.MobileUI;
import com.anhtester.reports.AllureManager;
import com.anhtester.utils.DateUtils;
import com.anhtester.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext result) {
        //Delete folder screenshots/videos
        LogUtils.info("♻\uFE0F Setup môi trường: " + result.getStartDate());
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("\uD83D\uDD06 Kết thúc chạy test: " + result.getEndDate());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("➡\uFE0F Bắt đầu chạy test case: " + result.getName());

        // Tạo tên file video duy nhất dựa trên device và thread
//        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
//        videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + deviceName + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        CaptureHelpers.startRecording();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("✅ Test case " + result.getName() + " is passed.");

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        LogUtils.info("Thời gian: " + formattedDate);

        CaptureHelpers.captureScreenshot(result.getName());

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(5);
        CaptureHelpers.stopRecording(videoFileName);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("❌ Test case " + result.getName() + " is failed.");

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        LogUtils.info("Nguyên nhân lỗi: " + result.getThrowable());

        CaptureHelpers.captureScreenshot(result.getName());

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(2);
        CaptureHelpers.stopRecording(videoFileName);

        AllureManager.saveScreenshotPNG();

        //Connect Jira
        //Create new issue on Jira
        //Ghi logs vào file
        //Xuất report html nhìn trực quan và đẹp mắt
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.info("⛔\uFE0F Test case " + result.getName() + " is skipped.");

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(2);
        CaptureHelpers.stopRecording(videoFileName);
    }

}