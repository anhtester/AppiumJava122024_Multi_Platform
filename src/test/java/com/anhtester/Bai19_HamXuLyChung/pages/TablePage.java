package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class TablePage extends BasePage {
    public TablePage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "Add Table")
    @iOSXCUITFindBy(accessibility = "Add Table")
    private WebElement buttonAddNewTable;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(xpath = "")
    private WebElement inputSearchTable;

    public void addNewTable() {
        System.out.println("Add new table");
        buttonAddNewTable.click();
        //Viết tiếp
    }
}
