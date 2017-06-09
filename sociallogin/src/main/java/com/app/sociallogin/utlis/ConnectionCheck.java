package com.app.sociallogin.utlis;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.app.sociallogin.R;


public class ConnectionCheck {

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            showconnectiondialog(context);
            return false;
        } else
            return true;
    }

    public AlertDialog.Builder showconnectiondialog(Context context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setIcon(R.drawable.ic_action_internet_off)
                .setTitle("Error!")
                .setCancelable(false)
                .setMessage("No Internet Connection.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder;
    }
}