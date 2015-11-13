package com.appsball.rapidpoll.commons.communication;

import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;

public class RapidPollRestServiceTest {
    @Test
    public void testMockedRegister() {
        System.out.println("testing test");
        ResponseContainer<RegisterResponse> response = new ResponseContainer<>();
        response.messages = new ArrayList<>();
        response.messages.add("a");
        response.messages.add("b");
        response.status="status";
        response.result = new RegisterResponse();
        response.result.user_id="id";
        String test = new Gson().toJson(response);

        String expectedResult= "{\"result\":{\"user_id\":\"id\"},\"status\":\"status\",\"messages\":[\"a\",\"b\"]}";
        Assert.assertEquals(expectedResult, test);
    }
}
