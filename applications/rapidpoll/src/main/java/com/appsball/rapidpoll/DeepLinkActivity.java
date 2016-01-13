package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DeepLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyview);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("mopsz");
    }
}
