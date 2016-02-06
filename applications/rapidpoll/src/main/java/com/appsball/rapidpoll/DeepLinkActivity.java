package com.appsball.rapidpoll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appsball.rapidpoll.commons.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.FRAGMENT_NAME;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_TITLE;

public class DeepLinkActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyview);

        Uri data = getIntent().getData();
        String urlString = data.toString();
        String urlStringWithoutHttp = urlString.replaceFirst("http://", "");

        List<String> urlParts = Utils.ON_SLASH_SPLITTER.splitToList(urlStringWithoutHttp);

        String serverAddress = urlParts.get(0);
        String screenName = urlParts.get(1);
        Intent rapidPollIntent = new Intent(this, RapidPollActivity.class);

        if ("pollresult".equals(screenName)) {
            String pollTitle = "";
            try {
                pollTitle = URLDecoder.decode(urlParts.get(2), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String pollId = urlParts.get(3);
            String pollCode = urlParts.get(4);
            rapidPollIntent.putExtra(FRAGMENT_NAME, screenName);
            rapidPollIntent.putExtra(POLL_TITLE, pollTitle);
            rapidPollIntent.putExtra(POLL_ID, pollId);
            rapidPollIntent.putExtra(POLL_CODE, pollCode);
        }
        startActivity(rapidPollIntent);
    }
}
