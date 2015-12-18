package com.appsball.rapidpoll.commons.view;

import android.support.v4.app.Fragment;

import com.appsball.rapidpoll.RapidPollActivity;

public class RapidPollFragment extends Fragment {

    public RapidPollActivity getRapidPollActivity() {
        return (RapidPollActivity) getActivity();
    }
}
