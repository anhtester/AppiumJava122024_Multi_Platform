package com.anhtester.Bai19_HamXuLyChung.testcases;

import com.anhtester.Bai19_HamXuLyChung.pages.LoginPage;
import com.anhtester.Bai19_HamXuLyChung.pages.MenuPage;
import com.anhtester.common.BaseTestTaurusApp;
import org.testng.annotations.Test;

public class MenuTest extends BaseTestTaurusApp {

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
