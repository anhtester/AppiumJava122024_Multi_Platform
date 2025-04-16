package com.anhtester.Bai21_Config_File;

import com.anhtester.helpers.PropertiesHelpers;
import org.testng.annotations.Test;

public class DemoPropertiesFile {
    @Test
    public void testReadPropertiesFile() {
        PropertiesHelpers.loadAllFiles();

        System.out.println(PropertiesHelpers.getValue("APPIUM_DRIVER_LOCAL_SERVICE"));
        System.out.println(PropertiesHelpers.getValue("TIMEOUT_SERVICE"));
        System.out.println(PropertiesHelpers.getValue("TIMEOUT_EXPLICIT_DEFAULT"));
        System.out.println(PropertiesHelpers.getValue("JSON_CONFIG_FILE_PATH"));
    }

    @Test
    public void testSetValueToPropertiesFile() {
        PropertiesHelpers.setValue(
                "src/test/resources/test_data/data.properties",
                "platform",
                "Android");
    }
}
