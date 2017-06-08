package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollsListWrapper;
import com.appsball.rapidpoll.searchpolls.SearchPollsFragment;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.listeners.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.appsball.rapidpoll.searchpolls.model.SortType;
import com.google.common.collect.Lists;
import com.orhanobut.hawk.Hawk;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPollsFragment extends SearchPollsFragment implements PollCloser, PollReopener {

    private static final int MY_POLLS_LAYOUT = R.layout.mypolls_layout;
    private MyPollsAdapter resultsAdapter;

    @Override
    protected void additionalPageSetup(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @OnClick(R.id.create_new_poll_layout)
    public void toManagePoll(){
        getFragmentSwitcher().toManagePoll();
    }

    @Override
    protected void setupSortingView(View rootView) {
    }

    @Override
    protected NavigationButton getActiveButton() {
        return NavigationButton.MYPOLLS_BUTTON;
    }

    @Override
    protected PollItemClickListener createPollItemClickListener() {
        return new MyPollsItemClickListener(getFragmentSwitcher());
    }

    protected int getSearchpollsLayout() {
        return MY_POLLS_LAYOUT;
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
        return new SearchPollsDataState(SortType.STATUS);
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

    private ResponseCallback createUpdatePollStateCallback() {
        return new ResponseCallback() {
            @Override
            public void onSuccess() {
                resetAdapterAndGetPolls();
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onError(String errorMessage) {

            }
        };
    }
}
