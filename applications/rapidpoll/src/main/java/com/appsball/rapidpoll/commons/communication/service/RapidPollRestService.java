package com.appsball.rapidpoll.commons.communication.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

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
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.utils.LogLevel;
import com.orhanobut.wasp.utils.NetworkMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        rapidPollRestInterface.registerUser(request, new DefaultResponseContainerCallback<>(callback));
    }

    public void managePoll(ManagePollRequest request, ResponseContainerCallback<ManagePollResponse> callback) {
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
        rapidPollRestInterface.updatePollState(request, new EmptyResponseCallback(callback));
    }

    public void exportPollResult(ExportPollResultRequest request, final ResponseCallback callback) {
        FileRequest fileRequest =
                new FileRequest("http://rapidpoll.appsball.com:3000/pollresultexport/" + request.userId + "/" + request.pollId + "/" + request.exportType.name() + "/" + request.code,
                        context,
                        new com.android.volley.Response.Listener<File>() {
                            @Override
                            public void onResponse(File response) {

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) ;

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(fileRequest);
        /*
        rapidPollRestInterface.exportPollResult(request.userId,
                request.pollId, request.exportType.name(), request.code,
                new Callback<String>() {
                    @Override
                    public void onSuccess(Response response, String object) {
                        String toFile = object;
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(WaspError error) {

                    }
                });
        */
    }
    public Uri saveImageAlternativeMode(Bitmap bmp, String filename) {
        File lastpicture = new File(new File(Environment.getExternalStorageDirectory(), "Pictures" ), filename);

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(lastpicture);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout != null)
                    fout.close();
            } catch (IOException ignore) {
            }
        }
        return Uri.fromFile(lastpicture);
    }

}
