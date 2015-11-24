package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.ParseUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createManagePollRequest;

public class ManagePollRequestParseTest {
    @Test
    public void testManagePollRequestToJson() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "managepollrequest.json");
        String jsonString = new Gson().toJson(createManagePollRequest());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }

}
