package com.appsball.rapidpoll.fillpoll.viewholder;

import android.text.Editable;
import android.text.TextWatcher;

import com.appsball.rapidpoll.fillpoll.model.FillPollComment;

public class CommentTextChangedListener implements TextWatcher {
    private FillPollComment fillPollComment;

    public CommentTextChangedListener(FillPollComment fillPollComment) {
        this.fillPollComment = fillPollComment;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        fillPollComment.setComment(s.toString());
    }

}
