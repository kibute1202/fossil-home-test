package com.sdt.fossilhometest.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

public class ViewUtils {

    private ViewUtils() {
    }

    public static void setColorFilter(Drawable drawable, int color) {
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
    }

}
