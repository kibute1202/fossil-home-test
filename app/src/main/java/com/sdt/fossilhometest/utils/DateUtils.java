package com.sdt.fossilhometest.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public final class DateUtils {

    public static final String BASE_DATE = "dd-MM-yyyy";

    public static String getBaseDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format(BASE_DATE, cal).toString();
    }

    private DateUtils() {}

}
