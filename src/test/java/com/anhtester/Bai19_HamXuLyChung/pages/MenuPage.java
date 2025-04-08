package com.anhtester.Bai19_HamXuLyChung.pages;

import com.anhtester.drivers.DriverManager;
import com.anhtester.keywords.MobileUI;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MenuPage extends BasePage {
    // Constructor
    public MenuPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    //Element/Locators thuộc chính trang này (màn hình này)
    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(accessibility = "")
    private WebElement inputSearch;

    @AndroidFindBy(xpath = "(//android.view.View[contains(@content-desc,\"Table\")])[1]")
    @iOSXCUITFindBy(accessibility = "")
    private WebElement firstItemTable;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,\"Table\")]")
    @iOSXCUITFindBy(xpath = "")
    private List<WebElement> listItemTable;

    public void searchTable(String tableName) {
        clickMenuMenu();
        MobileUI.clickElement(inputSearch); // Click vào ô tìm kiếm
        MobileUI.setText(inputSearch, tableName); // Nhập từ khóa tìm kiếm
    }

    public void checkTableResultTotal(int expectedTotal) {
        List<WebElement> listTables = listItemTable;
        System.out.println("Table total: " + listTables.size());
        //Assert.assertTrue(listTables.size() >= expectedTotal);
        MobileUI.assertTrueCondition(listTables.size() >= expectedTotal, "The table total is not correct.");
    }

    public void clickFirstItemTable() {
        MobileUI.clickElement(firstItemTable);
    }
}