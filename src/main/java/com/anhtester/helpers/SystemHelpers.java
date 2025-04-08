package com.anhtester.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SystemHelpers {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String makeSlug(String input) {
        if (input == null)
            throw new IllegalArgumentException();

        String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("_");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    /**
     * @return Get the path to your source directory with a / at the end
     */
    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }

    public static boolean createFolder(String path) {
        try {
            File folder = new File(path);

            // Kiểm tra xem đã tồn tại và có phải là folder không
            if (folder.exists() && folder.isDirectory()) {
                System.out.println("Folder đã tồn tại: " + path);
                return false;
            }

            // Tạo folder và các thư mục cha
            boolean created = folder.mkdirs();

            if (created) {
                System.out.println("Tạo folder thành công: " + path);
            } else {
                System.out.println("Tạo folder thất bại: " + path);
            }

            return created;
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo folder: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param str        string to be split based on condition
     * @param valueSplit the character to split the string into an array of values
     * @return array of string values after splitting
     */
    public static ArrayList<String> splitString(String str, String valueSplit) {
        ArrayList<String> arrayListString = new ArrayList<>();
        for (String s : str.split(valueSplit, 0)) {
            arrayListString.add(s);
        }
        return arrayListString;
    }

    public static boolean checkValueInListString(String expected, String listValues[]) {
        boolean found = false;

        for (String s : listValues) {
            if (s.equals(expected)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean checkValueInListString(String expected, List<String> listValues) {
        boolean found = false;

        for (String s : listValues) {
            if (s.equals(expected)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static void killProcessOnPort(String port) {
        String command = "";

        // Check OS to set command to find and kill process
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            command = "cmd /c netstat -ano | findstr :" + port;
        } else {
            command = "lsof -i :" + port;
        }

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                String pid = tokens[1];  // PID position may vary by OS
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                } else {
                    Runtime.getRuntime().exec("kill -9 " + pid);
                }
            }
            reader.close();
            process.waitFor();
            System.out.println("####### Kill process on port " + port + " successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startAppiumWithPlugins(String server, String port) {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "appium",
                "-a", server,
                "-p", port,
                "-ka", "800",
                "--use-plugins", "appium-reporter-plugin,element-wait,gestures,device-farm,appium-dashboard",
                "-pa", "/",
                "--plugin-device-farm-platform", "android"
        );

        // Redirect error and output streams
        processBuilder.redirectErrorStream(true);

        try {
            // Start the process
            Process process = processBuilder.start();
            System.out.println("Appium server started with plugins.");

            // Optional: Read the output (if needed for debugging)
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}