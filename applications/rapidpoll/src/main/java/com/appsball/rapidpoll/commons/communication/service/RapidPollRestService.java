package com.appsball.rapidpoll.commons.communication.service;

import android.content.Context;

import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.UpdatePollStateRequest;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequestContainer;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.fillpoll.service.PollDetailsResponseCallback;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.WaspError;
import com.orhanobut.wasp.utils.LogLevel;
import com.orhanobut.wasp.utils.NetworkMode;

import java.util.List;

public class RapidPollRestService {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String FAILURE_MESSAGE = "FAILURE";
    public static final String GENERAL_ERROR_MESSAGE = "Unknown error occured while getting details.";
    RapidPollRestInterface rapidPollRestInterface;

    private RapidPollRestService(RapidPollRestInterface rapidPollRestInterface) {
        this.rapidPollRestInterface = rapidPollRestInterface;
    }

    public static RapidPollRestService createMockRestService(Context context) {
        Wasp.Builder builder = createMockRestInterfaceBuilder(context);
        return new RapidPollRestService(builder.build().create(RapidPollRestInterface.class));
    }

    public static RapidPollRestService createRapidPollRestService(Context context) {
        Wasp.Builder builder = createRapidPollInterfaceBuilder(context);
        return new RapidPollRestService(builder.build().create(RapidPollRestInterface.class));
    }

    private static Wasp.Builder createMockRestInterfaceBuilder(Context context) {
        return createRapidPollInterfaceBuilder(context)
                .setLogLevel(LogLevel.FULL)
                .setNetworkMode(NetworkMode.MOCK);
    }

    private static Wasp.Builder createRapidPollInterfaceBuilder(Context context) {
        return new Wasp.Builder(context).setEndpoint(SERVER_ADDRESS).setLogLevel(LogLevel.FULL);//.setParser(new RapidPollRestParser());
    }

    public void registerUser(RegisterRequest request, Callback<ResponseContainer<RegisterResponse>> callback) {
        rapidPollRestInterface.registerUser(request, callback);
    }

    public void managePoll(ManagePollRequest request, Callback<ResponseContainer<Object>> callback) {
        rapidPollRestInterface.managePoll(request, callback);
    }

    public void getPolls(PollsRequest pollsRequest, Callback<ResponseContainer<List<PollsResponse>>> callback) {
        rapidPollRestInterface.getPolls(pollsRequest.userId,
                pollsRequest.listType,
                pollsRequest.orderKey,
                pollsRequest.orderType,
                pollsRequest.pageSize,
                pollsRequest.page,
                callback);
    }

    public void pollDetails(PollDetailsRequest request, final PollDetailsResponseCallback callback) {
        rapidPollRestInterface.pollDetails(request.userId, request.pollId, request.code, new Callback<ResponseContainer<PollDetailsResponse>>() {
            @Override
            public void onSuccess(com.orhanobut.wasp.Response response, ResponseContainer<PollDetailsResponse> responseContainer) {
                if(SUCCESS_MESSAGE.equals(responseContainer.status)){
                    callback.onSuccess(responseContainer.result);
                }
                else if(FAILURE_MESSAGE.equals(responseContainer.status)){
                    callback.onWrongCodeGiven();
                }
                else if(!responseContainer.messages.isEmpty()){
                    callback.onError(responseContainer.messages.get(0));
                }
                else{
                    callback.onError(GENERAL_ERROR_MESSAGE);
                }
            }

            @Override
            public void onError(WaspError error) {
                callback.onError(error.getErrorMessage());
            }
        });
    }

    public void doPoll(DoPollRequest request, Callback<ResponseContainer<Object>> callback) {
        DoPollRequestContainer container = new DoPollRequestContainer();
        container.inputjson = request;
        rapidPollRestInterface.doPoll(request, callback);
    }

    public void pollResult(PollResultRequest pollResultRequest, Callback<ResponseContainer<PollResultResponse>> callback) {
        rapidPollRestInterface.pollResult(pollResultRequest.userId, pollResultRequest.pollId, callback);
    }

    public void searchPoll(SearchPollRequest searchPollRequest, Callback<ResponseContainer<List<PollsResponse>>> callback) {
        rapidPollRestInterface.searchPoll(searchPollRequest.userId,
                                          searchPollRequest.listType,
                                          searchPollRequest.searchItem,
                                          searchPollRequest.orderKey,
                                          searchPollRequest.orderType,
                                          searchPollRequest.pageSize,
                                          searchPollRequest.page,
                                          callback);
    }

    public void updatePollState(UpdatePollStateRequest request, Callback<ResponseContainer<Object>> callback) {
        rapidPollRestInterface.updatePollState(request, callback);
    }

}
