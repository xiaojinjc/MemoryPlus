package com.example.memoryplus.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileManager {

    // Save a JSON string to a file
    public static boolean saveToFile(Context context, String filename, String jsonContent) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    context.openFileOutput(filename, Context.MODE_PRIVATE)
            );
            writer.write(jsonContent);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Load a JSON string from a file
    public static String readFromFile(Context context, String filename) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.openFileInput(filename))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Check if the file exists
    public static boolean fileExists(Context context, String filename) {
        return context.getFileStreamPath(filename).exists();
    }
}

// String filename = "2025.json";
//
//// Save
//boolean success = FileManager.saveToFile(this, filename, jsonString);
//
//// Load
//String jsonString = FileManager.readFromFile(this, filename);
//
//// Check existence
//if (FileManager.fileExists(this, filename)) {
//    // Load or update
//}
