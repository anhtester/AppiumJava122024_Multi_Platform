package com.anhtester.dataproviders;

import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "loginSuccess")
    public Object[][] userDataLoginSuccess() {
        return new Object[][]{{"admin", "admin123"}, {"test", "test123"}};
    }

}