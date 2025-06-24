package com.anhtester.Bai24_Listeners.testcases;

import com.anhtester.Bai24_Listeners.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest_Json_Device {

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
