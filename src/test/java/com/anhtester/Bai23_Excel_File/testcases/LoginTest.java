package com.anhtester.Bai23_Excel_File.testcases;

import com.anhtester.Bai23_Excel_File.pages.LoginPage;
import com.anhtester.common.BaseTest_Json_Device;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.helpers.ExcelHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest_Json_Device {

    private LoginPage loginPage;

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"admin", "admin"},
                {"user2", "pass2"},
//                {"user3", "pass3"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTestDataProvider1(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Thực hiện thao tác test login ở đây...
        loginPage = new LoginPage();
        loginPage.login(username, password);
        loginPage.verifyLoginSuccess();
    }

    @Test(dataProvider = "login_from_excel", dataProviderClass = DataProviderFactory.class)
    public void testLoginFromExcel(String username, String password) {
        System.out.println("Login with: " + username + " - " + password);

        // Thực hiện thao tác test login ở đây...
        loginPage = new LoginPage();
        loginPage.login(username, password);
        loginPage.verifyLoginSuccess();
    }

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

        excelHelpers.setCellData("Passed", 1, "EXPECTED_RESULT");
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

        excelHelpers.setCellData("Passed", 2, "EXPECTED_RESULT");
    }

}
