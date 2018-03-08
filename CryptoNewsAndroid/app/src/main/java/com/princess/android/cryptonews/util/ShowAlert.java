package com.princess.android.cryptonews.util;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;

import com.princess.android.cryptonews.R;

/**
 * Created by Princess on 3/7/2018.
 */

public class ShowAlert {

    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set a title for alert dialog
        builder.setTitle(title);
        builder.setMessage(message);
        if(status != null)
            // Setting alert dialog icon
        builder.setIcon(R.mipmap.ic_alert);

        // Set the alert dialog yes button click listener
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        builder.show();
    }
}
