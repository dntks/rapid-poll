package com.appsball.rapidpoll.commons.communication.response;

import com.appsball.rapidpoll.commons.communication.ParseUtil;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;

import static com.appsball.rapidpoll.commons.communication.response.DefaultResponseBuilders.createPollResultResponse;

public class PollResultResponseParseTest {

    @Test
    public void testPollResponseToJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "pollresultresponse.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(createPollResultResponse());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }

    @Test
    public void testPollResponseFromJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "pollresultresponse.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type typeOfT = new TypeToken<ResponseContainer<PollResultResponse>>(){}.getType();
        ResponseContainer<PollResultResponse>  actualParsedPollsResponse= gson.fromJson(fileAsString, typeOfT);
        ResponseContainer<PollResultResponse> expectedPollsResponse = createPollResultResponse();
        Assert.assertEquals(expectedPollsResponse, actualParsedPollsResponse);
    }
}
