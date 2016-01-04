package com.appsball.rapidpoll.commons.view;

import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.model.NavigationButton;

public class BottomBarNavigationFragment extends RapidPollFragment {

    protected void createNavigationButtonListeners(View rootView, NavigationButton disabledButton) {
        rootView.findViewById(disabledButton.buttonId).setEnabled(false);
        rootView.findViewById(R.id.my_polls_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        rootView.findViewById(R.id.polls_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getRapidPollActivity().toAllPolls();
            }
        });
        rootView.findViewById(R.id.results_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getRapidPollActivity().toResults();
            }
        });
    }
}
