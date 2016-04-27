package com.appsball.rapidpoll.register;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.appsball.rapidpoll.pushnotification.RegistrationAsyncTask;
import com.appsball.rapidpoll.pushnotification.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static com.appsball.rapidpoll.commons.utils.Constants.PLAY_SERVICES_RESOLUTION_REQUEST;

public class GCMRegister {

    private static final String TAG = "GCMRegister";
    private final Activity activity;
    private final UserRegister userRegister;

    public GCMRegister(Activity activity, UserRegister userRegister) {
        this.activity = activity;
        this.userRegister = userRegister;
    }

    public void registerGCM() {
        if (checkPlayServices()) {
            Intent intent = new Intent(activity, RegistrationIntentService.class);
            activity.startService(intent);
            RegistrationAsyncTask registrationAsyncTask = new RegistrationAsyncTask(userRegister, activity);
            registrationAsyncTask.execute();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }
}
