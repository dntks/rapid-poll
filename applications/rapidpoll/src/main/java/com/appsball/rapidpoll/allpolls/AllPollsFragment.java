package com.appsball.rapidpoll.allpolls;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.google.common.collect.Lists;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllPollsFragment extends BottomBarNavigationFragment {

    public static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;

    private View rootView;

    UltimateRecyclerView ultimateRecyclerView;
    AllPollsAdapter allPollsAdapter = null;
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
        final List<AllPollsItemData> stringList = new ArrayList<>();


        allPollsAdapter = new AllPollsAdapter(stringList);


        linearLayoutManager = new LinearLayoutManager(getContext());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        ultimateRecyclerView.enableLoadmore();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        allPollsAdapter.setCustomLoadMoreView(layoutInflater.inflate(R.layout.loadingview, null));


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(i>2){
                            allPollsAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                            ultimateRecyclerView.disableLoadmore();
                        }
                        else {
                            List<AllPollsItemData> elements = Lists.newArrayList();
                            for (int i = 0; i < 5; i++) {
                                AllPollsItemData item = new AllPollsItemData();
                                item.name="elem"+i;
                                elements.add(item);
                            }
                            allPollsAdapter.insertAll(elements, allPollsAdapter.getAdapterItemCount());
//                            allPollsAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                            i++;
                        }
                    }
                }, 1000);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                List<AllPollsItemData> elements = Lists.newArrayList();
                for (int i = 0; i < 5; i++) {
                    AllPollsItemData item = new AllPollsItemData();
                    item.name="elem"+i;
                    elements.add(item);
                }
                allPollsAdapter.insertAll(elements, allPollsAdapter.getAdapterItemCount());
                ultimateRecyclerView.setAdapter(allPollsAdapter);
            }
        }, 1000);

        ultimateRecyclerView.addItemDividerDecoration(getContext());
    }

    int i=0;
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
