package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.PollsListWrapper;
import com.appsball.rapidpoll.searchpolls.SearchPollsFragment;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.google.common.collect.Lists;

public class MyPollsFragment extends SearchPollsFragment {

    private MyPollsAdapter resultsAdapter;

    @Override
    protected NavigationButton getActiveButton() {
        return NavigationButton.MYPOLLS_BUTTON;
    }

    @Override
    protected PollItemClickListener createPollItemClickListener() {
        return new MyPollsItemClickListener(getRapidPollActivity(), requestCreator, service);
    }

    @Override
    protected void createSearchPollsAdapter(PollItemClickListener pollItemClickListener) {
        resultsAdapter = new MyPollsAdapter(Lists.<SearchPollsItemData>newArrayList(), pollItemClickListener, new DateStringFormatter(getResources()));
    }

    @Override
    protected SimpleAdapter getSearchPollsAdapter() {
        return resultsAdapter;
    }

    @Override
    protected ListType getListType() {
        return ListType.MYPOLL;
    }

    @Override
    protected PollsListWrapper createPollsListWrapper(View rootView, View moreLoadView) {
        return new MyPollsListWrapper(new LinearLayoutManager(getContext()), rootView, moreLoadView, this, this);
    }

}
