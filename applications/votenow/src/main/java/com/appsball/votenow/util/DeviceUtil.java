package com.appsball.votenow.util;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.appsball.votenow.ui.FeedbackActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class DeviceUtil {

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String SENDER_ID = "585263852993";

	public static String getDeviceType(Context context) {
		return "Android";
	}

	public static String getDeviceIdGenerated(Context context) {
		String id = getRegistrationId(context);
		if (id == null || id.length() == 0) {
			id = "";
			char[] chars = getCharArray();
			Random r = new Random();
			for (int i = 0; i < 40; i++) {
				id += chars[r.nextInt(chars.length)];
			}
			storeRegistrationId(context, id);
		}
		return id;
	}

	private static char[] getCharArray() {
		int num = 'z' - 'a' + 'Z' - 'A' + '9' - '0' + 3;

		char[] chars = new char[num];
		int i = 0;
		for (char ch = 'a'; ch <= 'z'; ch++) {
			chars[i] = ch;
			i++;
		}
		for (char ch = 'A'; ch <= 'Z'; ch++) {
			chars[i] = ch;
			i++;
		}
		for (char ch = '0'; ch <= '9'; ch++) {
			chars[i] = ch;
			i++;
		}

		return chars;
	}

	public static String getDeviceId(Context context) {
		String regid = getRegistrationId(context);
		if (regid == null || regid.length() == 0) {
			try {
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
				regid = gcm.register(SENDER_ID);
				storeRegistrationId(context, regid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.e("REGID", regid);
		return regid;
	}

	private static String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId == null || registrationId.length() == 0) {
			Log.i("PlayServices", "Registration not found.");
			return "";
		}

		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("Play services", "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private static SharedPreferences getGCMPreferences(Context context) {
		return context.getSharedPreferences(FeedbackActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}
}
