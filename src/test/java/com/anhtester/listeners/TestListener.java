package com.anhtester.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext result) {
        System.out.println("♻\uFE0F Setup môi trường: " + result.getStartDate());
    }

    @Override
    public void onFinish(ITestContext result) {
        System.out.println("\uD83D\uDD06 Kết thúc chạy test: " + result.getEndDate());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("➡\uFE0F Bắt đầu chạy test case: " + result.getName());

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        System.out.println("Ngày giờ hiện tại: " + formattedDate);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test case " + result.getName() + " is passed.");
        System.out.println("==> Status: " + result.getStatus());

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        System.out.println("Ngày giờ hiện tại: " + formattedDate);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ Test case " + result.getName() + " is failed.");
        System.out.println("==> Status: " + result.getStatus());

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        System.out.println("Ngày giờ hiện tại: " + formattedDate);

        System.out.println("Nguyên nhân: " + result.getThrowable());

        //Connect Jira
        //Create new issue on Jira
        //Ghi logs vào file
        //Xuất report html nhìn trực quan và đẹp mắt
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⛔\uFE0F Test case " + result.getName() + " is skipped.");
        System.out.println("==> Status: " + result.getStatus());

        LocalDateTime now = LocalDateTime.now(); // lấy ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        System.out.println("Ngày giờ hiện tại: " + formattedDate);
    }

}