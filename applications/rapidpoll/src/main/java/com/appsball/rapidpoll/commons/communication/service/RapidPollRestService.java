package com.appsball.rapidpoll.commons.communication.service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.appsball.rapidpoll.commons.communication.request.ExportPollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.UpdatePollStateRequest;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.request.file.FileRequest;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.ManagePollResponse;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.utils.LogLevel;
import com.orhanobut.wasp.utils.NetworkMode;

import java.io.File;
import java.util.List;

public class RapidPollRestService {

    public static final String SERVER_ADDRESS = "http://rapidpoll.appsball.com:3000";
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    RapidPollRestInterface rapidPollRestInterface;
    private Context context;

    private RapidPollRestService(RapidPollRestInterface rapidPollRestInterface, Context context) {
        this.rapidPollRestInterface = rapidPollRestInterface;
        this.context = context;
    }

    public static RapidPollRestService createMockRestService(Context context) {
        Wasp.Builder builder = createMockRestInterfaceBuilder(context);
        return new RapidPollRestService(builder.build().create(RapidPollRestInterface.class), context);
    }

    public static RapidPollRestService createRapidPollRestService(Context context) {
        Wasp.Builder builder = createRapidPollInterfaceBuilder(context);
        return new RapidPollRestService(builder.build().create(RapidPollRestInterface.class), context);
    }

    private static Wasp.Builder createMockRestInterfaceBuilder(Context context) {
        return createRapidPollInterfaceBuilder(context)
                .setLogLevel(LogLevel.FULL)
                .setNetworkMode(NetworkMode.MOCK);
    }

    private static Wasp.Builder createRapidPollInterfaceBuilder(Context context) {
        return new Wasp.Builder(context).setEndpoint(SERVER_ADDRESS).setLogLevel(LogLevel.FULL);
    }

    public void registerUser(RegisterRequest request, ResponseContainerCallback<RegisterResponse> callback) {
        DialogsBuilder.showLoadingDialog(context, "Please wait");
        rapidPollRestInterface.registerUser(request, new DefaultResponseContainerCallback<>(callback));
    }

    public void managePoll(ManagePollRequest request, ResponseContainerCallback<ManagePollResponse> callback) {
        DialogsBuilder.showLoadingDialog(context, "Saving poll modifications...");
        rapidPollRestInterface.managePoll(request, new DefaultResponseContainerCallback<>(callback));
    }

    public void getPolls(PollsRequest pollsRequest, ResponseContainerCallback<List<PollsResponse>> callback) {
        rapidPollRestInterface.getPolls(pollsRequest.userId,
                pollsRequest.listType,
                pollsRequest.orderKey,
                pollsRequest.orderType,
                pollsRequest.pageSize,
                pollsRequest.page,
                new DefaultResponseContainerCallback<>(callback));
    }

    public void pollDetails(PollDetailsRequest request, ResponseContainerCallback<PollDetailsResponse> callback) {
        rapidPollRestInterface.pollDetails(request.userId, request.pollId, request.code, new DefaultResponseContainerCallback<>(callback));
    }

    public void doPoll(DoPollRequest request, ResponseCallback callback) {
        DialogsBuilder.showLoadingDialog(context, "Submitting votes...");
        rapidPollRestInterface.doPoll(request, new EmptyResponseCallback(callback));
    }

    public void pollResult(PollResultRequest pollResultRequest, ResponseContainerCallback<PollResultResponse> callback) {
        rapidPollRestInterface.pollResult(pollResultRequest.userId, pollResultRequest.pollId, pollResultRequest.pollCode, new DefaultResponseContainerCallback<>(callback));
    }

    public void searchPoll(SearchPollRequest searchPollRequest, ResponseContainerCallback<List<PollsResponse>> callback) {
        rapidPollRestInterface.searchPoll(searchPollRequest.userId,
                searchPollRequest.listType,
                searchPollRequest.searchItem,
                searchPollRequest.orderKey,
                searchPollRequest.orderType,
                searchPollRequest.pageSize,
                searchPollRequest.page,
                new DefaultResponseContainerCallback<>(callback));
    }

    public void updatePollState(UpdatePollStateRequest request, ResponseCallback callback) {
        DialogsBuilder.showLoadingDialog(context, "Updating poll state...");
        rapidPollRestInterface.updatePollState(request, new EmptyResponseCallback(callback));
    }

    public void exportPollResult(ExportPollResultRequest request, final ResponseContainerCallback<File> callback) {
        DialogsBuilder.showLoadingDialog(context, "Saving file to share...");
        String url = Utils.ON_SLASH_JOINER.join(SERVER_ADDRESS, "pollresultexport", request.userId, request.pollId, request.exportType.name(), request.code);
        FileRequest fileRequest = new FileRequest(request,
                url,
                context,
                new com.android.volley.Response.Listener<File>() {
                    @Override
                    public void onResponse(File response) {
                        DialogsBuilder.hideLoadingDialog();
                        callback.onSuccess(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogsBuilder.hideLoadingDialog();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(fileRequest);
    }

}
