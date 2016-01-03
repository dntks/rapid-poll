package com.appsball.rapidpoll.commons.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.appsball.rapidpoll.R;


public class DialogsBuilder {
    public static void showNoNetConnectionDialog(final Activity activity, OnClickListener okButtonListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Network error");
        builder.setMessage("Can't connect to internet, please check your connection");
        builder.setPositiveButton(R.string.device_settings, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.ok, okButtonListener);
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showErrorDialog(final Activity activity) {
        showErrorDialog(activity, "Error", "Some error occured.");
    }

    public static void showErrorDialog(final Activity activity, String errorMessage) {
        showErrorDialog(activity, "Error", errorMessage);
    }

    public static void showErrorDialog(final Activity activity, String errorTitle, String errorMessage) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(errorTitle);
        builder.setMessage(errorMessage);

        builder.setNegativeButton(R.string.ok, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showErrorDialog(final Activity activity, String errorTitle, String errorMessage, OnClickListener clickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(errorMessage);
        builder.setIcon(0);

        builder.setPositiveButton(R.string.ok, clickListener);
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showDialogWithOnlyOkButton(final Activity activity, String errorMessage, OnClickListener clickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(errorMessage);
        builder.setIcon(0);

        builder.setPositiveButton(R.string.ok, clickListener);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showErrorDialog(final Activity activity, String errorMessage, OnClickListener okClickListener,
                                       final OnClickListener cancelClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(0);
        builder.setMessage(errorMessage);

        builder.setPositiveButton(R.string.ok, okClickListener);
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                cancelClickListener.onClick(dialog, which);
                dialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showErrorDialog(final Activity activity,
                                       String errorMessage,
                                       String negativeButtonText,
                                       OnClickListener okClickListener,
                                       final OnClickListener cancelClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(0);
        builder.setMessage(errorMessage);

        builder.setPositiveButton(R.string.ok, okClickListener);
        builder.setNegativeButton(negativeButtonText, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                cancelClickListener.onClick(dialog, which);
                dialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public static void showEditTextDialog(final Activity activity,
                                          String errorMessage,
                                          String editTextHint,
                                          final TextEnteredListener textEnteredListener){
        final EditText inputView = new EditText(activity);
        inputView.setHint(editTextHint);
        new android.support.v7.app.AlertDialog.Builder(activity)
                .setIcon(0)
                .setMessage(errorMessage)
                .setView(inputView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editTextContent = inputView.getText().toString();
                        if (!"".equals(editTextContent)) {
                            textEnteredListener.textEntered(editTextContent);
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
    }

}
