package com.appsball.rapidpoll.newpoll.model;

public class NewPollAddQuestion extends NewPollListItem {

    public NewPollAddQuestion(String text) {
        super(text);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ADD_QUESTION;
    }

}
