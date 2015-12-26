package com.appsball.rapidpoll.allpolls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.google.common.collect.Lists;
import com.orhanobut.logger.Logger;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

public class AllPollsFragment extends BottomBarNavigationFragment implements GetPollsCaller, SearchPollsCaller {

    public static final int ALLPOLLS_LAYOUT = R.layout.allpolls_layout;

    AllPollsDataState allPollsDataState;

    private View rootView;

    private RapidPollRestService service;
    private AllPollsAdapter allPollsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private AllPollsItemDataTransformer allPollsItemDataTransformer;

    private boolean isNetDialogShownForGetPolls = false;
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
        allPollsDataState = new AllPollsDataState();
        DateStringFormatter dateStringFormatter = new DateStringFormatter(getResources());
        allPollsItemDataTransformer = new AllPollsItemDataTransformer(dateStringFormatter, getResources());
        moreLoadView = inflater.inflate(R.layout.loadingview, null);
        initializeAllPollsAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext());
        pollsListWrapper = new PollsListWrapper(linearLayoutManager, rootView, moreLoadView, this, this);
        pollsListWrapper.initializeView(savedInstanceState);
        createNavigationButtonListeners(rootView, NavigationButton.POLLS_BUTTON);
        createSortButtonListeners(rootView);
        setToolBarSwipeListener();
        setSortByViewSwipeListener();
        callGetPolls();
        return rootView;
    }

    private void setSortByViewSwipeListener() {
        View sortByLayout = rootView.findViewById(R.id.sort_horizontal_scrollview);
        sortByLayout.setOnTouchListener(new View.OnTouchListener() {
            boolean hasMovedOut = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_MOVE):
                        int topOfView = v.getTop();
                        float eventY = event.getY();
                        Logger.d("Action was MOVE at " + eventY + " top is:" + topOfView);
                        hasMovedOut = eventY < topOfView;
                        return false;
                    case (MotionEvent.ACTION_UP):
                        Logger.d("Action was UP");
                        if (hasMovedOut) {
                            hideSortByLayout();
                        }
                        return false;
                    default:
                        return false;
                }

            }
        });
    }

    private void setToolBarSwipeListener() {
        View toolbar = getActivity().findViewById(R.id.my_toolbar);
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            boolean hasMovedOut = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_MOVE):
                        int bottomOfView = v.getBottom();
                        float eventY = event.getY();
                        Logger.d("Action was MOVE at " + eventY + " top is:" + bottomOfView);
                        hasMovedOut = eventY > bottomOfView;
                        return false;
                    case (MotionEvent.ACTION_UP):
                        Logger.d("Action was UP");
                        if (hasMovedOut) {
                            showSortByLayout();
                        }
                        return false;
                    default:
                        return false;
                }

            }
        });
    }

    private void hideSortByLayout() {
        final View sortByLayout = rootView.findViewById(R.id.sort_horizontal_scrollview);
        if(sortByLayout.getVisibility() == View.GONE){
            return;
        }
        final View pagingView = rootView.findViewById(R.id.paging_list_view);
        pagingView.animate()
                .translationY(sortByLayout.getHeight() * -1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        sortByLayout.setVisibility(View.GONE);
                        pagingView.setTranslationY(0);
                    }
                });
    }

    private void showSortByLayout() {
        final View sortByLayout = rootView.findViewById(R.id.sort_horizontal_scrollview);
        if(sortByLayout.getVisibility() == View.VISIBLE){
            return;
        }
        final View pagingView = rootView.findViewById(R.id.paging_list_view);
        sortByLayout.setVisibility(View.VISIBLE);
        pagingView.setTranslationY(sortByLayout.getHeight() * -1);
        pagingView.animate()
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        pagingView.setTranslationY(0);
                    }
                });
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
                allPollsDataState.setDateSort();
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        titleSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setTitleSort();
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        voteSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setVotesSort();
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        publicitySortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setPublicitySort();
                enableOtherButtons(v);
                resetAdapterAndCallPolls();
            }
        });
        statusSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setStatusSort();
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
            service.getPolls(allPollsDataState.createAllPollsRequest(),
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
                allPollsDataState.actualPage++;
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
                allPollsDataState.actualPage = 1;
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSortByLayout();
            }
        });
    }

    private void resetAdapterAndCallPolls() {
        allPollsDataState.actualPage = 1;
        allPollsAdapter.removeAllItems();
        pollsListWrapper.setGetPollsMoreListener();
        pollsListWrapper.resetPollsList();
        callGetPolls();
    }


    public boolean searchForText(String searchPhrase) {
        if (checkIsOnlineAndShowSimpleDialog()) {
            service.searchPoll(allPollsDataState.createSearchPollRequest(searchPhrase),
                               createSearchPollsCallback());
            return true;
        }
        return false;
    }

    private Callback<ResponseContainer<List<PollsResponse>>> createSearchPollsCallback() {
        return new Callback<ResponseContainer<List<PollsResponse>>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
                allPollsDataState.actualPage++;
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
