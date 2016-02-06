package com.appsball.rapidpoll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DeepLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyview);

        TextView textView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        textView.setText("mopsz " + data.toString());
    }
}
