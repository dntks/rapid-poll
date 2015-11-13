package com.appsball.rapidpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appsball.rapidpoll.commons.communication.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.ResponseContainer;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RapidPollRestService service = new Wasp.Builder(this)
                .setEndpoint(SERVER_ADDRESS)
                .build()
                .create(RapidPollRestService.class);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.device_id ="kk";
        service.registerUser(registerRequest, new Callback<ResponseContainer<RegisterResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<RegisterResponse> registerResponseResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }
}
