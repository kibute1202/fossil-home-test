package com.sdt.fossilhometest.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.sdt.fossilhometest.R;

public class LoadingDialog {

    private Context context;
    private Dialog dialog;

    public LoadingDialog(@NonNull Context context) {
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_processing_dialog, null);
        dialog = new Dialog(context, R.style.ProcessingDialogTheme);
        dialog.setContentView(view);
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void show() {
        if (isShowing()) {
            dismiss();
        }
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
