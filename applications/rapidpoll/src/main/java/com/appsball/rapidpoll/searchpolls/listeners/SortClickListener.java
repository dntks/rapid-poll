package com.appsball.rapidpoll.searchpolls.listeners;

import android.view.View;

import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.searchpolls.PollsListInitializer;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.appsball.rapidpoll.searchpolls.model.SortType;
import com.appsball.rapidpoll.searchpolls.view.SortingView;
import com.orhanobut.hawk.Hawk;

public class SortClickListener implements View.OnClickListener {

    private final SortType sortType;
    private final SortingView sortingView;
    private final SearchPollsDataState searchPollsDataState;
    private final PollsListInitializer pollsListInitializer;

    public SortClickListener(SortType sortType, SortingView sortingView, SearchPollsDataState searchPollsDataState, PollsListInitializer pollsListInitializer) {
        this.sortType = sortType;
        this.sortingView = sortingView;
        this.searchPollsDataState = searchPollsDataState;
        this.pollsListInitializer = pollsListInitializer;
    }

    @Override
    public void onClick(View v) {
        searchPollsDataState.setSort(sortType);
        Hawk.put(Constants.LAST_SORTING, sortType.name());
        sortingView.enableOtherButtons(v);
        pollsListInitializer.resetAdapterAndGetPolls();
    }
}
