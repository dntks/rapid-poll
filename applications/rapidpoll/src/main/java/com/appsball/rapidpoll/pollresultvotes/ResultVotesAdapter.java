package com.appsball.rapidpoll.pollresultvotes;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.appsball.rapidpoll.pollresultvotes.model.ResultVotesListItem;
import com.appsball.rapidpoll.pollresultvotes.viewholder.ResultVotesViewHolderParent;

import java.util.List;

public class ResultVotesAdapter extends RecyclerView.Adapter<ResultVotesViewHolderParent>{

    private final List<ResultVotesListItem> resultVotesListItems;

    public ResultVotesAdapter(List<ResultVotesListItem> resultVotesListItems) {
        this.resultVotesListItems = resultVotesListItems;
    }

    @Override
    public ResultVotesViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ResultVotesViewHolderParent holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
