package com.anhtester.Bai23_Excel_File;

import com.anhtester.dataproviders.DataProviderFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class DemoDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{{"user1", "pass1"}, {"user2", "pass2"}, {"user3", "pass3"}};
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Thực hiện thao tác test login ở đây...
    }

    @Test(dataProvider = "loginSuccess", dataProviderClass = DataProviderFactory.class)
    public void testLogin(String username, String password) {
        System.out.println("Login with: " + username + " - " + password);
    }

    @Test(dataProvider = "login_from_excel", dataProviderClass = DataProviderFactory.class)
    public void testLoginFromExcel(String username, String password) {
        System.out.println("Login with: " + username + " - " + password);
    }

    @Test(dataProvider = "login_from_excel_hashtable", dataProviderClass = DataProviderFactory.class)
    public void testLoginFromExcelHashtable(Hashtable<String, String> data) {
        System.out.println("Login with: " + data.get("USERNAME") + " - " + data.get("PASSWORD"));
    }

    // Sử dụng DataProvider với các dòng cụ thể cố định (1, 3)
    @Test(dataProvider = "login_specific_rows", dataProviderClass = DataProviderFactory.class)
    public void testLoginWithSpecificRows(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    // Sử dụng DataProvider với các dòng cụ thể dạng Hashtable
    @Test(dataProvider = "login_specific_rows_hashtable", dataProviderClass = DataProviderFactory.class)
    public void testLoginWithSpecificRowsHashtable(Hashtable<String, String> data) {
        String username = data.get("USERNAME");
        String password = data.get("PASSWORD");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    // Sử dụng DataProvider động với tham số từ testng.xml
    @Test(dataProvider = "dynamic_rows", dataProviderClass = DataProviderFactory.class)
    public void testLoginWithDynamicRows(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

    }

    // Sử dụng DataProvider động dạng Hashtable với tham số từ testng.xml
    @Test(dataProvider = "dynamic_rows_hashtable", dataProviderClass = DataProviderFactory.class)
    public void testLoginWithDynamicRowsHashtable(Hashtable<String, String> data) {
        String username = data.get("USERNAME");
        String password = data.get("PASSWORD");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

    }

}