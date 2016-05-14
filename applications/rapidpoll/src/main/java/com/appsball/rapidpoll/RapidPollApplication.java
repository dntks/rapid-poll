package com.appsball.rapidpoll;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class RapidPollApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }

}
