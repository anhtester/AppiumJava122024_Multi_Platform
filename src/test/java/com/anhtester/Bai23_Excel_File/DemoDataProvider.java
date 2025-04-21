package com.anhtester.Bai23_Excel_File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

//    @Test(dataProvider = "loginSuccess", dataProviderClass = DataProviderFactory.class)
//    public void testLogin(String username, String password) {
//        System.out.println("Login with: " + username + " - " + password);
//    }

}