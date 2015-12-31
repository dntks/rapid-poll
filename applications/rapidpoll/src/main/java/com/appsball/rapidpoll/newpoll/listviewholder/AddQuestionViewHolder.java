package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;

import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.listadapter.PollQuestionToAdapterAdder;

public class AddQuestionViewHolder extends ViewHolderParent {

    private PollQuestionToAdapterAdder pollQuestionToAdapterAdder;

    public AddQuestionViewHolder(View parent, PollQuestionToAdapterAdder pollQuestionToAdapterAdder) {
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