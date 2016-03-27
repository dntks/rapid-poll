package com.appsball.rapidpoll.pollresultvotes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsball.rapidpoll.pollresultvotes.model.ResultVotesListItem;


public abstract class ResultVotesViewHolderParent<LISTITEM extends ResultVotesListItem> extends RecyclerView.ViewHolder {
    public ResultVotesViewHolderParent(View parent) {
        super(parent);
    }

    public abstract void bindView(LISTITEM listItem);

}