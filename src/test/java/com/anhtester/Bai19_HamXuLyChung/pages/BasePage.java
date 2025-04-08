package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    //Constructor bắt buộc để init elements
    public BasePage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    //Element/Locators thuộc chung cho nhiều trang
    @AndroidFindBy(accessibility = "Date")
    @iOSXCUITFindBy(accessibility = "Date")
    public WebElement menuDate;

    @AndroidFindBy(accessibility = "Menu")
    @iOSXCUITFindBy(accessibility = "Menu")
    public WebElement menuMenu;

    @AndroidFindBy(accessibility = "Wallet")
    @iOSXCUITFindBy(accessibility = "Wallet")
    public WebElement menuWallet;

    @AndroidFindBy(accessibility = "Profile")
    @iOSXCUITFindBy(accessibility = "Profile")
    public WebElement menuProfile;

    @AndroidFindBy(accessibility = "Config")
    @iOSXCUITFindBy(accessibility = "Config")
    public WebElement menuConfig;

    @AndroidFindBy(accessibility = "Open navigation menu")
    @iOSXCUITFindBy(accessibility = "Open navigation menu")
    public WebElement openNavigationLeftMenu;

    @AndroidFindBy(accessibility = "Web view")
    @iOSXCUITFindBy(accessibility = "Web view")
    public WebElement itemWebView;

    @AndroidFindBy(accessibility = "Back")
    @iOSXCUITFindBy(accessibility = "Back")
    public WebElement buttonBack;

    //Các hàm xử lý chung cho nhiều trang đều có
    public void clickMenuDate() {
        menuDate.click();
    }

    public MenuPage clickMenuMenu() {
        menuMenu.click();
        return new MenuPage();
    }

    public void clickMenuWallet() {
        menuWallet.click();
    }

    public ProfilePage clickMenuProfile() {
        menuProfile.click();
        return new ProfilePage();
    }

    public ConfigPage clickMenuConfig() {
        menuConfig.click();
        return new ConfigPage();
    }

    public void clickOpenNavigationLeftMenu() {
        openNavigationLeftMenu.click();
    }

    public void clickItemWebView() {
        itemWebView.click();
    }

    public void clickButtonBack() {
        buttonBack.click();
    }

}