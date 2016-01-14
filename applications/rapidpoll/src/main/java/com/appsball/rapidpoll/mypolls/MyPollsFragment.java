package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.PollsListWrapper;
import com.appsball.rapidpoll.searchpolls.SearchPollsFragment;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.google.common.collect.Lists;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

public class MyPollsFragment extends SearchPollsFragment implements PollCloser, PollReopener {

    private MyPollsAdapter resultsAdapter;


    @Override
    protected void setupSortingView(View rootView) {
    }

    @Override
    protected NavigationButton getActiveButton() {
        return NavigationButton.MYPOLLS_BUTTON;
    }

    @Override
    protected PollItemClickListener createPollItemClickListener() {
        return new MyPollsItemClickListener(getRapidPollActivity());
    }

    @Override
    protected void createSearchPollsAdapter(PollItemClickListener pollItemClickListener, View moreLoadView) {
        resultsAdapter = new MyPollsAdapter(Lists.<SearchPollsItemData>newArrayList(), pollItemClickListener, new DateStringFormatter(getResources()), this, this);
        resultsAdapter.setCustomLoadMoreView(moreLoadView);
    }

    @Override
    protected SimpleAdapter getSearchPollsAdapter() {
        return resultsAdapter;
    }


    protected SearchPollsDataState createSearchPollsDataState() {
        return new SearchPollsDataState(OrderType.ASC, OrderKey.STATUS);
    }

    @Override
    protected ListType getListType() {
        return ListType.MYPOLL;
    }

    @Override
    protected PollsListWrapper createPollsListWrapper(View rootView, View moreLoadView) {
        return new MyPollsListWrapper(new LinearLayoutManager(getContext()), rootView, moreLoadView, this, this);
    }

    public void closePoll(String pollId) {
        service.updatePollState(requestCreator.createUpdatePollStateRequest(pollId, PollState.CLOSED), createUpdatePollStateCallback());
    }

    public void reopenPoll(String pollId) {
        service.updatePollState(requestCreator.createUpdatePollStateRequest(pollId, PollState.PUBLISHED), createUpdatePollStateCallback());
    }

    private Callback<ResponseContainer<Object>> createUpdatePollStateCallback() {
        return new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {
                resetAdapterAndGetPolls();
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }
}
