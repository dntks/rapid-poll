package com.appsball.rapidpoll.pollresultvotes.viewholder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresultvotes.model.AnswerListItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerViewHolder extends ResultVotesViewHolderParent<AnswerListItem>{
    @BindView(R.id.color_view) View colorView;
    @BindView(R.id.answer_textview) TextView nameTextView;
    @BindView(R.id.percentage_textview) TextView percentageTextView;

    public AnswerViewHolder(View parent) {
        super(parent);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(AnswerListItem answerListItem) {
        setColorView(colorView, answerListItem.color);
        nameTextView.setText(answerListItem.name);
        percentageTextView.setText(answerListItem.percentageValue);
    }

    private void setColorView(View colorView, Integer color) {
        Drawable background = colorView.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(color);
        }
    }
}
