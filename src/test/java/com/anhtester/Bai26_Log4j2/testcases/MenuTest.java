package com.anhtester.Bai26_Log4j2.testcases;

import com.anhtester.Bai26_Log4j2.pages.LoginPage;
import com.anhtester.Bai26_Log4j2.pages.MenuPage;
import com.anhtester.common.BaseTest_Json_Device;
import org.testng.annotations.Test;

public class MenuTest extends BaseTest_Json_Device {

    LoginPage loginPage;
    MenuPage menuPage;

    @Test
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
