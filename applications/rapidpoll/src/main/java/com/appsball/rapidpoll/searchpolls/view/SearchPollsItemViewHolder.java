package com.appsball.rapidpoll.searchpolls.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

public class SearchPollsItemViewHolder extends UltimateRecyclerviewViewHolder {

    public TextView nameTextView;
    public TextView startedTextView;
    public TextView votesTextView;
    public ProgressBar answeredQuestionsBar;
    public ImageView itemRightImage;

    public SearchPollsItemViewHolder(View itemView, boolean isItem) {
        super(itemView);
        if (isItem) {
            nameTextView = (TextView) itemView.findViewById(R.id.pollitem_name);
            startedTextView = (TextView) itemView.findViewById(R.id.started_text);
            votesTextView = (TextView) itemView.findViewById(R.id.votes_text);
            answeredQuestionsBar = (ProgressBar) itemView.findViewById(R.id.answered_questions_bar);
            itemRightImage = (ImageView) itemView.findViewById(R.id.item_right_image);
        }
    }
}
