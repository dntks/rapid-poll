package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;

import java.util.Set;

public class FillPollAlternativeViewHolder extends FillPollViewHolderParent {
    private AlternativeCheckUpdater alternativeCheckUpdater;
    private TextView alternativeText;
    private CheckBox checkBox;

    public FillPollAlternativeViewHolder(View parent, AlternativeCheckUpdater alternativeCheckUpdater) {
        super(parent);
        this.alternativeCheckUpdater = alternativeCheckUpdater;
        this.alternativeText = (TextView) itemView.findViewById(R.id.answer_textview);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.answer_checkbox);
    }

    @Override
    public void bindView(FillPollListItem fillPollListItem) {
        final FillPollAlternative fillPollAlternative = (FillPollAlternative) fillPollListItem;
        alternativeText.setText(fillPollAlternative.name);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(fillPollAlternative.getQuestion().getCheckedAnswers().contains(fillPollAlternative));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChanged(fillPollAlternative, isChecked);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    private boolean checkAnyOtherIsSet(FillPollAlternative fillPollAlternative) {
        return !(fillPollAlternative.getQuestion().getCheckedAnswers().isEmpty());
    }

    private void checkChanged(FillPollAlternative fillPollAlternative, boolean isChecked) {
        if (isChecked) {
            alternativeChecked(fillPollAlternative);
        } else {
            alternativeRemoved(fillPollAlternative);
        }
    }

    private void alternativeRemoved(FillPollAlternative fillPollAlternative) {
        Set<FillPollAlternative> checkedAnswers = fillPollAlternative.getQuestion().getCheckedAnswers();
        checkedAnswers.remove(fillPollAlternative);
    }

    private void alternativeChecked(FillPollAlternative fillPollAlternative) {
        Set<FillPollAlternative> checkedAnswers = fillPollAlternative.getQuestion().getCheckedAnswers();
        if (!fillPollAlternative.getQuestion().isMultiChoice && !checkedAnswers.isEmpty()) {
            FillPollAlternative prevCheckedAlternative = checkedAnswers.iterator().next();
            if (!prevCheckedAlternative.equals(fillPollAlternative)) {
                checkedAnswers.remove(prevCheckedAlternative);
                alternativeCheckUpdater.alternativeUnchecked(prevCheckedAlternative);
            }
        }
        checkedAnswers.add(fillPollAlternative);
    }
}
