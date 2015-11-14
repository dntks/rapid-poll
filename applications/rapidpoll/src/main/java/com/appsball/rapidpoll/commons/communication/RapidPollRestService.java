package com.appsball.rapidpoll.commons.communication;

import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.http.Body;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.Mock;
import com.orhanobut.wasp.http.POST;
import com.orhanobut.wasp.http.Path;

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
                  Callback<ResponseContainer<RegisterResponse>> callback);

}
