package com.appsball.rapidpoll.pollresult;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.appsball.rapidpoll.pollresult.model.PollResult;
import com.appsball.rapidpoll.pollresult.model.PollResultListItem;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultCommentViewHolder;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultQuestionViewHolder;
import com.appsball.rapidpoll.pollresult.viewholder.PollResultViewHolderParent;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.newpoll.model.ViewType.fromValue;

public class PollResultQuestionAdapter extends RecyclerView.Adapter<PollResultViewHolderParent>{

    private final PollResult pollResult;
    private final List<PollResultListItem> allListItems;
    private final List<Integer> answerColors;
    private final boolean isAnonymous;
    private final PollResultQuestionItemClickListener pollResultQuestionItemClickListener;

    public PollResultQuestionAdapter(PollResult pollResult,
                                     List<Integer> answerColors,
                                     boolean isAnonymous,
                                     PollResultQuestionItemClickListener pollResultQuestionItemClickListener) {
        this.pollResult = pollResult;
        this.answerColors = answerColors;
        this.isAnonymous = isAnonymous;
        this.pollResultQuestionItemClickListener = pollResultQuestionItemClickListener;
        allListItems = Lists.newArrayList();
        allListItems.addAll(pollResult.questions);
        allListItems.addAll(pollResult.comments);
    }

    @Override
    public PollResultViewHolderParent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewType type = fromValue(viewType);
        switch (type) {
            case COMMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pollresult_comment, parent, false);
                return new PollResultCommentViewHolder(view);
            case QUESTION:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pollresult_item, parent, false);
                return new PollResultQuestionViewHolder(view, pollResultQuestionItemClickListener, answerColors, isAnonymous);
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

}