package com.appsball.rapidpoll.commons.communication;

import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.request.RegisterRequest;
import com.appsball.rapidpoll.commons.communication.response.PollDetailsResponseModel;
import com.appsball.rapidpoll.commons.communication.response.PollResponseModel;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.http.Body;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.Mock;
import com.orhanobut.wasp.http.POST;
import com.orhanobut.wasp.http.Path;

import java.util.List;

public interface RapidPollRestService {

    @Mock
    @POST("/register")
    void registerUser(@Body RegisterRequest request, Callback<ResponseContainer<RegisterResponse>> callback);

    @Mock
    @POST("/managepoll")
    void managePoll(@Body ManagePollRequest request, Callback<ResponseContainer<Object>> callback);

    @Mock
    @GET("/polls/{userid}/{orderkey}/{ordertype}/{pagesize}/{page}")
    void getPolls(@Path("userid") String userid,
                  @Path("orderkey") String orderkey,
                  @Path("ordertype") String ordertype,
                  @Path("pagesize") String pagesize,
                  @Path("page") String page,
                  Callback<ResponseContainer<List<PollResponseModel>>> callback);

    @Mock
    @GET("/polldetails/{userid}/{pollid}")
    void pollDetails(@Path("userid") String userid,
                     @Path("pollid") String orderkey,
                     Callback<ResponseContainer<PollDetailsResponseModel>> callback);

}
