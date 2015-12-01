package com.appsball.rapidpoll;

import android.test.InstrumentationTestCase;

import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders.createManagePollRequest;

public class ManagePollRequestParseTest extends InstrumentationTestCase {
    @Test
    public void testManagePollRequestToJson() throws IOException {

        String testFile = "managepollrequest.json";
        String file = "assets/" + testFile;

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        InputStream in2 = getInstrumentation().getTargetContext().getResources().getAssets().open(testFile);
        String fileAsString = CharStreams.toString(new InputStreamReader(in2, "UTF-8"));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ManagePollRequest managePollRequest = createManagePollRequest();
        String jsonString = gson.toJson(managePollRequest);
        JsonParser parser = new JsonParser();
        Assert.assertEquals(parser.parse(fileAsString), parser.parse(jsonString));

    }
}
