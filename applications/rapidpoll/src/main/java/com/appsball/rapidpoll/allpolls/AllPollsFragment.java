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
import com.appsball.rapidpoll.allpolls.adapter.AllPollsAdapter;
import com.appsball.rapidpoll.allpolls.model.AllPollsDataState;
import com.appsball.rapidpoll.allpolls.model.AllPollsItemData;
import com.appsball.rapidpoll.allpolls.service.OnPollsReceivedListener;
import com.appsball.rapidpoll.allpolls.service.SearchPollsCallback;
import com.appsball.rapidpoll.allpolls.view.SortingView;
import com.appsball.rapidpoll.allpolls.view.ToolbarTouchListener;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.google.common.collect.Lists;

import java.util.List;

public class AllPollsFragment extends BottomBarNavigationFragment implements GetPollsCaller, SearchPollsCaller, PollsListInitializer, OnPollsReceivedListener {

    private static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;

    private AllPollsDataState allPollsDataState;

    private RapidPollRestService service;
    private AllPollsAdapter allPollsAdapter;
    private AllPollsItemDataTransformer allPollsItemDataTransformer;

    private PollsListWrapper pollsListWrapper;
    private View moreLoadView;
    private SortingView sortingView;
    private SearchPollsCallback searchPollsCallback;
    private RequestCreator requestCreator;
    private PollItemClickListener pollItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeButtonVisibility(false);
        service = getRapidPollActivity().getRestService();
        initializeComponents();
        View rootView = inflater.inflate(ALLPOLLS_LAYOUT, container, false);

        initializeViews(inflater, savedInstanceState, rootView);
        createNavigationButtonListeners(rootView, NavigationButton.POLLS_BUTTON);

        callGetPolls();

        return rootView;
    }

    private void initializeComponents() {
        allPollsDataState = new AllPollsDataState();
        requestCreator = new RequestCreator();
        allPollsItemDataTransformer = new AllPollsItemDataTransformer(new DateStringFormatter(getResources()), getResources());
        searchPollsCallback = new SearchPollsCallback(Lists.<OnPollsReceivedListener>newArrayList(this));
        getActivity().findViewById(R.id.my_toolbar).setOnTouchListener(new ToolbarTouchListener(sortingView));
        pollItemClickListener = new DefaultPollItemClickListener(getRapidPollActivity(), requestCreator, service);
    }

    private void initializeViews(LayoutInflater inflater, Bundle savedInstanceState, View rootView) {
        moreLoadView = inflater.inflate(R.layout.loadingview, null);
        allPollsAdapter = new AllPollsAdapter(Lists.<AllPollsItemData>newArrayList(), pollItemClickListener);
        allPollsAdapter.setCustomLoadMoreView(moreLoadView);
        pollsListWrapper = new PollsListWrapper(new LinearLayoutManager(getContext()), rootView, moreLoadView, this, this);
        pollsListWrapper.initializeView(savedInstanceState);
        sortingView = new SortingView(rootView, allPollsDataState, this);
        sortingView.init();
    }


    public void callGetPolls() {
        if (checkIsOnlineAndShowSimpleDialog(createGetPollsOnNetOkButtonListener())) {
            service.getPolls(requestCreator.createAllPollsRequest(allPollsDataState), searchPollsCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetDialogShownForGetPolls) {
            callGetPolls();
        }
    }

    private DialogInterface.OnClickListener createGetPollsOnNetOkButtonListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callGetPolls();
            }
        };
    }

    public void onPollsReceived(List<PollsResponse> pollsResponses) {
        allPollsDataState.actualPage++;
        pollsListWrapper.setupAdapterIfFirstCallIsBeingDone(allPollsAdapter);
        List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(pollsResponses);
        allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
        pollsListWrapper.disableLoadMoreIfNoMoreItems(items);
    }

    @Override
    public void resetAdapterAndGetPolls() {
        resetAdapterAndList();
        pollsListWrapper.setGetPollsMoreListener();
        callGetPolls();
    }

    private void resetAdapterAndList() {
        allPollsDataState.actualPage = 1;
        allPollsAdapter.removeAllItems();
        pollsListWrapper.resetPollsList();
    }

    public boolean searchForText(String searchPhrase) {
        if (checkIsOnlineAndShowSimpleDialog()) {
            service.searchPoll(requestCreator.createSearchPollRequest(searchPhrase, allPollsDataState),
                    searchPollsCallback);
            return true;
        }
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_polls_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resetAdapterAndList();
                pollsListWrapper.setSearchPollsMoreListener(query);
                return searchForText(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                resetAdapterAndGetPolls();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingView.hideSortByLayout();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                item.expandActionView();
                return true;
            case R.id.add_poll:
                getRapidPollActivity().toCreatePoll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
