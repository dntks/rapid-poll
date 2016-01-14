package com.appsball.rapidpoll.pollresult;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.viewholder.AlternativeCheckUpdater;
import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.appsball.rapidpoll.pollresult.model.PollResultListItem;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultCommentViewHolder;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultQuestionViewHolder;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultViewHolderParent;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.model.ViewType.fromValue;

public class PollResultAdapter  extends RecyclerView.Adapter<PollResultViewHolderParent> implements AlternativeCheckUpdater {

    private List<PollResultQuestionItem> pollResultQuestionItems;
    private List<CommentItem> commentItems;
    private List<PollResultListItem> allListItems;

    public PollResultAdapter(List<PollResultQuestionItem> pollResultQuestionItems, List<CommentItem> commentItems) {
        this.pollResultQuestionItems = pollResultQuestionItems;
        this.commentItems = commentItems;
        allListItems = Lists.newArrayList();
        allListItems.addAll(pollResultQuestionItems);
        allListItems.addAll(commentItems);
    }

    @Override
    public PollResultViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case COMMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fill_poll_comment, parent, false);
                return new PollResultCommentViewHolder(view);
            case QUESTION:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fill_poll_question, parent, false);
                return new PollResultQuestionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(PollResultViewHolderParent holder, int position) {
        holder.bindView(allListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return allListItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return allListItems.get(position).getViewType().value;
    }

    @Override
    public void alternativeUnchecked(FillPollAlternative prevCheckedAlternative) {
        Optional<Integer> location = getLocationOfItem(prevCheckedAlternative);
        if (location.isPresent()) {
            notifyItemChanged(location.get());
        }
    }

    private Optional<Integer> getLocationOfItem(FillPollListItem fillPollListItem) {
        for (int i = 0; i < allListItems.size(); i++) {
            if (allListItems.get(i).equals(fillPollListItem)) {
                return Optional.of(i);
            }
        }
        return Optional.absent();
    }
}