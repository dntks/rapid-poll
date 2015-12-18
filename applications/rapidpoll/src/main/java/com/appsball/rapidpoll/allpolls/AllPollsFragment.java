package com.appsball.rapidpoll.allpolls;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.DefaultRequestBuilders;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.google.common.collect.Lists;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

public class AllPollsFragment extends BottomBarNavigationFragment {

    public static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;
    public static final int NUMBER_OF_POLLS_REQUESTED = 25;

    private View rootView;

    private RapidPollRestService service;
    private UltimateRecyclerView ultimateRecyclerView;
    private AllPollsAdapter allPollsAdapter = null;
    private LinearLayoutManager linearLayoutManager;
    private AllPollsItemDataTransformer allPollsItemDataTransformer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(ALLPOLLS_LAYOUT, container, false);
        service = getRapidPollActivity().getRestService();
        DateStringFormatter dateStringFormatter = new DateStringFormatter(getResources());
        allPollsItemDataTransformer = new AllPollsItemDataTransformer(dateStringFormatter, getResources());
        linearLayoutManager = new LinearLayoutManager(getContext());
        createButtonListeners(rootView);
        initializeListView(savedInstanceState);
        return rootView;
    }

    protected void initializeListView(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.paging_list_view);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();

        allPollsAdapter = new AllPollsAdapter(Lists.<AllPollsItemData>newArrayList());

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        allPollsAdapter.setCustomLoadMoreView(layoutInflater.inflate(R.layout.loadingview, null));


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                callGetPolls();
            }
        });

        callGetPolls(true);
        ultimateRecyclerView.addItemDividerDecoration(getContext());
    }

    private void callGetPolls() {
        callGetPolls(false);
    }

    private void callGetPolls(final boolean isInitializing) {
        service.getPolls(DefaultRequestBuilders.createAllPollsRequest(),
                         createGetPollsCallback(isInitializing));
    }

    private Callback<ResponseContainer<List<PollsResponse>>> createGetPollsCallback(final boolean isInitializing) {
        return new Callback<ResponseContainer<List<PollsResponse>>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                if (isInitializing) {
                    rootView.findViewById(R.id.centered_loading_view).setVisibility(View.GONE);
                    ultimateRecyclerView.setAdapter(allPollsAdapter);
                }
                List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(listResponseContainer.result);
                allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
                if(items.size()< NUMBER_OF_POLLS_REQUESTED){
                    ultimateRecyclerView.disableLoadmore();
                    allPollsAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }


}
