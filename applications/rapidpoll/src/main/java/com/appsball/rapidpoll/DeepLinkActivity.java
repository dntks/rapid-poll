package com.appsball.rapidpoll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appsball.rapidpoll.commons.utils.Utils;

import java.util.List;

import static com.appsball.rapidpoll.commons.utils.Constants.FRAGMENT_NAME;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;

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

        if (ScreenFragment.POLL_RESULT.apiName.equals(screenName) || ScreenFragment.FILL_POLL.apiName.equals(screenName)) {
            String pollId = urlParts.get(2);
            String pollCode = urlParts.get(3);
            rapidPollIntent.putExtra(FRAGMENT_NAME, screenName);
            rapidPollIntent.putExtra(POLL_ID, pollId);
            rapidPollIntent.putExtra(POLL_CODE, pollCode);
        }
        startActivity(rapidPollIntent);
        finish();
    }
}
