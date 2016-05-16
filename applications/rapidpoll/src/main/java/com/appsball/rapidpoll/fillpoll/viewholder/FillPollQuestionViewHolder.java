package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillPollQuestionViewHolder extends FillPollViewHolderParent {
    private final String singleText;
    private final String multiText;
    @BindView(R.id.question_textview) TextView questionText;
    @BindView(R.id.multichoice_textview) TextView multichoiceText;

    public FillPollQuestionViewHolder(View parent) {
        super(parent);
        ButterKnife.bind(this, itemView);
        singleText = parent.getResources().getString(R.string.single);
        multiText = parent.getResources().getString(R.string.multi);
    }

    @Override
    public void bindView(FillPollListItem fillPollListItem) {
        final FillPollQuestion fillPollQuestion = (FillPollQuestion) fillPollListItem;
        questionText.setText(fillPollQuestion.question);
        multichoiceText.setText(fillPollQuestion.isMultiChoice ? multiText : singleText);
    }
}
