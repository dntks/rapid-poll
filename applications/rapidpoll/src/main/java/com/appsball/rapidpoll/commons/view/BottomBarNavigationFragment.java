package com.appsball.rapidpoll.commons.view;

import android.view.View;

import com.appsball.rapidpoll.R;

public class BottomBarNavigationFragment extends RapidPollFragment {

    protected void createButtonListeners(View rootView) {
        rootView.findViewById(R.id.my_polls_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        rootView.findViewById(R.id.polls_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        rootView.findViewById(R.id.results_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
    }
}
