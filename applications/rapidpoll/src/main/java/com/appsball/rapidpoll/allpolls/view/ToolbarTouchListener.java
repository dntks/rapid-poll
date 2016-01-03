package com.appsball.rapidpoll.allpolls.view;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

public class ToolbarTouchListener implements View.OnTouchListener {
    private final SortingView sortingView;
    private boolean hasMovedOut = false;

    public ToolbarTouchListener(SortingView sortingView) {
        this.sortingView = sortingView;
    }

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
                    sortingView.showSortByLayout();
                }
                return false;
            default:
                return false;
        }

    }
}

