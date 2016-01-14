package com.appsball.rapidpoll.pollresult.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsball.rapidpoll.pollresult.model.PollResultListItem;

public abstract class PollResultViewHolderParent extends RecyclerView.ViewHolder {
    public PollResultViewHolderParent(View parent) {
        super(parent);
    }

    public abstract void bindView(PollResultListItem pollResultListItem);
}