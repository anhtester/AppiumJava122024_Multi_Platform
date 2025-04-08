package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ConfigPage extends BasePage {
    public ConfigPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,\"Product management\")]")
    @iOSXCUITFindBy(xpath = "")
    private WebElement itemProductManagement;

    @AndroidFindBy(accessibility = "Server database")
    @iOSXCUITFindBy(accessibility = "Server database")
    private WebElement itemServerDatabase;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,\"Tables management\")]")
    @iOSXCUITFindBy(xpath = "")
    private WebElement itemTableManagement;

    @AndroidFindBy(accessibility = "Logout")
    @iOSXCUITFindBy(accessibility = "Logout")
    private WebElement itemLogout;

    public ProductPage openProductManagement() {
        itemProductManagement.click();

        return new ProductPage();
    }

    public ServerPage openServerDatabase() {
        itemServerDatabase.click();

        return new ServerPage();
    }

    public TablePage openTableManagement() {
        itemTableManagement.click();

        return new TablePage();
    }

    public LoginPage logout() {
        itemLogout.click();

        return new LoginPage();
    }
}
