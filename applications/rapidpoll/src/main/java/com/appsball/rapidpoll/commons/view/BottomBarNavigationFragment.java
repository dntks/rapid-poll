package com.appsball.rapidpoll.commons.view;

import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.model.NavigationButton;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomBarNavigationFragment extends RapidPollFragment {

    protected void createNavigationButtonListeners(View rootView, NavigationButton disabledButton) {
        rootView.findViewById(disabledButton.buttonId).setEnabled(false);
        ButterKnife.bind(this, rootView);
    }

    @OnClick(R.id.my_polls_button)
    public void toMyPolls(){
        getFragmentSwitcher().toMyPolls();
    }

    @OnClick(R.id.polls_button)
    public void toAllPolls(){
        getFragmentSwitcher().toAllPolls();
    }

    @OnClick(R.id.results_button)
    public void toResults(){
        getFragmentSwitcher().toResults();
    }
}
