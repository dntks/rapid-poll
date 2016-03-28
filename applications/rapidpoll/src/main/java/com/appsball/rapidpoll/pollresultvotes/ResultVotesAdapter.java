package com.appsball.rapidpoll.pollresultvotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.appsball.rapidpoll.pollresultvotes.model.ResultVotesListItem;
import com.appsball.rapidpoll.pollresultvotes.viewholder.AnswerViewHolder;
import com.appsball.rapidpoll.pollresultvotes.viewholder.ResultVotesViewHolderParent;
import com.appsball.rapidpoll.pollresultvotes.viewholder.UserViewHolder;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.model.ViewType.fromValue;

public class ResultVotesAdapter extends RecyclerView.Adapter<ResultVotesViewHolderParent>{

    private final List<ResultVotesListItem> resultVotesListItems;

    public ResultVotesAdapter(List<ResultVotesListItem> resultVotesListItems) {
        this.resultVotesListItems = resultVotesListItems;
    }

    @Override
    public ResultVotesViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultvote_answer_listitem, parent, false);
                return new AnswerViewHolder(view);
            case QUESTION:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_email_listitem, parent, false);
                return new UserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ResultVotesViewHolderParent holder, int position) {
        holder.bindView(resultVotesListItems.get(position));

    }

    @Override
    public int getItemCount() {
        return resultVotesListItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return resultVotesListItems.get(position).getViewType().value;
    }
}
