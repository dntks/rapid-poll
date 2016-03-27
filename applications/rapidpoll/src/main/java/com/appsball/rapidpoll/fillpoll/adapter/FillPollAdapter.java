package com.appsball.rapidpoll.fillpoll.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.viewholder.AlternativeCheckUpdater;
import com.appsball.rapidpoll.fillpoll.viewholder.FillPollAlternativeViewHolder;
import com.appsball.rapidpoll.fillpoll.viewholder.FillPollCommentViewHolder;
import com.appsball.rapidpoll.fillpoll.viewholder.FillPollQuestionViewHolder;
import com.appsball.rapidpoll.fillpoll.viewholder.FillPollViewHolderParent;
import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.base.Optional;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.model.ViewType.fromValue;

public class FillPollAdapter  extends RecyclerView.Adapter<FillPollViewHolderParent> implements AlternativeCheckUpdater{

    private List<FillPollListItem> fillPollListItems;

    public FillPollAdapter(List<FillPollListItem> fillPollListItems) {
        this.fillPollListItems = fillPollListItems;
    }

    @Override
    public FillPollViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case ANSWER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fill_poll_answer, parent, false);
                return new FillPollAlternativeViewHolder(view, this);
            case COMMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fill_poll_comment, parent, false);
                return new FillPollCommentViewHolder(view);
            case QUESTION:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fill_poll_question, parent, false);
                return new FillPollQuestionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(FillPollViewHolderParent holder, int position) {
        holder.bindView(fillPollListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return fillPollListItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return fillPollListItems.get(position).getViewType().value;
    }

    @Override
    public void alternativeUnchecked(FillPollAlternative prevCheckedAlternative) {
        Optional<Integer> location = getLocationOfItem(prevCheckedAlternative);
        if (location.isPresent()) {
            notifyItemChanged(location.get());
        }
    }

    private Optional<Integer> getLocationOfItem(FillPollListItem fillPollListItem) {
        for (int i = 0; i < fillPollListItems.size(); i++) {
            if (fillPollListItems.get(i).equals(fillPollListItem)) {
                return Optional.of(i);
            }
        }
        return Optional.absent();
    }
}
