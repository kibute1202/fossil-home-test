package com.sdt.fossilhometest.utils;


import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.sdt.fossilhometest.R;

public final class BindingUtils {

    private BindingUtils() {
    }

    public static void gone(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void visible(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void invisible(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter(
        value = {
            "circleImageUrl",
            "placeholder"
        }
    )
    public static void loadCircleImage(ImageView view,
                                       String url,
                                       Drawable placeholder) {

        if (TextUtils.isEmpty(url)) {
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view.setImageDrawable(placeholder);
        } else {

            RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .circleCrop();

            Glide.with(view.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(view);
        }

    }

    @BindingAdapter({"numberToText"})
    public static void setTextWithNumber(TextView view, int num) {
        view.setText(String.valueOf(num));
    }

    @BindingAdapter(value = {"verticalList"}, requireAll = false)
    public static void setupVerticalList(RecyclerView view, boolean hasFixSize) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
            view.getContext(),
            LinearLayoutManager.VERTICAL,
            false
        );
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(hasFixSize);
    }

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
