package com.example.memoryplus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // TODO: change format to dd/MM/yyyy
    // Standard format: yyyy-MM-dd (e.g., 2025-06-13)
    private static final SimpleDateFormat standardFormat = new SimpleDateFormat("dd/mm/yy", Locale.getDefault());

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

    public static String formatDate(Date date){
        return standardFormat.format(date);
    }

    public static Date parseDate(String dateString){
        try {
            return standardFormat.parse(dateString);
        } catch (Exception e){
            e.printStackTrace();
            return new Date();
        }
    }
}

