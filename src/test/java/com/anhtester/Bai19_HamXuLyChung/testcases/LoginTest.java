package com.anhtester.Bai19_HamXuLyChung.testcases;

import com.anhtester.Bai19_HamXuLyChung.pages.LoginPage;
import com.anhtester.common.BaseTestTaurusApp;
import org.testng.annotations.Test;

public class LoginTest extends BaseTestTaurusApp {

    //Khai báo các đối tượng Page class liên quan
    private LoginPage loginPage;

    @Test
    public void testLoginSuccess() {
        //Khởi tạo đối tượng Page class
        loginPage = new LoginPage();

        //Gọi hàm từ Page class sử dụng
        loginPage.login("admin", "admin");
        loginPage.verifyLoginSuccess();
    }

    @Test
    public void testLoginFailWithUsernameInvalid() {
        //Khởi tạo đối tượng Page class
        loginPage = new LoginPage();

        //Gọi hàm từ Page class sử dụng
        loginPage.login("admin123", "admin");
        loginPage.verifyLoginFail();
    }
}
