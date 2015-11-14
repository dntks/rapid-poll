package com.appsball.rapidpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appsball.rapidpoll.commons.communication.DefaultBuilders;
import com.appsball.rapidpoll.commons.communication.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.PollRequestObject;
import com.appsball.rapidpoll.commons.communication.QuestionRequestObject;
import com.appsball.rapidpoll.commons.communication.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.ResponseContainer;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;
import com.orhanobut.wasp.utils.NetworkMode;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.DefaultBuilders.createManagePollRequest;
import static com.appsball.rapidpoll.commons.communication.RegisterRequest.registerRequest;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";

    String id = "11E58A51BCEF4F919E7502000029BDFD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RapidPollRestService service = new Wasp.Builder(this)
                .setEndpoint(SERVER_ADDRESS)
//                .setNetworkMode(NetworkMode.MOCK)
                .build()
                .create(RapidPollRestService.class);

        register(service);
        createPoll(service);
    }

    private void createPoll(RapidPollRestService service) {
        service.managePoll(createManagePollRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }



    private void register(RapidPollRestService service) {
        RegisterRequest registerRequest = registerRequest("kk");
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
