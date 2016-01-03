package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;

public class FillPollQuestionViewHolder extends FillPollViewHolderParent {
    private final String singleText;
    private final String multiText;
    private TextView questionText;
    private TextView multichoiceText;

    public FillPollQuestionViewHolder(View parent) {
        super(parent);
        questionText = (TextView) itemView.findViewById(R.id.question_textview);
        multichoiceText = (TextView) itemView.findViewById(R.id.multichoice_textview);
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
