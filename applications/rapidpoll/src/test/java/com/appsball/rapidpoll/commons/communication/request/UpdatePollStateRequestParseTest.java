package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.ParseUtil;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createUpdatePollStateRequest;

public class UpdatePollStateRequestParseTest {
    @Test
    public void testManagePollRequestToJson() throws IOException {

        String fileAsString = ParseUtil.getFileContentAsString(this, "updatepollstaterequest.json");
        String jsonString = new Gson().toJson(createUpdatePollStateRequest());

        ParseUtil.assertJsonsEqual(fileAsString, jsonString);
    }
}
