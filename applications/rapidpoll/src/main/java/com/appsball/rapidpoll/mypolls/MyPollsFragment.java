package com.appsball.rapidpoll.mypolls;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.allpolls.SimpleAdapter;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public class MyPollsFragment extends BottomBarNavigationFragment {

    public static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;

    private View rootView;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(ALLPOLLS_LAYOUT, container, false);
        createButtonListeners(rootView);
        initializeListView(savedInstanceState);
        service = RapidPollRestService.createRapidPollRestService(getContext());
        return rootView;
    }

    protected void initializeListView(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.paging_list_view);
        ultimateRecyclerView.setHasFixedSize(false);


        linearLayoutManager = new LinearLayoutManager(getContext());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);
        ultimateRecyclerView.enableLoadmore();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        simpleRecyclerViewAdapter.setCustomLoadMoreView(layoutInflater.inflate(R.layout.loadingview, null));


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
//                            simpleRecyclerViewAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                        ultimateRecyclerView.disableLoadmore();
                    }
                }, 1000);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
//                simpleRecyclerViewAdapter = new SimpleAdapter(Lists.<String>newArrayList());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                simpleRecyclerViewAdapter.insert("uj", simpleRecyclerViewAdapter.getAdapterItemCount());
                ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
            }
        }, 10000);
    }

    private RapidPollRestService service;

    private PollsRequest createPollsRequest() {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withUserId("");
        builder.withListType(ListType.ALL);
        builder.withOrderKey(OrderKey.TITLE);
        builder.withOrderType(OrderType.DESC);
        builder.withPage("1");
        builder.withPageSize("25");
        return builder.build();
    }

}
