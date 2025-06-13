package com.example.memoryplus.utils;

import java.util.Calendar;

public class FilenameUtils {

    public static String getFilenameForYear(int year) {
        return year + ".json";
    }

    public static String getFilenameForCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return getFilenameForYear(year);
    }
}
