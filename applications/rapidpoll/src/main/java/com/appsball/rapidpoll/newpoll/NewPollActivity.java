package com.appsball.rapidpoll.newpoll;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.allpolls.AllPollsAdapter;
import com.appsball.rapidpoll.allpolls.AllPollsDataState;
import com.appsball.rapidpoll.allpolls.AllPollsItemDataTransformer;
import com.appsball.rapidpoll.allpolls.PollsListWrapper;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;

public class NewPollActivity  extends BottomBarNavigationFragment {

    public static final int NEWPOLL_LAYOUT = R.layout.newpoll_layout;

    AllPollsDataState allPollsDataState;

    private View rootView;
    private RapidPollRestService service;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);

        return rootView;
    }
}
