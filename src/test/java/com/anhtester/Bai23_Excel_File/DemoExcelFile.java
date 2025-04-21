package com.anhtester.Bai23_Excel_File;

import com.anhtester.helpers.ExcelHelpers;
import org.testng.annotations.Test;

public class DemoExcelFile {

    @Test
    public void testReadExcelFile() {
        String filePath = "src/test/resources/test_data/data.xlsx";
        ExcelHelpers excelHelpers = new ExcelHelpers();
        excelHelpers.setExcelFile(filePath, "Login");

        System.out.println("USERNAME: " + excelHelpers.getCellData(1, "USERNAME"));
        System.out.println("PASSWORD: " + excelHelpers.getCellData(1, "PASSWORD"));

        System.out.println("USERNAME: " + excelHelpers.getCellData(2, "USERNAME"));
        System.out.println("PASSWORD: " + excelHelpers.getCellData(2, "PASSWORD"));

    }

//    @Test
//    public void testWriteExcelFile() {
//        String filePath = "src/test/resources/test_data/data.xlsx";
//        ExcelHelpers excelHelpers = new ExcelHelpers();
//        excelHelpers.setExcelFile(filePath, "Login");
//
//        excelHelpers.setCellData("Passed", 1, "EXPECTED_RESULT");
//        excelHelpers.setCellData("Failed", 2, "EXPECTED_RESULT");
//    }

}
