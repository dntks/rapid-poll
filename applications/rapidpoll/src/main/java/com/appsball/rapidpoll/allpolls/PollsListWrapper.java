package com.appsball.rapidpoll.allpolls;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.allpolls.adapter.AllPollsAdapter;
import com.appsball.rapidpoll.allpolls.model.AllPollsDataState;
import com.appsball.rapidpoll.allpolls.model.AllPollsItemData;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

public class PollsListWrapper {
    private UltimateRecyclerView ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private View rootView;
    private View centeredLoadingView;
    private View moreLoadView;
    private GetPollsCaller getPollsCaller;
    private SearchPollsCaller searchPollsCaller;

    public PollsListWrapper(LinearLayoutManager linearLayoutManager,
                            View rootView,
                            View moreLoadView,
                            GetPollsCaller getPollsCaller,
                            SearchPollsCaller searchPollsCaller) {
        this.linearLayoutManager = linearLayoutManager;
        this.rootView = rootView;
        this.getPollsCaller = getPollsCaller;
        this.searchPollsCaller = searchPollsCaller;
        this.moreLoadView = moreLoadView;
    }

    public void initializeView(Bundle savedInstanceState) {
        centeredLoadingView = rootView.findViewById(R.id.centered_loading_view);

        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.paging_list_view);
        ultimateRecyclerView.setHasFixedSize(false);

        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();
        setGetPollsMoreListener();
    }

    public void setGetPollsMoreListener() {
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                getPollsCaller.callGetPolls();
            }
        });
    }

    public void setSearchPollsMoreListener(final String searchText) {
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                searchPollsCaller.searchForText(searchText);
            }
        });
    }

    public void disableLoadMoreIfNoMoreItems(List<AllPollsItemData> items) {
        if (items.size() < AllPollsDataState.numberOfRequestedPolls) {
            ultimateRecyclerView.disableLoadmore();
        }
    }

    public void setupAdapterIfFirstCallIsBeingDone(AllPollsAdapter allPollsAdapter) {
        if (centeredLoadingView.getVisibility() == View.VISIBLE) {
            centeredLoadingView.setVisibility(View.GONE);
            ultimateRecyclerView.setAdapter(allPollsAdapter);
            ultimateRecyclerView.reenableLoadmore(moreLoadView);
        }
    }

    public void resetPollsList() {
        ultimateRecyclerView.setAdapter(null);
        centeredLoadingView.setVisibility(View.VISIBLE);
    }
}
