package com.appsball.rapidpoll;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.appsball.rapidpoll.commons.communication.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.ResponseContainer;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;
import com.orhanobut.wasp.utils.NetworkMode;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

}