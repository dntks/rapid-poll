package com.appsball.rapidpoll.pollresultvotes.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresultvotes.model.AnswerListItem;

public class AnswerViewHolder extends ResultVotesViewHolderParent<AnswerListItem>{
    private TextView nameTextView;
    private TextView percentageTextView;

    public AnswerViewHolder(View parent) {
        super(parent);
        nameTextView = (TextView) itemView.findViewById(R.id.answer_textview);
        percentageTextView = (TextView) itemView.findViewById(R.id.percentage_textview);
    }

    @Override
    public void bindView(AnswerListItem answerListItem) {
        nameTextView.setText(answerListItem.name);
        percentageTextView.setText(answerListItem.percentageValue);
    }
}
