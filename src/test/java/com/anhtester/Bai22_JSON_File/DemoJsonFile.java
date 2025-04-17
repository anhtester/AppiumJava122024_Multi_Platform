package com.anhtester.Bai22_JSON_File;

import com.anhtester.helpers.JsonHelpers;
import org.testng.annotations.Test;

public class DemoJsonFile {
    @Test
    public void testReadJsonFile() {
        //Get value from JSON object
        JsonHelpers.getValueJsonObject("platform");
        JsonHelpers.getValueJsonObject("device");

        JsonHelpers.getValueJsonObject("account", "username");
        JsonHelpers.getValueJsonObject("account", "password");

        JsonHelpers.getValueJsonObject("product", "product_name");
        JsonHelpers.getValueJsonObject("product", "product_price");
        JsonHelpers.getValueJsonObject("product", "hidden_product");
        JsonHelpers.getValueJsonObject("product", "product_image");

        JsonHelpers.getValueJsonObject_FilePath("src/test/resources/configs/device.json", "platforms", "android", "devices", "pixel8", "deviceName");

        //Get value from JSON array
        JsonHelpers.getValueJsonArray_FilePath("src/test/resources/test_data/sample_products.json", 0, "price", "amount");
    }

    @Test
    public void testUpdateValueToJsonFile() {
        //Add new data with root key value
        JsonHelpers.updateValueJsonObject(
                "database_name", "Data 4");
        JsonHelpers.updateValueJsonObject(
                "database_index", 4);

        //Add new data with parent key is object
        JsonHelpers.updateValueJsonObject("database",
                "database_name", "Data 1");
        JsonHelpers.updateValueJsonObject("database",
                "database_index", 1);

        //Update value with parent key is object in file path
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "product", "product_name", "Product 3");
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "product", "product_price", 140000);

        //Add new data with parent key is object in file path
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "profile", "name", "Anh Tester");
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "profile", "email", "anhtester@example.com");
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "profile", "phone", "0939206009");
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "profile", "address", "HCM");
        JsonHelpers.updateValueJsonObject_FilePath("src/test/resources/test_data/data.json",
                "profile", "job_title", "Tester");

        //Update value in JSON array
        JsonHelpers.updateValueJsonArray_FilePath("src/test/resources/test_data/sample_products.json", "20000", 0, "price", "amount");
        
    }
}
