package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appsball.rapidpoll.searchpolls.GetPollsCaller;
import com.appsball.rapidpoll.searchpolls.PollsListWrapper;
import com.appsball.rapidpoll.searchpolls.SearchPollsCaller;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public class MyPollsListWrapper extends PollsListWrapper {
    public MyPollsListWrapper(LinearLayoutManager linearLayoutManager, View rootView, View moreLoadView, GetPollsCaller getPollsCaller, SearchPollsCaller searchPollsCaller) {
        super(linearLayoutManager, rootView, moreLoadView, getPollsCaller, searchPollsCaller);
    }


    public void setupAdapterIfFirstCallIsBeingDone(SimpleAdapter allPollsAdapter) {
        if (centeredLoadingView.getVisibility() == View.VISIBLE) {
            centeredLoadingView.setVisibility(View.GONE);
            ultimateRecyclerView.setAdapter(allPollsAdapter);
            ultimateRecyclerView.reenableLoadmore(moreLoadView);
            StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(allPollsAdapter);
            ultimateRecyclerView.addItemDecoration(headersDecor);
        }
    }
}
