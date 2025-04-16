package com.anhtester.helpers;

import com.anhtester.constants.ConfigData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelpers {

    public static String getValueJsonObject(String keyName) {
        String value = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(ConfigData.JSON_DATA_FILE_PATH));

            value = rootNode.path(keyName).asText();
            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getValueJsonObject(String parentKey, String keyName) {
        String value = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(ConfigData.JSON_DATA_FILE_PATH));

            value = rootNode.path(parentKey).path(keyName).asText();
            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getValueJsonObject(String parentKey1, String parentKey2, String keyName) {
        String value = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(ConfigData.JSON_DATA_FILE_PATH));

            value = rootNode.path(parentKey1).path(parentKey2).path(keyName).asText();
            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getValueJsonFileArray(String filePath, int itemIndex, String keyName) {
        String value = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            JsonNode itemNode = rootNode.get(itemIndex);

            value = itemNode.path(keyName).asText();
            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getValueJsonFileArray(String filePath, int itemIndex, String parentKey, String keyName) {
        String value = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            JsonNode itemNode = rootNode.get(itemIndex);

            value = itemNode.path(parentKey).path(keyName).asText();
            System.out.println("Value: " + value);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void updateValueJsonObject(String keyName, Number value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(ConfigData.JSON_DATA_FILE_PATH));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(ConfigData.JSON_DATA_FILE_PATH);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonObject(String keyName, String value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(ConfigData.JSON_DATA_FILE_PATH));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(ConfigData.JSON_DATA_FILE_PATH);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonObject(String parentKey, String keyName, Number value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(ConfigData.JSON_DATA_FILE_PATH));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.getAsJsonObject(parentKey).addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(ConfigData.JSON_DATA_FILE_PATH);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonObject(String parentKey, String keyName, String value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(ConfigData.JSON_DATA_FILE_PATH));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.getAsJsonObject(parentKey).addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(ConfigData.JSON_DATA_FILE_PATH);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonFile_Object(String filePath, String keyName, String value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(filePath));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to new file
            File jsonFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();

            //Close reader
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonFile_Object(String filePath, String keyName, Number value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(filePath));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to new file
            File jsonFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();

            //Close reader
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonFile_Object(String filePath, String parentKey, String keyName, String value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(filePath));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.getAsJsonObject(parentKey).addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateValueJsonFile_Object(String filePath, String parentKey, String keyName, Number value) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(filePath));

            Gson gson = new Gson();
            //Convert Json file to Json Object
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println("Original JSON: " + jsonObject);

            //Update value if exist key
            jsonObject.getAsJsonObject(parentKey).addProperty(keyName, value);

            System.out.println("Modified JSON: " + jsonObject);

            //Store new Json data to file
            File jsonFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(jsonFile);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
