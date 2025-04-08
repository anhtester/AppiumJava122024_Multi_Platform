package com.anhtester.Bai20_Multiple_Platform.testcases;

import com.anhtester.Bai20_Multiple_Platform.pages.ConfigPage;
import com.anhtester.Bai20_Multiple_Platform.pages.LoginPage;
import com.anhtester.Bai20_Multiple_Platform.pages.ProductPage;
import com.anhtester.common.BaseTest;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {

    private LoginPage loginPage;
    private ConfigPage configPage;
    private ProductPage productPage;

    @Test
    public void testAddNewProduct() {
        loginPage = new LoginPage();
        loginPage.login("admin", "admin");

        configPage = new ConfigPage();
        productPage = configPage.openProductManagement();

        //productPage = new ProductPage();
        productPage.addNewProduct();
    }
}
