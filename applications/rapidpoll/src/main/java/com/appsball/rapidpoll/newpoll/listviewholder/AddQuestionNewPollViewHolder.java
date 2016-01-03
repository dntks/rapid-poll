package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;

import com.appsball.rapidpoll.newpoll.PollQuestionToAdapterAdder;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;

public class AddQuestionNewPollViewHolder extends NewPollViewHolderParent {

    private PollQuestionToAdapterAdder pollQuestionToAdapterAdder;

    public AddQuestionNewPollViewHolder(View parent, PollQuestionToAdapterAdder pollQuestionToAdapterAdder) {
        super(parent);
        this.pollQuestionToAdapterAdder = pollQuestionToAdapterAdder;
    }

    @Override
    public void bindView(final NewPollListItem newPollListItem) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollQuestionToAdapterAdder.addNewQuestion();
            }
        });
    }
}