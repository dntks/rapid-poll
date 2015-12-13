package com.appsball.rapidpoll.commons.communication.service;

import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequestContainer;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.http.Body;
import com.orhanobut.wasp.http.Field;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.Mock;
import com.orhanobut.wasp.http.POST;
import com.orhanobut.wasp.http.Path;

import java.util.List;

public interface RapidPollRestInterface {

    @Mock
    @POST("/register")
    void registerUser(@Body RegisterRequest request, Callback<ResponseContainer<RegisterResponse>> callback);

    @Mock
    @POST("/managepoll")
    void managePoll(@Body ManagePollRequest request, Callback<ResponseContainer<Object>> callback);

    @Mock
    @GET("/polls/{userid}/{listtype}/{orderkey}/{ordertype}/{pagesize}/{page}")
    void getPolls(@Path("userid") String userid,
                  @Path("listtype") String listtype,
                  @Path("orderkey") String orderkey,
                  @Path("ordertype") String ordertype,
                  @Path("pagesize") String pagesize,
                  @Path("page") String page,
                  Callback<ResponseContainer<List<PollsResponse>>> callback);

    @Mock
    @GET("/polldetails/{userid}/{pollid}")
    void pollDetails(@Path("userid") String userid,
                     @Path("pollid") String pollId,
                     Callback<ResponseContainer<PollDetailsResponse>> callback);

    @Mock
    @POST("/dopoll")
    void doPoll(@Body DoPollRequestContainer inputjson, Callback<String> callback);

    @Mock
    @GET("/pollresult/{userid}/{pollid}")
    void pollResult(@Path("userid") String userid,
                    @Path("pollid") String pollId,
                    Callback<ResponseContainer<PollResultResponse>> callback);

    @Mock
    @GET("/search/{userid}/{listtype}/{searchitem}/{orderkey}/{ordertype}/{pagesize}/{page}")
    void searchPoll(@Path("userid") String userid,
                    @Path("listtype") String listtype,
                    @Path("searchitem") String searchitem,
                    @Path("orderkey") String orderkey,
                    @Path("ordertype") String ordertype,
                    @Path("pagesize") String pagesize,
                    @Path("page") String page,
                    Callback<ResponseContainer<List<PollsResponse>>> callback);

    @Mock
    @POST("/updatepollstate")
    void updatePollState(@Field("inputjson")  String request, Callback<ResponseContainer<Object>> callback);

}
