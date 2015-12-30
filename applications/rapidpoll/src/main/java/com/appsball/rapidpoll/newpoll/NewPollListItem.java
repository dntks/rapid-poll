package com.appsball.rapidpoll.newpoll;

public abstract class NewPollListItem {
    public final String text;

    public NewPollListItem(String text) {
        this.text = text;
    }

    public abstract ViewType getViewType();
}
