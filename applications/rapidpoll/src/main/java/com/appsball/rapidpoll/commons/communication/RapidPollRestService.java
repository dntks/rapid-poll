package com.appsball.rapidpoll.commons.communication;

import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.http.Body;
import com.orhanobut.wasp.http.GET;
import com.orhanobut.wasp.http.POST;
import com.orhanobut.wasp.http.Path;

public interface RapidPollRestService {

    @POST("/register")
    void registerUser(@Body RegisterRequest request, Callback<ResponseContainer<RegisterResponse>> callback);

    @GET("/polls/{userid}/{orderkey}/{ordertype}/{pagesize}/{page}")
    void getPolls(@Path("userid") String userid,
                  @Path("orderkey") String orderkey,
                  @Path("ordertype") String ordertype,
                  @Path("pagesize") String pagesize,
                  @Path("page") String page,
                  Callback<ResponseContainer<RegisterResponse>> callback);
}
