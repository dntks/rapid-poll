package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appsball.rapidpoll.commons.communication.RapidPollRestInterface;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.UpdatePollStateRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.request.enums.PollState;
import com.appsball.rapidpoll.commons.communication.response.GetPollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;
import com.orhanobut.wasp.utils.LogLevel;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createDoPollRequest;
import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createManagePollRequest;
import static com.appsball.rapidpoll.commons.communication.request.RegisterRequest.registerRequest;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";
    private static final String TEST_POLL_ID ="2";

    private static final String TEST_DEVICE_ID = "11E592F746A409999E7502000029BDFD";
    private RapidPollRestInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Wasp.Builder(this)
                .setEndpoint(SERVER_ADDRESS)
                        .setLogLevel(LogLevel.FULL)
//                .setNetworkMode(NetworkMode.MOCK)
                .build()

                .create(RapidPollRestInterface.class);

//        getPollDetails();
//        getPollResult();
//        searchPoll();
//        register();


//        createPoll();
//        getPolls(createPollsRequest());
//        doPoll();
        updatePollState();
    }

    private void updatePollState() {
        service.updatePollState(createUpdatePollStateRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    public UpdatePollStateRequest createUpdatePollStateRequest() {
        UpdatePollStateRequest.Builder builder = UpdatePollStateRequest.builder();
        builder.withUserId(TEST_DEVICE_ID);
        builder.withPollId(TEST_POLL_ID);
        builder.withPollState(PollState.PUBLISHED);
        return builder.build();
    }
    private void searchPoll() {
        service.searchPoll(TEST_DEVICE_ID, ListType.ALL.name(), "west", OrderKey.PUBLIC.name(),
                OrderType.DESC.name(), "20", "1", new Callback<ResponseContainer<List<GetPollsResponse>>>() {
                    @Override
                    public void onSuccess(Response response, ResponseContainer<List<GetPollsResponse>> listResponseContainer) {

                    }

                    @Override
                    public void onError(WaspError error) {

                    }
                });
    }

    private void getPollResult() {
        service.pollResult(TEST_DEVICE_ID, TEST_POLL_ID, new Callback<ResponseContainer<PollResultResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<PollResultResponse> pollResultResponseResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    private void doPoll() {
        service.doPoll(createDoPollRequest(), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {

            }

            @Override
            public void onError(WaspError error) {

            }
        });
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
