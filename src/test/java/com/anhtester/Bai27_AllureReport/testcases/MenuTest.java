package com.anhtester.Bai27_AllureReport.testcases;

import com.anhtester.Bai27_AllureReport.pages.LoginPage;
import com.anhtester.Bai27_AllureReport.pages.MenuPage;
import com.anhtester.common.BaseTest_Json_Device;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class MenuTest extends BaseTest_Json_Device {

   LoginPage loginPage;
   MenuPage menuPage;

   @Test
   @Story("Functional test of Menu Page")
   @Severity(SeverityLevel.CRITICAL)
   @Description("Test case to verify the search functionality in the menu page")
   @Link(name = "Check Menu Page", url = "https://jira.com/anhtester/crm/menu/123")
   public void testSearchTable() {
      loginPage = new LoginPage();
      //Khởi tạo trang Menu thông qua việc Login nếu thành công
      menuPage = loginPage.login("admin", "admin");
      loginPage.verifyLoginSuccess();

      downloadDataFromServer(4);

      //menuPage = new MenuPage(); //không cần khởi tạo trang Menu riêng
      menuPage.searchTable("Table 1");
      menuPage.checkTableResultTotal(2);
   }
}
