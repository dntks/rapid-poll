package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.adapterhelper.QuestionItemRemover;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionNewPollViewHolder extends NewPollViewHolderParent {
    @BindView(R.id.question_edit_text) TextChangeAwareEditText editText;
    @BindView(R.id.multichoice_checkbox) CheckBox checkBox;
    @BindView(R.id.delete_button) ImageView deleteButton;
    private QuestionItemRemover questionItemRemover;

    public QuestionNewPollViewHolder(View parent, QuestionItemRemover questionItemRemover) {
        super(parent);
        ButterKnife.bind(this, parent);
        this.questionItemRemover = questionItemRemover;
    }

    @Override
    public void bindView(final NewPollListItem newPollListItem) {
        final NewPollQuestion newPollQuestion = (NewPollQuestion) newPollListItem;
        editText.setTextChangedListener(new TextChangedListener(newPollListItem));
        editText.setText(newPollQuestion.getQuestion());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newPollQuestion.setMultichoice(isChecked);
            }
        });
        checkBox.setChecked(newPollQuestion.isMultichoice());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionItemRemover.removeQuestion(newPollQuestion);
            }
        });
        deleteButton.setVisibility(questionItemRemover.isOnlyQuestionRemaining(newPollQuestion) ? View.GONE : View.VISIBLE);
    }
}