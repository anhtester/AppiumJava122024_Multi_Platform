package com.anhtester.Bai25_Screenshot_RecordVideo.testcases;

import com.anhtester.Bai25_Screenshot_RecordVideo.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import com.anhtester.helpers.CaptureHelpers;
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
      CaptureHelpers.captureScreenshot("testLoginSuccess");
      loginPage.verifyLoginSuccess();
   }

   @Test
   public void testLoginFailWithUsernameInvalid() {
      //Khởi tạo đối tượng Page class
      loginPage = new LoginPage();

      //Gọi hàm từ Page class sử dụng
      loginPage.login("admin123", "admin");
      CaptureHelpers.captureScreenshot("testLoginFailWithUsernameInvalid");
      loginPage.verifyLoginFail();
   }
}
