package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.adapterhelper.QuestionItemRemover;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

public class QuestionNewPollViewHolder extends NewPollViewHolderParent {
    private TextChangeAwareEditText editText;
    private CheckBox checkBox;
    private ImageView deleteButton;
    private QuestionItemRemover questionItemRemover;

    public QuestionNewPollViewHolder(View parent, QuestionItemRemover questionItemRemover) {
        super(parent);
        editText = (TextChangeAwareEditText) parent.findViewById(R.id.question_edit_text);
        checkBox = (CheckBox) parent.findViewById(R.id.multichoice_checkbox);
        deleteButton = (ImageView) parent.findViewById(R.id.delete_button);
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