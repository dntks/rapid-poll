package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.newpoll.adapterhelper.AdapterAnswerViewsUpdater;
import com.appsball.rapidpoll.newpoll.adapterhelper.AdapterItemViewRemover;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

import java.util.List;

public class AnswerNewPollViewHolder extends NewPollViewHolderParent {

    private AdapterItemViewRemover adapterItemViewRemover;
    private AdapterAnswerViewsUpdater adapterAnswerViewsUpdater;
    private TextChangeAwareEditText editText;
    private ImageView deleteButton;
    private View listitemAnswerSeparator;

    public AnswerNewPollViewHolder(View parent,
                                   AdapterItemViewRemover adapterItemViewRemover,
                                   AdapterAnswerViewsUpdater adapterAnswerViewsUpdater) {
        super(parent);
        this.adapterItemViewRemover = adapterItemViewRemover;
        this.adapterAnswerViewsUpdater = adapterAnswerViewsUpdater;
        editText = (TextChangeAwareEditText) itemView.findViewById(R.id.answer_edit_text);
        deleteButton = (ImageView) parent.findViewById(R.id.delete_button);
        listitemAnswerSeparator = parent.findViewById(R.id.listitem_answer_separator);
    }


    @Override
    public void bindView(NewPollListItem newPollListItem) {
        final NewPollAnswer newPollAnswer = (NewPollAnswer) newPollListItem;
        editText.setTextChangedListener(new TextChangedListener(newPollListItem));
        editText.setText(newPollAnswer.getAnswer());

        final NewPollQuestion question = newPollAnswer.question;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemViewRemover.removeView(newPollAnswer);
                question.removeAnswer(newPollAnswer);
                checkForSiblingAnswerViews(question);
            }
        });
        setSeparatorVisibility(newPollAnswer);
        setDeleteButtonVisibility(question);
        requestFocusAndShowKeyBoardIfAddedFromUI(newPollAnswer);
    }

    private void requestFocusAndShowKeyBoardIfAddedFromUI(NewPollAnswer newPollAnswer) {
        if (newPollAnswer.isCreatedFromUI && isLastAnswerView(newPollAnswer) && hasMoreThan2Items(newPollAnswer.question)) {
            editText.requestFocus();
            Utils.showSoftKeyboard(editText.getContext());
        }
    }

    private boolean isLastAnswerView(NewPollAnswer newPollAnswer) {
        List<NewPollAnswer> answers = newPollAnswer.question.getAnswers();
        return answers.get(answers.size()-1).equals(newPollAnswer);
    }

    private void checkForSiblingAnswerViews(NewPollQuestion newPollQuestion) {
        if (!hasMoreThan2Items(newPollQuestion)) {
            adapterAnswerViewsUpdater.updateAnswerViews(newPollQuestion.getAnswers());
        }
    }


    private void setDeleteButtonVisibility(NewPollQuestion newPollQuestion) {
        deleteButton.setVisibility(hasMoreThan2Items(newPollQuestion) ? View.VISIBLE : View.INVISIBLE);
    }

    private void setSeparatorVisibility(NewPollAnswer newPollAnswer) {
        listitemAnswerSeparator.setVisibility(isFirstAnswerView(newPollAnswer) ? View.GONE : View.VISIBLE);
    }

    private boolean isFirstAnswerView(NewPollAnswer newPollAnswer) {
        return newPollAnswer.question.getAnswers().get(0).equals(newPollAnswer);
    }

    private boolean hasMoreThan2Items(NewPollQuestion newPollQuestion) {
        return newPollQuestion.getAnswers().size() > 2;
    }
}