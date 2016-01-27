package com.appsball.rapidpoll.searchpolls.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.searchpolls.PollsListInitializer;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.orhanobut.logger.Logger;

public class SortingView {

    private SearchPollsDataState searchPollsDataState;
    private PollsListInitializer pollsListInitializer;
    private boolean isAnimating = false;

    private View dateSortButton;
    private View titleSortButton;
    private View voteSortButton;
    private View publicitySortButton;
    private View statusSortButton;
    private View sortByLayout;
    private View listSizeHelper;
    private View pagingView;

    public SortingView(View rootView, SearchPollsDataState searchPollsDataState, PollsListInitializer pollsListInitializer) {
        this.searchPollsDataState = searchPollsDataState;
        this.pollsListInitializer = pollsListInitializer;
        sortByLayout = rootView.findViewById(R.id.sort_horizontal_scrollview);
        pagingView = rootView.findViewById(R.id.paging_list_view);
        dateSortButton = rootView.findViewById(R.id.sort_by_date_button);
        titleSortButton = rootView.findViewById(R.id.sort_by_title_button);
        voteSortButton = rootView.findViewById(R.id.sort_by_vote_button);
        publicitySortButton = rootView.findViewById(R.id.sort_by_publicity_button);
        statusSortButton = rootView.findViewById(R.id.sort_by_status_button);
        listSizeHelper = rootView.findViewById(R.id.list_size_helper);
    }

    public void init() {
        setSortByViewSwipeListener();
        createSortButtonListeners();
    }

    private void setSortByViewSwipeListener() {
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

    public void hideSortByLayout() {
        if (!isSortingVisible() || isAnimating) {
            return;
        }
        isAnimating = true;
        int height = sortByLayout.getHeight();
        listSizeHelper.setVisibility(View.GONE);
        pagingView.setTranslationY(height);
        pagingView.animate()
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        sortByLayout.setVisibility(View.INVISIBLE);
                        isAnimating = false;
                    }
                });

    }

    public void showSortByLayout() {
        if (isSortingVisible() || isAnimating) {
            return;
        }
        isAnimating = true;
        sortByLayout.setVisibility(View.VISIBLE);
        int height = sortByLayout.getHeight();
        pagingView.animate()
                .translationY(height)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        listSizeHelper.setVisibility(View.VISIBLE);
                        pagingView.setTranslationY(0);
                        isAnimating = false;
                    }
                });
    }

    private boolean isSortingVisible() {
        return sortByLayout.getVisibility() == View.VISIBLE
                && listSizeHelper.getVisibility() == View.VISIBLE;
    }

    private void createSortButtonListeners() {
        dateSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPollsDataState.setDateSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        titleSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPollsDataState.setTitleSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        voteSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPollsDataState.setVotesSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        publicitySortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPollsDataState.setPublicitySort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        statusSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPollsDataState.setStatusSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
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
}
