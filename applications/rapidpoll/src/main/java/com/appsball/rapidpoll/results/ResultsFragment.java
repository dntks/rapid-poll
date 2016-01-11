package com.appsball.rapidpoll.results;

import android.view.View;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.SearchPollsFragment;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.google.common.collect.Lists;

public class ResultsFragment extends SearchPollsFragment {

    private ResultsAdapter resultsAdapter;

    @Override
    protected NavigationButton getActiveButton() {
        return NavigationButton.RESULTS_BUTTON;
    }

    @Override
    protected PollItemClickListener createPollItemClickListener() {
        return new ResultsItemClickListener(getRapidPollActivity(), requestCreator, service);
    }

    @Override
    protected void createSearchPollsAdapter(PollItemClickListener pollItemClickListener, View moreLoadView) {
        resultsAdapter = new ResultsAdapter(Lists.<SearchPollsItemData>newArrayList(), pollItemClickListener, new DateStringFormatter(getResources()));
        resultsAdapter.setCustomLoadMoreView(moreLoadView);
    }

    @Override
    protected SimpleAdapter getSearchPollsAdapter() {
        return resultsAdapter;
    }

    @Override
    protected ListType getListType() {
        return ListType.CLOSED;
    }
}
