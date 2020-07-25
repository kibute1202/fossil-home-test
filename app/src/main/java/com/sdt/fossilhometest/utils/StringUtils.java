package com.sdt.fossilhometest.utils;

public final class StringUtils {

    private StringUtils() {}

    public static String safeString(String s) {
        return s == null ? "" : s;
    }
}
