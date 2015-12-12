package com.appsball.rapidpoll.commons.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;

import com.appsball.rapidpoll.R;


public class DialogsBuilder {
	public static void showNoNetConnectionDialog(final Activity activity) {
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
		builder.setTitle(errorTitle);
		builder.setMessage(errorMessage);

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

	public static void showErrorDialog(final Activity activity, String errorTitle, String errorMessage, OnClickListener okClickListener,
			final OnClickListener cancelClickListener) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(errorTitle);
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
}
