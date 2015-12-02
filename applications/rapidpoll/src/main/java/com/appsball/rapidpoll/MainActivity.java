package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appsball.rapidpoll.commons.communication.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.response.GetPollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createManagePollRequest;
import static com.appsball.rapidpoll.commons.communication.request.RegisterRequest.registerRequest;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";
    private static final String TEST_POLL_ID ="2";

    private static final String TEST_DEVICE_ID = "11E592F746A409999E7502000029BDFD";
    private RapidPollRestService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Wasp.Builder(this)
                .setEndpoint(SERVER_ADDRESS)
//                .setNetworkMode(NetworkMode.MOCK)
                .build()
                .create(RapidPollRestService.class);

//        register();
        createPoll();
        getPolls(createPollsRequest());
        getPollDetails();
    }

    private void getPollDetails() {
        service.pollDetails(TEST_DEVICE_ID, TEST_POLL_ID, new Callback<ResponseContainer<PollDetailsResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<PollDetailsResponse> pollDetailsResponseModelResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    private PollsRequest createPollsRequest() {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withUserId(TEST_DEVICE_ID);
        builder.withListType(ListType.ALL);
        builder.withOrderKey(OrderKey.TITLE);
        builder.withOrderType(OrderType.DESC);
        builder.withPage("1");
        builder.withPageSize("25");
        return builder.build();
    }

    private void createPoll() {
        service.managePoll(createManagePollRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    private void register() {
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

    private void getPolls(PollsRequest pollsRequest) {
        service.getPolls(pollsRequest.userId,
                pollsRequest.listType,
                pollsRequest.orderKey,
                pollsRequest.orderType,
                pollsRequest.pageSize, pollsRequest.page,
                new Callback<ResponseContainer<List<GetPollsResponse>>>() {
                    @Override
                    public void onSuccess(Response response, ResponseContainer<List<GetPollsResponse>> listResponseContainer) {

                    }

                    @Override
                    public void onError(WaspError error) {

                    }
                }
        );
    }
}
