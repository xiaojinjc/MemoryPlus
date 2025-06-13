package com.example.memoryplus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Standard format: yyyy-MM-dd (e.g., 2025-06-13)
    private static final SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    // Friendly display format: e.g., June 13, 2025
    private static final SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);

    // Get today’s date as a string in standard format
    public static String getTodayDate() {
        return standardFormat.format(new Date());
    }

    // Convert standard format to display format
    public static String toDisplayFormat(String dateString) {
        try {
            Date date = standardFormat.parse(dateString);
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // fallback
        }
    }

    // Optional: Check if two dates are the same day
    public static boolean isSameDay(String date1, String date2) {
        return date1.equals(date2);
    }
}

