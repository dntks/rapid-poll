package com.appsball.rapidpoll.newpoll.listadapter;

import android.view.View;

import com.appsball.rapidpoll.newpoll.model.NewPollListItem;

public interface AdapterItemViewRemover {
    void removeView(NewPollListItem newPollListItem, View v);
}