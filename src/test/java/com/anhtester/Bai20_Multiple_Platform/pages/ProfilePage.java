package com.anhtester.Bai20_Multiple_Platform.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage extends BasePage {
    public ProfilePage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "Edit product")
    @iOSXCUITFindBy(accessibility = "Edit product")
    private WebElement buttonEditProduct;

    public void editProduct() {
        System.out.println("Edit profile");
        buttonEditProduct.click();
        //Viết tiếp
    }
}
