package com.example.memoryplus.utils;

public class CategoryUtils {

    // Build a display-friendly category string
    public static String formatCategory(String group, String type) {
        return group + " → " + type;
    }

    // Extract group from category (e.g., "Media" from "Media → Anime")
    public static String getCategory(String category) {
        String[] parts = category.split(" → ");
        return parts.length > 0 ? parts[0] : "";
    }

    // Extract type (e.g., "Anime")
    public static String getType(String category) {
        String[] parts = category.split(" → ");
        return parts.length > 1 ? parts[1] : "";
    }
}

