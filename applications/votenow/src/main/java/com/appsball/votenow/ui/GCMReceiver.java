package com.appsball.votenow.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.appsball.votenow.R;

public class GCMReceiver extends BroadcastReceiver {
	public final static String RATE_CODE_BY_NOTIFICATION = "RATE_CODE_BY_NOTIFICATION";
	public final static String RATE_CODE_IN_MESSAGE = "ratecode";
	public final static String RATE_TITLE = "title";
	public final static String RATE_ID = "id";

	@Override
	public void onReceive(Context context, Intent receivedIntent) {
		String ratecode = receivedIntent.getStringExtra(RATE_CODE_IN_MESSAGE);
		String title = receivedIntent.getStringExtra(RATE_TITLE);
		Intent intent = new Intent(context, FeedbackActivity.class);
		intent.setAction(ratecode);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		Notification n = new NotificationCompat.Builder(context).setContentTitle("Your question closed").setContentText(title)
				.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent).setAutoCancel(true).build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		int id = Integer.parseInt(receivedIntent.getStringExtra(RATE_ID));
		notificationManager.notify(id, n);

		try {
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(context, notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
