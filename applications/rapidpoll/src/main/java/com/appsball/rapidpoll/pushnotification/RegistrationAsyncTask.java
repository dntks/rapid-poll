package com.appsball.rapidpoll.pushnotification;

import android.content.Context;
import android.os.AsyncTask;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.register.UserRegister;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;

import java.io.IOException;

public class RegistrationAsyncTask extends AsyncTask<Void, Void, Optional<String>> {
    private final UserRegister userRegister;
    private final Context context;

    public RegistrationAsyncTask(UserRegister userRegister, Context context) {
        this.userRegister = userRegister;
        this.context = context;
    }

    @Override
    protected Optional<String> doInBackground(Void... params) {
        InstanceID instanceID = InstanceID.getInstance(context);
        Optional<String> optionalToken = Optional.absent();
        try {
            String token = instanceID.getToken(context.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            optionalToken = Optional.of(token);
            Logger.i("GCM registered");
        } catch (IOException e) {
            Logger.e(e, "GCM register failed");
        }
        return optionalToken;
    }

    @Override
    protected void onPostExecute(Optional<String> userToken) {
        super.onPostExecute(userToken);
        if (userToken.isPresent()) {
            userRegister.registerUser(userToken.get());
        } else {
            userRegister.gcmFailed();
        }
    }
}
