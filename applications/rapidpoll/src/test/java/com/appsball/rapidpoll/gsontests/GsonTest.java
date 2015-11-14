package com.appsball.rapidpoll.gsontests;

import com.appsball.rapidpoll.commons.communication.ManagePollRequest;
import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Test;

import static com.appsball.rapidpoll.commons.communication.DefaultBuilders.createManagePollRequest;

public class GsonTest {
    @Test
    public void testManagePollRequestToJson(){
        ManagePollRequest managePollRequest = createManagePollRequest();
        String jsonString = new Gson().toJson(managePollRequest);

        Assert.assertEquals("{\"user_id\":\"11E58A684CDE15E49E7502000029BDFD\",\"action\":\"CREATE\",\"poll\":{\"name\":\"WestEndChristmas\",\"public\":\"1\",\"anonymous\":\"0\",\"allow_comment\":\"1\",\"allow_uncomplete_result\":\"1\",\"questions\":[{\"name\":\"What is your favourite shop in WestEnd?\",\"multichoice\":\"1\",\"alternatives\":[{\"name\":\"Media Markt\"},{\"name\":\"Nike\"},{\"name\":\"Budmil\"}]},{\"name\":\"How much do you usually spend at West End?\",\"multichoice\":\"0\",\"alternatives\":[{\"name\":\"1-1000HUF\"},{\"name\":\"1000-5000HUF\"},{\"name\":\"5000-10000\"},{\"name\":\"10000-\"}]}]}}",jsonString);

    }
}
