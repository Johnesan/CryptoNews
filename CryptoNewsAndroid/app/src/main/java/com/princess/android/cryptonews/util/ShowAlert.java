package com.princess.android.cryptonews.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;

import com.princess.android.cryptonews.R;

/**
 * Created by Princess on 3/7/2018.
 */

public class ShowAlert {

    // Build an AlertDialog
    AlertDialog.Builder builder;

    public void showAlertDialog(Context context, String title, String message, Boolean status, boolean restart, Activity Source, Class Destination) {


        builder = new AlertDialog.Builder(context);
        // Set a title for alert dialog
        builder.setTitle(title);
        builder.setMessage(message);
        if(status != null)
            // Setting alert dialog icon
        builder.setIcon(R.mipmap.ic_alert);

        // Set the alert dialog yes button click listener
        builder.setNegativeButton("OK", (dialog, whichButton) -> {

            if (restart){
                Source.finish();
                Intent  intent = new Intent(Source, Destination);
                context.startActivity(intent);
            }
        });
        builder.show();
    }




}
