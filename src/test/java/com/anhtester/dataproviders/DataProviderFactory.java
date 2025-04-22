package com.anhtester.dataproviders;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.ExcelHelpers;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "loginSuccess")
    public Object[][] userDataLoginSuccess() {
        return new Object[][]{{"admin", "admin123"}, {"test", "test123"}};
    }

    @DataProvider(name = "login_from_excel")
    public Object[][] login_from_excel() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getExcelData(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login"
        );
    }

    @DataProvider(name = "login_from_excel_hashtable")
    public Object[][] login_from_excel_hashtable() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getDataHashTable(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login",
                2,
                3
        );
    }

    @DataProvider(name = "login_specific_rows")
    public Object[][] login_specific_rows() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        // Đọc dữ liệu từ các dòng 1, 3
        int[] specificRows = new int[]{1, 3};
        return excelHelpers.getDataFromSpecificRows(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login",
                specificRows
        );
    }

    @DataProvider(name = "login_specific_rows_hashtable")
    public Object[][] login_specific_rows_hashtable() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        // Đọc dữ liệu từ các dòng 1, 3
        int[] specificRows = new int[]{1, 3};
        return excelHelpers.getDataHashTableFromSpecificRows(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login",
                specificRows
        );
    }

}