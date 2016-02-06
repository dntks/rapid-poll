package com.appsball.rapidpoll.commons.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.utils.Utils;


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

    public static void showErrorDialog(final Activity activity, String errorMessage, OnClickListener clickListener) {
        showErrorDialog(activity, errorMessage, clickListener, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
            }
        });
    }

    public static void showErrorDialog(final Activity activity,
                                       String errorMessage,
                                       OnClickListener okClickListener,
                                       final OnClickListener cancelClickListener) {
        showErrorDialog(activity, errorMessage, activity.getString(R.string.cancel), okClickListener, cancelClickListener);
    }

    public static void showErrorDialog(final Activity activity,
                                       String errorMessage,
                                       String negativeButtonText,
                                       OnClickListener okClickListener,
                                       final OnClickListener cancelClickListener) {
        showErrorDialog(activity, errorMessage, activity.getString(R.string.ok), negativeButtonText, okClickListener, cancelClickListener);
    }

    public static void showErrorDialog(final Activity activity,
                                       String errorMessage,
                                       String positiveButtonText,
                                       String negativeButtonText,
                                       OnClickListener okClickListener,
                                       final OnClickListener cancelClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(0);
        builder.setMessage(errorMessage);

        builder.setPositiveButton(positiveButtonText, okClickListener);
        builder.setNegativeButton(negativeButtonText, cancelClickListener);
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
                                          final TextEnteredListener textEnteredListener) {
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

    public static void showEmailInputDialog(final Activity activity,
                                            final String errorMessage,
                                            final String editTextMessage,
                                            final TextEnteredListener textEnteredListener) {
        final EditText inputView = new EditText(activity);
        inputView.setHint(activity.getString(R.string.email_hint));
        inputView.setText(editTextMessage);
        new android.support.v7.app.AlertDialog.Builder(activity)
                .setIcon(0)
                .setMessage(errorMessage)
                .setView(inputView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editTextContent = inputView.getText().toString();
                        dialog.dismiss();
                        if (Utils.isValidEmail(editTextContent)) {
                            textEnteredListener.textEntered(editTextContent);
                        } else {
                            showEmailInputDialog(activity, activity.getString(R.string.enter_email_wrong_format),
                                    editTextMessage, textEnteredListener);
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

    private static android.support.v7.app.AlertDialog loadingDialog;

    public static void showLoadingDialog(Context context, String message) {
        hideLoadingDialog();
        loadingDialog = new android.support.v7.app.AlertDialog.Builder(context)
                .setIcon(0)
                .setMessage(message)
                .setView(R.layout.loading_view).create();
        loadingDialog.show();
    }

    public static void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing() && loadingDialog.getContext() != null) {
            loadingDialog.hide();
        }
    }
}
