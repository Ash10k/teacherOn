package com.ash.teacheron.commonComponents;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.ash.teacheron.R;


public class NetworkLoader {
    private AlertDialog progressDialog;

    // Method to show the progress dialog
    public void showLoadingDialog(Context context) {
        if (progressDialog == null) {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);

            // Create and configure the AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            builder.setCancelable(false); // Prevents the dialog from being canceled by back button or outside touch

            progressDialog = builder.create();

            // Set the position of the dialog
            WindowManager.LayoutParams layoutParams = progressDialog.getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            layoutParams.x = 0;
            layoutParams.y = 0;
            progressDialog.getWindow().setAttributes(layoutParams);
        }

        // Show the dialog
        progressDialog.show();
    }
    public void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
