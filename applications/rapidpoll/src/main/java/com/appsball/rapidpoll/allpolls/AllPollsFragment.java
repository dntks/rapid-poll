package com.appsball.rapidpoll.allpolls;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
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
    public static final int NUMBER_OF_REQUESTED_POLLS = 10;
    public static final OrderType CHOSEN_ORDER_TYPE = OrderType.DESC;
    public static final OrderKey CHOSEN_ORDER_KEY = OrderKey.DATE;

    private View rootView;

    private RapidPollRestService service;
    private UltimateRecyclerView ultimateRecyclerView;
    private AllPollsAdapter allPollsAdapter = null;
    private LinearLayoutManager linearLayoutManager;
    private AllPollsItemDataTransformer allPollsItemDataTransformer;

    private boolean isNetDialogShownForGetPolls = false;
    private int actualPage = 1;
    private View centeredLoadingView;
    private View moreLoadView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(ALLPOLLS_LAYOUT, container, false);
        moreLoadView = inflater.inflate(R.layout.loadingview, null);
        service = getRapidPollActivity().getRestService();
        DateStringFormatter dateStringFormatter = new DateStringFormatter(getResources());
        allPollsItemDataTransformer = new AllPollsItemDataTransformer(dateStringFormatter, getResources());
        linearLayoutManager = new LinearLayoutManager(getContext());
        centeredLoadingView = rootView.findViewById(R.id.centered_loading_view);
        createButtonListeners(rootView);
        initializeListView(savedInstanceState);
        initializeAllPollsAdapter();
        callGetPolls();
        return rootView;
    }

    protected void initializeListView(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.paging_list_view);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.addItemDividerDecoration(getContext());
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                callGetPolls();
            }
        });

    }

    private void initializeAllPollsAdapter() {
        allPollsAdapter = new AllPollsAdapter(Lists.<AllPollsItemData>newArrayList());
        allPollsAdapter.setCustomLoadMoreView(moreLoadView);
    }

    private void callGetPolls() {
        if (checkIsOnlineAndShowSimpleDialog(getGetPollsOnNetOkButtonListener())) {
            service.getPolls(createAllPollsRequest(),
                             createGetPollsCallback());
        } else {
            isNetDialogShownForGetPolls = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetDialogShownForGetPolls) {
            callGetPolls();
        }
    }

    private PollsRequest createAllPollsRequest() {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withPage(String.valueOf(actualPage));
        builder.withOrderType(CHOSEN_ORDER_TYPE);
        builder.withOrderKey(CHOSEN_ORDER_KEY);
        builder.withListType(ListType.ALL);
        builder.withUserId(getUserId());
        builder.withPageSize(String.valueOf(NUMBER_OF_REQUESTED_POLLS));
        return builder.build();
    }


    private DialogInterface.OnClickListener getGetPollsOnNetOkButtonListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callGetPolls();
            }
        };
    }

    private Callback<ResponseContainer<List<PollsResponse>>> createGetPollsCallback() {
        return new Callback<ResponseContainer<List<PollsResponse>>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                actualPage++;
                setupAdapterIfFirstCallIsBeingDone();
                List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(listResponseContainer.result);
                allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
                disableLoadMoreIfNoMoreItems(items);
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }

    private void disableLoadMoreIfNoMoreItems(List<AllPollsItemData> items) {
        if (items.size() < NUMBER_OF_REQUESTED_POLLS) {
            ultimateRecyclerView.disableLoadmore();
        }
    }

    private void setupAdapterIfFirstCallIsBeingDone() {
        if (centeredLoadingView.getVisibility() == View.VISIBLE) {
            centeredLoadingView.setVisibility(View.GONE);
            ultimateRecyclerView.setAdapter(allPollsAdapter);
            ultimateRecyclerView.reenableLoadmore(moreLoadView);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_polls_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resetPollsPagerAdapter();
                return searchForEnteredText(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                resetPollsPagerAdapter();
                callGetPolls();
                return false;
            }
        });
    }

    private void resetPollsPagerAdapter() {
        actualPage = 1;
        allPollsAdapter.removeAllItems();
        ultimateRecyclerView.setAdapter(null);

//        allPollsAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
        centeredLoadingView.setVisibility(View.VISIBLE);
    }

    private boolean searchForEnteredText(String searchPhrase) {
        if (checkIsOnlineAndShowSimpleDialog()) {
            service.searchPoll(createSearchPollRequest(searchPhrase),
                               createSearchPollsCallback());
            return true;
        }
        return false;
    }

    private Callback<ResponseContainer<List<PollsResponse>>> createSearchPollsCallback() {
        return new Callback<ResponseContainer<List<PollsResponse>>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                actualPage++;
                setupAdapterIfFirstCallIsBeingDone();
                List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(listResponseContainer.result);
                allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
                disableLoadMoreIfNoMoreItems(items);
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }

    private SearchPollRequest createSearchPollRequest(String searchPhrase) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(actualPage));
        builder.withOrderType(CHOSEN_ORDER_TYPE);
        builder.withOrderKey(CHOSEN_ORDER_KEY);
        builder.withListType(ListType.ALL);
        builder.withUserId(getUserId());
        builder.withPageSize(String.valueOf(NUMBER_OF_REQUESTED_POLLS));
        builder.withSearchItem(searchPhrase);
        return builder.build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                item.expandActionView();
                return true;
            case R.id.add_poll:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
