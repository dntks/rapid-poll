package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;

public class FillPollQuestionViewHolder extends FillPollViewHolderParent {
    private TextView questionText;
    private TextView multichoiceText;

    public FillPollQuestionViewHolder(View parent) {
        super(parent);
    }

    @Override
    public void bindView(FillPollListItem newPollListItem) {
        final FillPollQuestion fillPollQuestion = (FillPollQuestion) newPollListItem;
    }
}
