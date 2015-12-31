package com.appsball.rapidpoll.newpoll.model;

public abstract class NewPollListItem {

    private String text;

    public NewPollListItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public abstract ViewType getViewType();
}
