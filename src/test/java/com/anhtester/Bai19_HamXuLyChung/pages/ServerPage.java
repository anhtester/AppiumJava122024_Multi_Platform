package com.anhtester.Bai19_HamXuLyChung.pages;

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
