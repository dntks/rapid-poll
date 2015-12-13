package com.appsball.rapidpoll.allpolls;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

public class AllPollsItemViewHolder extends UltimateRecyclerviewViewHolder {

    TextView nameTextView;
    TextView startedTextView;
    TextView votesTextView;
    ProgressBar answeredQuestionsBar;

    public AllPollsItemViewHolder(View itemView, boolean isItem) {
        super(itemView);
        if (isItem) {
            nameTextView = (TextView) itemView.findViewById(R.id.pollitem_name);
            startedTextView = (TextView) itemView.findViewById(R.id.started_text);
            votesTextView = (TextView) itemView.findViewById(R.id.votes_text);
            answeredQuestionsBar = (ProgressBar) itemView.findViewById(R.id.answered_questions_bar);
        }
    }
}
