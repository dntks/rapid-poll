package com.appsball.votenow.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.appsball.votenow.R;
import com.appsball.votenow.util.DeviceUtil;
import com.appsball.votenow.util.GAnalyticsHelper;
import com.appsball.votenow.util.GAnalyticsHelper.SimpleAction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class FeedbackActivity extends FragmentActivity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	@Override
	public void onStart() {
		super.onStart();
		GAnalyticsHelper.activityStart(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		GAnalyticsHelper.trackEvent(this, SimpleAction.AppStarted);

		DialogInfo.showInfoDialogOnFirstRun(getSupportFragmentManager(), getApplicationContext());

		if (savedInstanceState == null) {
			changeFragment(HomePageFragment.newInstance());
			checkPlayServices();

			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					String deviceId = DeviceUtil.getDeviceId(getApplicationContext());
					Log.e("DEVID:", deviceId);
					return null;
				}
			}.execute();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// String rateCode =
		// getIntent().getExtras().getString(GCMReceiver.RATE_CODE_BY_NOTIFICATION);
		String action = getIntent().getAction();
		String rateCode = action;
		if (rateCode != null && rateCode.length() > 0 && !rateCode.startsWith("android")) {
			Log.e("NOTIFICATION", "From notification: " + rateCode);
			changeFragment(RateResultFragment.newInstance(rateCode));
		}
	}

	public void changeFragment(BaseFragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		setTitle(fragment.getTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_info) {
			DialogInfo.showInfoDialog(getSupportFragmentManager());
			GAnalyticsHelper.trackEvent(this, SimpleAction.InfoButtonPressed);
			return true;
		}
		if (id == R.id.action_appsball) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.appsball.com"));
			startActivity(browserIntent);
			GAnalyticsHelper.trackEvent(this, SimpleAction.AppsBallIconPressed);
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("PlayServices", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		BaseFragment currentFragment = null;
		for (Fragment f : getSupportFragmentManager().getFragments()) {
			if (f instanceof BaseFragment)
				currentFragment = (BaseFragment) f;
		}
		Log.e("fragment", "f: " + currentFragment + "  " + getSupportFragmentManager().getFragments().size());
		if (currentFragment == null || !currentFragment.doBackButtonAction()) {
			super.onBackPressed();
		}
	}
}
