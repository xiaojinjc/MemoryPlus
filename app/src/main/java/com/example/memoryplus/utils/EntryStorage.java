package com.example.memoryplus.utils;

import android.content.Context;
import android.util.Log;

//import com.example.memoryplus.model.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EntryStorage {

//    private static final Gson gson = new Gson();
//    private static final Type entryListType = new TypeToken<List<Entry>>(){}.getType();
//
//    // Save list of entries to a given filename (e.g., 2025.json)
//    public static boolean saveEntries(Context context, String filename, List<Entry> entries) {
//        String json = gson.toJson(entries, entryListType);
//        return FileManager.saveToFile(context, filename, json);
//    }
//
//    // Load entries from a given filename
//    public static List<Entry> loadEntries(Context context, String filename) {
//        if (!FileManager.fileExists(context, filename)) {
//            Log.d("EntryStorage", "File not found: " + filename);
//            return new ArrayList<>();  // Return empty list if file not found
//        }
//        String json = FileManager.readFromFile(context, filename);
//        Log.d("EntryStorage", "File not found: " + filename);
//        try {
//            List<Entry> entries = gson.fromJson(json, entryListType);
//            Log.d("EntryStorage", "Parsed " + entries.size() + " entries.");
//            return entries;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
}

//    // Save a list
//    List<Entry> entries = ...;
//        EntryStorage.saveEntries(context, "2025.json", entries);
//
//    // Load the list
//    List<Entry> loaded = EntryStorage.loadEntries(context, "2025.json");

