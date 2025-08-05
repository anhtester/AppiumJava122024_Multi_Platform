package com.anhtester.constants;

import com.anhtester.helpers.PropertiesHelpers;
import com.anhtester.helpers.SystemHelpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigData {

   private ConfigData() {
      // Ngăn chặn khởi tạo class
   }

   // Load all properties files
   static {
      PropertiesHelpers.loadAllFiles();
   }

   public static final String PROJECT_PATH = SystemHelpers.getCurrentDir();
   public static final String EXCEL_DATA_FILE_PATH = PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
   public static final String JSON_DATA_FILE_PATH = PropertiesHelpers.getValue("JSON_DATA_FILE_PATH");
   public static final String JSON_CONFIG_FILE_PATH = PropertiesHelpers.getValue("JSON_CONFIG_FILE_PATH");
   public static final String TEST_DATA_FOLDER_PATH = PropertiesHelpers.getValue("TEST_DATA_FOLDER_PATH");
   public static final String LOCATE = PropertiesHelpers.getValue("LOCATE");
   public static final String TIMEOUT_SERVICE = PropertiesHelpers.getValue("TIMEOUT_SERVICE");
   public static final String TIMEOUT_EXPLICIT_DEFAULT = PropertiesHelpers.getValue("TIMEOUT_EXPLICIT_DEFAULT");
   public static final String APPIUM_DRIVER_LOCAL_SERVICE = PropertiesHelpers.getValue("APPIUM_DRIVER_LOCAL_SERVICE");
   public static final String STEP_ACTION_TIMEOUT = PropertiesHelpers.getValue("STEP_ACTION_TIMEOUT");
   public static final String SCREENSHOT_FAIL = PropertiesHelpers.getValue("SCREENSHOT_FAIL");
   public static final String SCREENSHOT_PASS = PropertiesHelpers.getValue("SCREENSHOT_PASS");
   public static final String SCREENSHOT_ALL_STEP = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEP");
   public static final String SCREENSHOT_PATH = PropertiesHelpers.getValue("SCREENSHOT_PATH");
   public static final String RECORD_VIDEO = PropertiesHelpers.getValue("RECORD_VIDEO");
   public static final String RECORD_VIDEO_PATH = PropertiesHelpers.getValue("RECORD_VIDEO_PATH");


   public static String getValueJsonConfig(String platform, String device, String propertyName) {
      // Initialize Jackson ObjectMapper
      ObjectMapper mapper = new ObjectMapper();
      // Read JSON file
      JsonNode rootNode = null;
      try {
         rootNode = mapper.readTree(new File(ConfigData.JSON_CONFIG_FILE_PATH));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      String result = rootNode
              .path("platforms")
              .path(platform.trim().toLowerCase())
              .path("devices")
              .path(device.trim().toLowerCase())
              .path(propertyName)
              .asText();

      System.out.println("***" + propertyName + ": " + result);
      return result;
   }

}
