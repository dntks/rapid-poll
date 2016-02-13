package com.appsball.rapidpoll.newpoll.listviewholder;

import android.text.Editable;
import android.text.TextWatcher;

import com.appsball.rapidpoll.newpoll.model.NewPollListItem;

public class TextChangedListener implements TextWatcher{
    private NewPollListItem newPollListItem;

    public TextChangedListener(NewPollListItem newPollListItem) {
        this.newPollListItem = newPollListItem;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        newPollListItem.textChanged(s.toString());
    }

}
