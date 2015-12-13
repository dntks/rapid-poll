package com.appsball.rapidpoll.commons.communication.response;

import com.appsball.rapidpoll.commons.communication.ParseUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static com.appsball.rapidpoll.commons.communication.response.DefaultResponseBuilders.createPollsResponse;

public class PollsResponseParseTest {
    @Test
    public void testPollResponseToJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "getpollsresponse.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(createPollsResponse());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }
    @Test
    public void testPollResponseFromJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "getpollsresponse.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type typeOfT = new TypeToken<ResponseContainer<List<PollsResponse>>>(){}.getType();
        ResponseContainer<List<PollsResponse>>  actualParsedPollsResponse= gson.fromJson(fileAsString, typeOfT);
        ResponseContainer<List<PollsResponse>> expectedPollsResponse = createPollsResponse();
        Assert.assertEquals(expectedPollsResponse, actualParsedPollsResponse);
    }

}
