package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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
    }

    @Override
    public void bindView(FillPollListItem newPollListItem) {
        final FillPollAlternative fillPollAlternative = (FillPollAlternative) newPollListItem;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChanged(fillPollAlternative, isChecked);
            }
        });
    }

    private boolean checkAnyOtherIsSet(FillPollAlternative fillPollAlternative) {
        return !(fillPollAlternative.getQuestion().getCheckedAnswers().isEmpty());
    }

    private void checkChanged(FillPollAlternative fillPollAlternative, boolean isChecked) {
        Set<FillPollAlternative> checkedAnswers = fillPollAlternative.getQuestion().getCheckedAnswers();
        if (fillPollAlternative.getQuestion().isMultiChoice) {
            checkedAnswers.add(fillPollAlternative);
        } else {
            FillPollAlternative prevCheckedAlternative = checkedAnswers.iterator().next();
            if (!prevCheckedAlternative.equals(fillPollAlternative)) {
                checkedAnswers.remove(prevCheckedAlternative);
                checkedAnswers.add(fillPollAlternative);
                alternativeCheckUpdater.alternativeUnchecked(prevCheckedAlternative);
            }
        }
    }
}
