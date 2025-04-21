package com.anhtester.Bai23_Excel_File.testcases;

import com.anhtester.Bai23_Excel_File.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import com.anhtester.helpers.ExcelHelpers;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest_Json_Device {

    private LoginPage loginPage;

    @Test
    public void testLoginSuccess() {
        loginPage = new LoginPage();

        ExcelHelpers excelHelpers = new ExcelHelpers();
        String filePath = "src/test/resources/test_data/data.xlsx";
        excelHelpers.setExcelFile(filePath, "Login");
        String username = excelHelpers.getCellData(1, "USERNAME");
        String password = excelHelpers.getCellData(1, "PASSWORD");
        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);

        loginPage.login(username, password);
        loginPage.verifyLoginSuccess();
    }

    @Test
    public void testLoginFailWithUsernameInvalid() {
        loginPage = new LoginPage();

        ExcelHelpers excelHelpers = new ExcelHelpers();
        String filePath = "src/test/resources/test_data/data.xlsx";
        excelHelpers.setExcelFile(filePath, "Login");
        String username = excelHelpers.getCellData(2, "USERNAME");
        String password = excelHelpers.getCellData(2, "PASSWORD");
        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);

        loginPage.login(username, password);

        loginPage.verifyLoginFail();
    }
}
