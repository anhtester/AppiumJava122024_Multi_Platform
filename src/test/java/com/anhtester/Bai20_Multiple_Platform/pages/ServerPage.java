package com.anhtester.Bai20_Multiple_Platform.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class ServerPage extends BasePage {
    public ServerPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    public void searchDatabase() {
        System.out.println("Search database");
    }

    public void downloadDatabase() {
        System.out.println("Download database");
    }

    public void resetDatabase() {
        System.out.println("Reset database");
    }
}
