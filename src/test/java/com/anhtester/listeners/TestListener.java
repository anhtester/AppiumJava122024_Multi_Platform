package com.anhtester.listeners;

import com.anhtester.helpers.CaptureHelpers;
import com.anhtester.helpers.SystemHelpers;
import com.anhtester.keywords.MobileUI;
import com.anhtester.utils.DateUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext result) {
        //Delete folder screenshots/videos
        System.out.println("♻\uFE0F Setup môi trường: " + result.getStartDate());
    }

    @Override
    public void onFinish(ITestContext result) {
        System.out.println("\uD83D\uDD06 Kết thúc chạy test: " + result.getEndDate());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("➡\uFE0F Bắt đầu chạy test case: " + result.getName());

        // Tạo tên file video duy nhất dựa trên device và thread
//        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
//        videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + deviceName + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        CaptureHelpers.startRecording();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test case " + result.getName() + " is passed.");

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        System.out.println("Thời gian: " + formattedDate);

        CaptureHelpers.captureScreenshot(result.getName());

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(5);
        CaptureHelpers.stopRecording(videoFileName);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test case " + result.getName() + " is failed.");

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        System.out.println("Thời gian: " + formattedDate);
        System.out.println("Nguyên nhân: " + result.getThrowable());

        CaptureHelpers.captureScreenshot(result.getName());

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(5);
        CaptureHelpers.stopRecording(videoFileName);

        //Connect Jira
        //Create new issue on Jira
        //Ghi logs vào file
        //Xuất report html nhìn trực quan và đẹp mắt
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⛔\uFE0F Test case " + result.getName() + " is skipped.");

        SystemHelpers.createFolder(SystemHelpers.getCurrentDir() + "exports/videos");
        String videoFileName = SystemHelpers.getCurrentDir() + "exports/videos/recording_" + result.getName() + "_" + Thread.currentThread().getId() + "_" + SystemHelpers.makeSlug(DateUtils.getCurrentDateTime()) + ".mp4";
        MobileUI.sleep(2);
        CaptureHelpers.stopRecording(videoFileName);
    }

}