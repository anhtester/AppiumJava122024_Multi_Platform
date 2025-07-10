package com.anhtester.Bai27_AllureReport.testcases;

import com.anhtester.Bai27_AllureReport.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Web interface")
@Feature("Essential features")
public class LoginTest extends BaseTest_Json_Device {

   private LoginPage loginPage;

   @Test
   @Story("Authentication")
   @Description("Test case to verify the login functionality with valid credentials")
   public void testLoginSuccess() {
      loginPage = new LoginPage();

      loginPage.login("admin", "admin");
      //CaptureHelpers.captureScreenshot("testLoginSuccess");
      loginPage.verifyLoginSuccess();
   }

   @Test
   @Story("Authentication")
   @Description("Test case to verify the login functionality with invalid credentials")
   public void testLoginFailWithUsernameInvalid() {
      loginPage = new LoginPage();

      loginPage.login("admin", "admin");
      //CaptureHelpers.captureScreenshot("testLoginFailWithUsernameInvalid");
      loginPage.verifyLoginFail();
   }
}
