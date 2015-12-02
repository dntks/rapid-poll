package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.ParseUtil;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createDoPollRequest;

public class DoPollRequestParseTest {
    @Test
    public void testManagePollRequestToJson() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "dopollrequest.json");
        String jsonString = new Gson().toJson(createDoPollRequest());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }
}
