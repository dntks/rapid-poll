package com.appsball.rapidpoll.commons.communication.service;

import android.content.Context;

import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.UpdatePollStateRequest;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.GetPollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.utils.LogLevel;
import com.orhanobut.wasp.utils.NetworkMode;

import java.util.List;

public class RapidPollRestService {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";
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
        return new Wasp.Builder(context).setEndpoint(SERVER_ADDRESS);
    }

    public void registerUser(RegisterRequest request, Callback<ResponseContainer<RegisterResponse>> callback) {
        rapidPollRestInterface.registerUser(request, callback);
    }

    public void managePoll(ManagePollRequest request, Callback<ResponseContainer<Object>> callback) {
        rapidPollRestInterface.managePoll(request, callback);
    }

    public void getPolls(PollsRequest pollsRequest, Callback<ResponseContainer<List<GetPollsResponse>>> callback) {
        rapidPollRestInterface.getPolls(pollsRequest.userId,
                                        pollsRequest.listType,
                                        pollsRequest.orderKey,
                                        pollsRequest.orderType,
                                        pollsRequest.pageSize,
                                        pollsRequest.page,
                                        callback);
    }

    public void pollDetails(PollDetailsRequest request, Callback<ResponseContainer<PollDetailsResponse>> callback) {
        rapidPollRestInterface.pollDetails(request.userId, request.pollId, callback);
    }

    public void doPoll(DoPollRequest request, Callback<ResponseContainer<Object>> callback) {
        rapidPollRestInterface.doPoll(request, callback);
    }

    public void pollResult(PollResultRequest pollResultRequest, Callback<ResponseContainer<PollResultResponse>> callback) {
        rapidPollRestInterface.pollResult(pollResultRequest.userId, pollResultRequest.pollId, callback);
    }

    public void searchPoll(SearchPollRequest searchPollRequest, Callback<ResponseContainer<List<GetPollsResponse>>> callback) {
        rapidPollRestInterface.searchPoll(searchPollRequest.userId,
                                          searchPollRequest.listType,
                                          searchPollRequest.searchItem,
                                          searchPollRequest.orderKey,
                                          searchPollRequest.orderType,
                                          searchPollRequest.pageSize,
                                          searchPollRequest.page,
                                          callback);
    }

    public void updatePollState(UpdatePollStateRequest request, Callback<ResponseContainer<Object>> callback){
        rapidPollRestInterface.updatePollState(request, callback);
    }

}
