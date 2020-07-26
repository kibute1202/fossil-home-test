package com.sdt.fossilhometest.utils;

public final class StringUtils {

    private StringUtils() {}

    public static String safe(String s) {
        return s == null ? "" : s;
    }
}
