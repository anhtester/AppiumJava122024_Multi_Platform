package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import com.anhtester.keywords.MobileUI;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    // Constructor
    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    //Element/Locators thuộc chính trang này (màn hình này)
    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Mobile App Flutter Beta\"]/following-sibling::android.widget.EditText[1]")
    @iOSXCUITFindBy(accessibility = "username")
    private WebElement usernameField;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Mobile App Flutter Beta\"]/following-sibling::android.widget.EditText[2]")
    @iOSXCUITFindBy(accessibility = "password")
    private WebElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Sign in\"]")
    @iOSXCUITFindBy(id = "loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = " Invalid email or password")
    @iOSXCUITFindBy(accessibility = " Invalid email or password")
    private WebElement errorMessage;

    //Các hàm xử lý trong chính nội bộ trang này (màn hình này)

    public MenuPage login(String username, String password) {
        MobileUI.clickElement(usernameField); // Click vào username field
        MobileUI.setText(usernameField, username); // Nhập username
        MobileUI.clickElement(passwordField); // Click vào password field
        MobileUI.setText(passwordField, password); // Nhập password
        MobileUI.clickElement(loginButton); // Click nút login

        return new MenuPage();
    }

    public MenuPage verifyLoginSuccess() {
        // Sử dụng MobileUI để verify
        MobileUI.verifyElementPresentAndDisplayed(menuMenu, "The Table page not display. (Menu not found)");
        return new MenuPage();
    }

    public void verifyLoginFail() {
        // Sử dụng MobileUI để verify
        MobileUI.verifyElementPresentAndDisplayed(errorMessage, "The error message not display.");
        System.out.println(MobileUI.getElementAttribute(errorMessage, "content-desc"));
        MobileUI.verifyElementAttribute(errorMessage, "content-desc", " Invalid email or password",
                "The content of error message not display.");
    }
}