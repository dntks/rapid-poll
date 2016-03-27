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
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.NavigationButton;
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
    private View moreLoadView;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(getSearchpollsLayout(), container, false);
        getRapidPollActivity().hideBackButton();
        service = getRapidPollActivity().getRestService();
        initializeComponents();

        initializeViews(inflater, savedInstanceState, rootView);
        createNavigationButtonListeners(rootView, getActiveButton());
        additionalPageSetup(rootView);
        callGetPolls();

        return rootView;
    }

    protected void additionalPageSetup(View rootView) {

    }

    protected int getSearchpollsLayout() {
        return SEARCHPOLLS_LAYOUT;
    }

    protected abstract NavigationButton getActiveButton();

    private void initializeComponents() {
        searchPollsDataState = createSearchPollsDataState();
        requestCreator = new RequestCreator();
        searchPollsItemDataTransformer = new SearchPollsItemDataTransformer(getResources());
        searchPollsCallback = new SearchPollsCallback(Lists.<OnPollsReceivedListener>newArrayList(this));
    }

    protected SearchPollsDataState createSearchPollsDataState() {
        return new SearchPollsDataState(OrderType.DESC, OrderKey.DATE);
    }

    protected abstract PollItemClickListener createPollItemClickListener();

    protected abstract void createSearchPollsAdapter(PollItemClickListener pollItemClickListener, View moreLoadView);

    private void initializeViews(LayoutInflater inflater, Bundle savedInstanceState, View rootView) {
        moreLoadView = inflater.inflate(R.layout.loadingview, null);

        createSearchPollsAdapter(createPollItemClickListener(), moreLoadView);
        pollsListWrapper = createPollsListWrapper(rootView, moreLoadView);
        pollsListWrapper.initializeView(savedInstanceState);
        setupSortingView(rootView);
    }


    protected void setupSortingView(View rootView) {
        sortingView = new SortingView(rootView, searchPollsDataState, this);
        sortingView.init();
        getRapidPollActivity().findViewById(R.id.my_toolbar).setOnTouchListener(new ToolbarTouchListener(sortingView));
    }

    protected PollsListWrapper createPollsListWrapper(View rootView, View moreLoadView) {
        return new PollsListWrapper(new LinearLayoutManager(getContext()), rootView, moreLoadView, this, this);
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
        if (items.size() == 0 && searchPollsDataState.actualPage == 2) {
            pollsListWrapper.hideList();
        } else {
            pollsListWrapper.showList();
        }
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
        createSearchPollsAdapter(createPollItemClickListener(), moreLoadView);
    }

    public boolean searchForText(String searchPhrase) {
        if (checkIsOnlineAndShowSimpleDialog()) {
            SearchPollRequest searchPollRequest = requestCreator.createSearchPollRequest(searchPhrase, searchPollsDataState, getListType());
            service.searchPoll(searchPollRequest, searchPollsCallback);
            return true;
        }
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_polls_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
                getRapidPollActivity().hideBackButton();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortingView != null) {
                    sortingView.hideSortByLayout();
                }
                replaceLogoByBackButton();
            }
        });
    }

    private void replaceLogoByBackButton() {
        getRapidPollActivity().showBackButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            case R.id.search:
                item.expandActionView();
                return true;
            case R.id.add_poll:
                getFragmentSwitcher().toManagePoll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
