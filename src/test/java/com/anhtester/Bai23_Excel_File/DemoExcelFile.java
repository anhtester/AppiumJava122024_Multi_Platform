package com.anhtester.Bai23_Excel_File;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.ExcelHelpers;
import org.testng.annotations.Test;

public class DemoExcelFile {

    @Test
    public void testReadExcelFile() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        excelHelpers.setExcelFile(ConfigData.EXCEL_DATA_FILE_PATH, "Login");

        System.out.println("USERNAME: " + excelHelpers.getCellData(1, "USERNAME"));
        System.out.println("PASSWORD: " + excelHelpers.getCellData(1, "PASSWORD"));

        System.out.println("USERNAME: " + excelHelpers.getCellData(2, "USERNAME"));
        System.out.println("PASSWORD: " + excelHelpers.getCellData(2, "PASSWORD"));

    }

    @Test
    public void testWriteExcelFile() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        excelHelpers.setExcelFile(ConfigData.EXCEL_DATA_FILE_PATH, "Login");

        excelHelpers.setCellData("Passed", 1, "EXPECTED_RESULT");
        excelHelpers.setCellData("Failed", 2, "EXPECTED_RESULT");
    }

}
