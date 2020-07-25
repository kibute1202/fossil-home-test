package com.sdt.fossilhometest.utils;


import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public final class BindingUtils {

    private BindingUtils() {}

    @BindingAdapter({"colorFilter"})
    public static void setColorFilter(ProgressBar progressBar, int color) {
        ViewUtils.setColorFilter(progressBar.getIndeterminateDrawable(), color);
    }

    @BindingAdapter({"formatKeyword"})
    public static void formatKeywordOnTwoLine(TextView textView, String keyword) {
        textView.setText(formatKeyword(keyword));
    }

    static String formatKeyword(String element) {
        if (element != null && element.length() > 0) {
            String[] splitElement = element.split(" ");
            int splitLength = splitElement.length;
            if (splitLength > 1) {
                int mid = splitLength / 2;
                String midElement = splitElement[mid];
                int lenMid = midElement.length();

                String leftHalf = subElements(splitElement, 0, mid - 1);
                int lenLeft = leftHalf.length();

                String rightHalf = subElements(splitElement, mid + 1, splitLength - 1);
                int lenRight = rightHalf.length();

                if (lenLeft + lenMid <= lenRight) {
                    element = leftHalf + " " + midElement + "\n" + rightHalf;
                } else {
                    if (rightHalf.isEmpty()) {
                        element = leftHalf + "\n" + midElement;
                    } else {
                        element = leftHalf + "\n" + midElement + " " + rightHalf;
                    }
                }
            }
        }
        return element;
    }

    static String subElements(String[] root, int start, int end) {
        if (start < 0) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        if (end >= root.length) {
            throw new ArrayIndexOutOfBoundsException(end);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; ++i) {
            sb.append(root[i]);
            if (i < end) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
