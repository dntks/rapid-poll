package com.appsball.rapidpoll.allpolls.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.allpolls.PollsListInitializer;
import com.appsball.rapidpoll.allpolls.model.AllPollsDataState;
import com.orhanobut.logger.Logger;

public class SortingView {

    private AllPollsDataState allPollsDataState;
    private PollsListInitializer pollsListInitializer;

    private View dateSortButton;
    private View titleSortButton;
    private View voteSortButton;
    private View publicitySortButton;
    private View statusSortButton;
    private View sortByLayout;
    private View pagingView;

    public SortingView(View rootView, AllPollsDataState allPollsDataState, PollsListInitializer pollsListInitializer) {
        this.allPollsDataState = allPollsDataState;
        this.pollsListInitializer = pollsListInitializer;
        sortByLayout = rootView.findViewById(R.id.sort_horizontal_scrollview);
        pagingView = rootView.findViewById(R.id.paging_list_view);
        dateSortButton = rootView.findViewById(R.id.sort_by_date_button);
        titleSortButton = rootView.findViewById(R.id.sort_by_title_button);
        voteSortButton = rootView.findViewById(R.id.sort_by_vote_button);
        publicitySortButton = rootView.findViewById(R.id.sort_by_publicity_button);
        statusSortButton = rootView.findViewById(R.id.sort_by_status_button);
    }

    public void init(){
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

    public void showSortByLayout() {
        if(sortByLayout.getVisibility() == View.VISIBLE){
            return;
        }
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

    public void hideSortByLayout() {
        if(sortByLayout.getVisibility() == View.GONE){
            return;
        }
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

    private void createSortButtonListeners() {
        dateSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setDateSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        titleSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setTitleSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        voteSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setVotesSort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        publicitySortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setPublicitySort();
                enableOtherButtons(v);
                pollsListInitializer.resetAdapterAndGetPolls();
            }
        });
        statusSortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                allPollsDataState.setStatusSort();
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
