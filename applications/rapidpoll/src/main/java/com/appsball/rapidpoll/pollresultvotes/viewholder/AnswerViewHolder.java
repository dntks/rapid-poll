package com.appsball.rapidpoll.pollresultvotes.viewholder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresultvotes.model.AnswerListItem;

public class AnswerViewHolder extends ResultVotesViewHolderParent<AnswerListItem>{
    private View colorView;
    private TextView nameTextView;
    private TextView percentageTextView;

    public AnswerViewHolder(View parent) {
        super(parent);
        colorView = itemView.findViewById(R.id.color_view);
        nameTextView = (TextView) itemView.findViewById(R.id.answer_textview);
        percentageTextView = (TextView) itemView.findViewById(R.id.percentage_textview);
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
