package com.anhtester.dataproviders;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.ExcelHelpers;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

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

    
    // Truyền tham số từ suite XML vào DataProvider

    /**
     * DataProvider động cho phép truyền tham số là các dòng cần đọc
     *
     * @param rowIndices Chuỗi chứa các chỉ số dòng, phân cách bởi dấu phẩy, ví dụ: "1,3,5"
     * @return Dữ liệu từ các dòng được chỉ định
     */
    @DataProvider(name = "dynamic_rows")
    public Object[][] dynamic_rows(ITestContext context) {
        // Lấy tham số từ test context hoặc suite XML
        String rowIndicesStr = context.getCurrentXmlTest().getParameter("rowIndices");
        if (rowIndicesStr == null || rowIndicesStr.isEmpty()) {
            // Mặc định đọc các dòng 1, 2, 3 nếu không có tham số nào được truyền
            rowIndicesStr = "1,2,3";
        }

        // Chuyển đổi chuỗi thành mảng các số nguyên
        int[] rowIndices = Arrays.stream(rowIndicesStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getDataFromSpecificRows(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login",
                rowIndices
        );
    }

    /**
     * DataProvider động trả về dữ liệu dạng Hashtable
     *
     * @param rowIndices Chuỗi chứa các chỉ số dòng, phân cách bởi dấu phẩy, ví dụ: "1,3,5"
     * @return Dữ liệu dạng Hashtable từ các dòng được chỉ định
     */
    @DataProvider(name = "dynamic_rows_hashtable")
    public Object[][] dynamic_rows_hashtable(ITestContext context) {
        // Lấy tham số từ test context hoặc suite XML
        String rowIndicesStr = context.getCurrentXmlTest().getParameter("rowIndices");
        if (rowIndicesStr == null || rowIndicesStr.isEmpty()) {
            // Mặc định đọc các dòng 1, 2, 3 nếu không có tham số nào được truyền
            rowIndicesStr = "1,2,3";
        }

        // Chuyển đổi chuỗi thành mảng các số nguyên
        int[] rowIndices = Arrays.stream(rowIndicesStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getDataHashTableFromSpecificRows(
                ConfigData.EXCEL_DATA_FILE_PATH,
                "Login",
                rowIndices
        );
    }

}