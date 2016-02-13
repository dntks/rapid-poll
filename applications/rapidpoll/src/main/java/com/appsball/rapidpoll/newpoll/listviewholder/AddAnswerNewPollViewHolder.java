package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;

import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.newpoll.AdapterAnswerViewsUpdater;
import com.appsball.rapidpoll.newpoll.PollAnswerToAdapterAdder;
import com.appsball.rapidpoll.newpoll.model.NewPollAddAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

public class AddAnswerNewPollViewHolder extends NewPollViewHolderParent {
    private PollAnswerToAdapterAdder pollAnswerToAdapterAdder;
    private AdapterAnswerViewsUpdater adapterAnswerViewsUpdater;

    public AddAnswerNewPollViewHolder(View parent, PollAnswerToAdapterAdder pollAnswerToAdapterAdder, AdapterAnswerViewsUpdater adapterAnswerViewsUpdater) {
        super(parent);
        this.pollAnswerToAdapterAdder = pollAnswerToAdapterAdder;
        this.adapterAnswerViewsUpdater = adapterAnswerViewsUpdater;
    }

    @Override
    public void bindView(NewPollListItem newPollListItem) {
        final NewPollAddAnswer newPollAddAnswer = (NewPollAddAnswer) newPollListItem;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPollQuestion question = newPollAddAnswer.question;
                NewPollAnswer newAnswer = new NewPollAnswer(Utils.getLetterOfAlphabet(question.getAnswers().size() + 1), question);
                question.getAnswers().add(newAnswer);
                pollAnswerToAdapterAdder.addAnswerToAdapter(newAnswer, newPollAddAnswer);
                checkForSiblingAnswerViews(question);
            }
        });
    }

    private void checkForSiblingAnswerViews(NewPollQuestion newPollQuestion) {
        if (has3Items(newPollQuestion)) {
            adapterAnswerViewsUpdater.updateAnswerViews(newPollQuestion.getAnswers());
        }
    }

    private boolean has3Items(NewPollQuestion newPollQuestion) {
        return newPollQuestion.getAnswers().size() == 3;
    }
}