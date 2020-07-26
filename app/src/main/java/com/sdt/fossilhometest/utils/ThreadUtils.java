package com.sdt.fossilhometest.utils;

public final class ThreadUtils {

    public static void delay(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ThreadUtils() {}
}
