package com.sdt.fossilhometest.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public final class DialogUtils {

    private DialogUtils() {
    }

    public interface PositiveListener {
        void onClick();
    }

    public interface NegativeListener {
        void onClick();
    }

    public static AlertDialog showDialog(Context context,
                                         String title, String message,
                                         String textPositive, PositiveListener positiveListener,
                                         String textNegative, NegativeListener negativeListener,
                                         boolean cancelable, boolean canceledOnTouchOutside) {
        if (context == null) return null;

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(textPositive, (dialog, which) -> {
                if (positiveListener != null)
                    positiveListener.onClick();
            })
            .setNegativeButton(textNegative, ((dialog, which) -> {
                if (negativeListener != null)
                    negativeListener.onClick();
            }))
            .setCancelable(cancelable)
            .create();

        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alertDialog.show();

        return alertDialog;
    }

    public static AlertDialog showDialog(Context context,
                                         String message,
                                         String textPositive) {

        return showDialog(context,
            null, message,
            textPositive, null,
            null, null,
            true, true);
    }
}
