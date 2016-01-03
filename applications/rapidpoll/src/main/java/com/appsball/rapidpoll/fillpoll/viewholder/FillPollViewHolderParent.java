package com.appsball.rapidpoll.fillpoll.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;

public abstract class FillPollViewHolderParent extends RecyclerView.ViewHolder {
    public FillPollViewHolderParent(View parent) {
        super(parent);
    }

    public abstract void bindView(FillPollListItem fillPollListItem);
}