package com.appsball.rapidpoll.commons.communication.service;

import android.content.Context;
import android.os.AsyncTask;

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
import com.google.gson.Gson;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Wasp;
import com.orhanobut.wasp.utils.LogLevel;
import com.orhanobut.wasp.utils.NetworkMode;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
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

    public void pollDetails(PollDetailsRequest request, Callback<ResponseContainer<PollDetailsResponse>> callback) {
        rapidPollRestInterface.pollDetails(request.userId, request.pollId, callback);
    }

    public void doPoll(DoPollRequest request, Callback<String> callback) {
        DoPollRequestContainer container = new DoPollRequestContainer();
        container.inputjson = request;//"{\"user_id\":\"11E58407B7A5FDDC9D0B8675BA421DCB\"}";//new Gson().toJson(request);
        rapidPollRestInterface.doPoll(container, callback);
        PostRequestTask postRequestTask = new PostRequestTask();
//        postRequestTask.execute();
    }

   public class PostRequestTask extends AsyncTask<Void,Void,Void>{

       @Override
       protected Void doInBackground(Void... params) {

           String value = "{\"user_id\":\"11E58407B7A5FDDC9D0B8675BA421DCB\", \"poll_id\":\"1\", \"questions\":[ {\"question_id\":\"1\",\"answers\":[{\"alternative_id\":\"1\"}]}, {\"question_id\":\"2\",\"answers\":[{\"alternative_id\":\"3\"}]}, {\"question_id\":\"3\",\"answers\":[{\"alternative_id\":\"7\"}]}, {\"question_id\":\"4\",\"answers\":[{\"alternative_id\":\"12\"}]} ], \"comment\":\"Here is a comment.\" }";

           OkHttpClient client = new OkHttpClient();
           RequestBody formBody = new FormEncodingBuilder()
                   .add("inputjson", value)
                   .build();
           Request request = new Request.Builder()
                   .url(SERVER_ADDRESS+"/dopoll")
                   .post(formBody)
                   .build();

           Response response = null;
           try {
               response = client.newCall(request).execute();

               if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

               System.out.println(response.body().string());
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }
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
        String request1 = new Gson().toJson(request);
        rapidPollRestInterface.updatePollState(request1, callback);
    }

}
