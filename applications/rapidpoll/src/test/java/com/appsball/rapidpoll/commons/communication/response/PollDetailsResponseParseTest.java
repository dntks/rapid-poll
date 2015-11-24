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

import static com.appsball.rapidpoll.commons.communication.response.DefaultResponseBuilders.createPollDetailsResponse;

public class PollDetailsResponseParseTest {

    @Test
    public void testPollResponseToJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "polldetails.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(createPollDetailsResponse());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }

    @Test
    public void testPollResponseFromJsonParse() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "polldetails.json");
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type typeOfT = new TypeToken<ResponseContainer<PollDetailsResponseModel>>(){}.getType();
        ResponseContainer<PollDetailsResponseModel>  actualParsedPollsResponse= gson.fromJson(fileAsString, typeOfT);
        ResponseContainer<PollDetailsResponseModel> expectedPollsResponse = createPollDetailsResponse();
        Assert.assertEquals(expectedPollsResponse, actualParsedPollsResponse);
    }
}
