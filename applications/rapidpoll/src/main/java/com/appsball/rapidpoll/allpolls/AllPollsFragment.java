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
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.google.common.collect.Lists;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

public class AllPollsFragment extends BottomBarNavigationFragment implements GetPollsCaller, SearchPollsCaller {

    public static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;
    public static final int NUMBER_OF_REQUESTED_POLLS = 10;

    public OrderType chosenOrderType = OrderType.DESC;
    public OrderKey chosenOrderKey = OrderKey.DATE;

    private View rootView;

    private RapidPollRestService service;
    private AllPollsAdapter allPollsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private AllPollsItemDataTransformer allPollsItemDataTransformer;

    private boolean isNetDialogShownForGetPolls = false;
    private int actualPage = 1;
    private PollsListWrapper pollsListWrapper;
    private View moreLoadView;
    private View dateSortButton;
    private View titleSortButton;
    private View voteSortButton;
    private View publicitySortButton;
    private View statusSortButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(ALLPOLLS_LAYOUT, container, false);
        DateStringFormatter dateStringFormatter = new DateStringFormatter(getResources());
        allPollsItemDataTransformer = new AllPollsItemDataTransformer(dateStringFormatter, getResources());
        moreLoadView = inflater.inflate(R.layout.loadingview, null);
        initializeAllPollsAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext());
        pollsListWrapper = new PollsListWrapper(linearLayoutManager, rootView, moreLoadView, this, this);
        pollsListWrapper.initializeView(savedInstanceState, inflater);
        createNavigationButtonListeners(rootView, NavigationButton.POLLS_BUTTON);
        createSortButtonListeners(rootView);
        callGetPolls();
        return rootView;
    }

    private void createSortButtonListeners(View rootView) {
        dateSortButton = rootView.findViewById(R.id.sort_by_date_button);
        titleSortButton = rootView.findViewById(R.id.sort_by_title_button);
        voteSortButton = rootView.findViewById(R.id.sort_by_vote_button);
        publicitySortButton = rootView.findViewById(R.id.sort_by_publicity_button);
        statusSortButton = rootView.findViewById(R.id.sort_by_status_button);
        dateSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chosenOrderKey = OrderKey.DATE;
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        titleSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chosenOrderKey = OrderKey.TITLE;
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        voteSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chosenOrderKey = OrderKey.VOTES;
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        publicitySortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chosenOrderKey = OrderKey.PUBLIC;
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        statusSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chosenOrderKey = OrderKey.STATUS;
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
    }

    private void enableOtherButtons(View v) {
        dateSortButton.setEnabled(true);
        titleSortButton.setEnabled(true);
        voteSortButton.setEnabled(true);
        publicitySortButton.setEnabled(true);
        statusSortButton.setEnabled(true);
        v.setEnabled(false);
    }


    public void initializeAllPollsAdapter() {
        allPollsAdapter = new AllPollsAdapter(Lists.<AllPollsItemData>newArrayList());
        allPollsAdapter.setCustomLoadMoreView(moreLoadView);
    }

    public void callGetPolls() {
        if (checkIsOnlineAndShowSimpleDialog(getGetPollsOnNetOkButtonListener())) {
            isNetDialogShownForGetPolls = false;
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
        builder.withOrderType(chosenOrderType);
        builder.withOrderKey(chosenOrderKey);
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
                pollsListWrapper.setupAdapterIfFirstCallIsBeingDone(allPollsAdapter);
                List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(listResponseContainer.result);
                allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
                pollsListWrapper.disableLoadMoreIfNoMoreItems(items);
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_polls_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                actualPage = 1;
                allPollsAdapter.removeAllItems();
                pollsListWrapper.resetPollsList();
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
                resetAdapterAndCallPolls();
                return false;
            }
        });
    }

    private void resetAdapterAndCallPolls() {
        actualPage = 1;
        allPollsAdapter.removeAllItems();
        pollsListWrapper.setGetPollsMoreListener();
        pollsListWrapper.resetPollsList();
        callGetPolls();
    }


    public boolean searchForText(String searchPhrase) {
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
                pollsListWrapper.setupAdapterIfFirstCallIsBeingDone(allPollsAdapter);
                List<AllPollsItemData> items = allPollsItemDataTransformer.transformAll(listResponseContainer.result);
                allPollsAdapter.insertAll(items, allPollsAdapter.getAdapterItemCount());
                pollsListWrapper.disableLoadMoreIfNoMoreItems(items);
            }

            @Override
            public void onError(WaspError error) {

            }
        };
    }

    private SearchPollRequest createSearchPollRequest(String searchPhrase) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(actualPage));
        builder.withOrderType(chosenOrderType);
        builder.withOrderKey(chosenOrderKey);
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
