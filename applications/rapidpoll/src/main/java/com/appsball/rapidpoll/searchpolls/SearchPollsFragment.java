package com.appsball.rapidpoll.searchpolls;

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
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.appsball.rapidpoll.searchpolls.service.OnPollsReceivedListener;
import com.appsball.rapidpoll.searchpolls.service.SearchPollsCallback;
import com.appsball.rapidpoll.searchpolls.view.SortingView;
import com.appsball.rapidpoll.searchpolls.view.ToolbarTouchListener;
import com.google.common.collect.Lists;

import java.util.List;

public abstract class SearchPollsFragment extends BottomBarNavigationFragment implements GetPollsCaller, SearchPollsCaller, PollsListInitializer, OnPollsReceivedListener {

    private static final int SEARCHPOLLS_LAYOUT = R.layout.allpolls_layout;
    private SearchPollsDataState searchPollsDataState;

    protected RapidPollRestService service;
    private SearchPollsItemDataTransformer searchPollsItemDataTransformer;

    private PollsListWrapper pollsListWrapper;
    private SortingView sortingView;
    private SearchPollsCallback searchPollsCallback;
    protected RequestCreator requestCreator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeButtonVisibility(false);
        service = getRapidPollActivity().getRestService();
        initializeComponents();
        View rootView = inflater.inflate(SEARCHPOLLS_LAYOUT, container, false);

        initializeViews(inflater, savedInstanceState, rootView);
        createNavigationButtonListeners(rootView, getActiveButton());

        callGetPolls();

        return rootView;
    }

    protected abstract NavigationButton getActiveButton();

    private void initializeComponents() {
        searchPollsDataState = new SearchPollsDataState();
        requestCreator = new RequestCreator();
        searchPollsItemDataTransformer = new SearchPollsItemDataTransformer(new DateStringFormatter(getResources()), getResources());
        searchPollsCallback = new SearchPollsCallback(Lists.<OnPollsReceivedListener>newArrayList(this));
        createSearchPollsAdapter(createPollItemClickListener());
    }

    protected abstract PollItemClickListener createPollItemClickListener();

    protected abstract void createSearchPollsAdapter(PollItemClickListener pollItemClickListener);

    private void initializeViews(LayoutInflater inflater, Bundle savedInstanceState, View rootView) {
        View  moreLoadView = inflater.inflate(R.layout.loadingview, null);

        getSearchPollsAdapter().setCustomLoadMoreView(moreLoadView);
        pollsListWrapper = new PollsListWrapper(new LinearLayoutManager(getContext()), rootView, moreLoadView, this, this);
        pollsListWrapper.initializeView(savedInstanceState);
        sortingView = new SortingView(rootView, searchPollsDataState, this);
        sortingView.init();
        getActivity().findViewById(R.id.my_toolbar).setOnTouchListener(new ToolbarTouchListener(sortingView));
    }

    protected abstract SimpleAdapter getSearchPollsAdapter();


    public void callGetPolls() {
        if (checkIsOnlineAndShowSimpleDialog(createGetPollsOnNetOkButtonListener())) {
            service.getPolls(requestCreator.createAllPollsRequest(searchPollsDataState, getListType()), searchPollsCallback);
        }
    }

    protected abstract ListType getListType();

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
        SimpleAdapter adapter = getSearchPollsAdapter();
        searchPollsDataState.actualPage++;
        pollsListWrapper.setupAdapterIfFirstCallIsBeingDone(adapter);
        List<SearchPollsItemData> items = searchPollsItemDataTransformer.transformAll(pollsResponses);
        adapter.insertAll(items, adapter.getAdapterItemCount());
        pollsListWrapper.disableLoadMoreIfNoMoreItems(items);
    }

    @Override
    public void resetAdapterAndGetPolls() {
        resetAdapterAndList();
        pollsListWrapper.setGetPollsMoreListener();
        callGetPolls();
    }

    private void resetAdapterAndList() {
        searchPollsDataState.actualPage = 1;
        getSearchPollsAdapter().removeAllItems();
        pollsListWrapper.resetPollsList();
    }

    public boolean searchForText(String searchPhrase) {
        if (checkIsOnlineAndShowSimpleDialog()) {
            service.searchPoll(requestCreator.createSearchPollRequest(searchPhrase, searchPollsDataState, getListType()),
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
