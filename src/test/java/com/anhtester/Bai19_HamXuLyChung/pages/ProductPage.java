package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {
    public ProductPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "Add Product")
    @iOSXCUITFindBy(accessibility = "Add Product")
    private WebElement buttonAddNewProduct;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(xpath = "")
    private WebElement inputSearchProduct;

    public void addNewProduct() {
        System.out.println("Add new product");
        buttonAddNewProduct.click();
        //Viết tiếp
    }
}
