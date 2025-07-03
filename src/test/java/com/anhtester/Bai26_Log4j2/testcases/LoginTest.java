package com.anhtester.Bai26_Log4j2.testcases;

import com.anhtester.Bai26_Log4j2.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest_Json_Device {

    private LoginPage loginPage;

    @Test
    public void testLoginSuccess() {
        loginPage = new LoginPage();

        loginPage.login("admin", "admin");
        //CaptureHelpers.captureScreenshot("testLoginSuccess");
        loginPage.verifyLoginSuccess();
    }

    @Test
    public void testLoginFailWithUsernameInvalid() {
        loginPage = new LoginPage();

        loginPage.login("admin", "admin");
        //CaptureHelpers.captureScreenshot("testLoginFailWithUsernameInvalid");
        loginPage.verifyLoginFail();
    }
}
