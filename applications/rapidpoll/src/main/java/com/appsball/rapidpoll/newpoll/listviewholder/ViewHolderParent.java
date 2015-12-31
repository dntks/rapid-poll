package com.appsball.rapidpoll.newpoll.listviewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsball.rapidpoll.newpoll.listadapter.TextChangedListener;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;

public abstract class ViewHolderParent extends RecyclerView.ViewHolder {
    public ViewHolderParent(View parent) {
        super(parent);
    }

    public abstract void bindView(NewPollListItem newPollListItem);
}