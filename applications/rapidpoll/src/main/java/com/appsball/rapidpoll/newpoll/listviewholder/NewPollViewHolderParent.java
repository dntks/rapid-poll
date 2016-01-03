package com.appsball.rapidpoll.newpoll.listviewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsball.rapidpoll.newpoll.model.NewPollListItem;

public abstract class NewPollViewHolderParent extends RecyclerView.ViewHolder {
    public NewPollViewHolderParent(View parent) {
        super(parent);
    }

    public abstract void bindView(NewPollListItem newPollListItem);
}