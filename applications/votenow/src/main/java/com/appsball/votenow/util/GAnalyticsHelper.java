package com.appsball.votenow.util;

import android.app.Activity;
import android.content.Context;

import com.appsball.votenow.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GAnalyticsHelper {
    private static Tracker mTracker;

    synchronized public static Tracker getDefaultTracker(Context context) {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(context.getResources().getString(R.string.ga_trackingId));
        }
        return mTracker;
    }

	public static void trackEvent(final Context context, final SimpleAction action) {
		trackEvent(context, action, null);
	}

    public static void trackEvent(Context context, SimpleAction action, String label) {
        getDefaultTracker(context)
                .send(new HitBuilders.EventBuilder()
                        .setAction(action.toString())
                        .setLabel(label).build());
    }

    public static void activityStart(Activity activity) {
        getDefaultTracker(activity).setScreenName(activity.getLocalClassName());
    }
	public enum SimpleAction {
		QuestionEdited, AppStarted, AppsBallIconPressed, InfoButtonPressed,

		SupportEmailLinkPressed, ServerErrorOccured
	}
}
