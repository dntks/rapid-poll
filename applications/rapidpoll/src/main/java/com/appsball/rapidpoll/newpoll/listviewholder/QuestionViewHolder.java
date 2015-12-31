package com.appsball.rapidpoll.newpoll.listviewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.listadapter.TextChangedListener;
import com.appsball.rapidpoll.newpoll.model.NewPollListItem;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

public class QuestionViewHolder extends ViewHolderParent {
    private TextChangeAwareEditText editText;
    private CheckBox checkBox;

    public QuestionViewHolder(View parent) {
        super(parent);
        editText = (TextChangeAwareEditText) parent.findViewById(R.id.question_edit_text);
        checkBox = (CheckBox) parent.findViewById(R.id.multichoice_checkbox);
    }

    @Override
    public void bindView(final NewPollListItem newPollListItem) {
        final NewPollQuestion newPollQuestion = (NewPollQuestion) newPollListItem;
        editText.setTextChangedListener(new TextChangedListener(newPollListItem));
        editText.setText(newPollQuestion.getText());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newPollQuestion.setMultichoice(isChecked);
            }
        });
        checkBox.setChecked(newPollQuestion.isMultichoice());
    }
}