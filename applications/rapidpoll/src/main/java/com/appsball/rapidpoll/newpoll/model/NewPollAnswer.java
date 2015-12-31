package com.appsball.rapidpoll.newpoll.model;

public class NewPollAnswer extends NewPollListItem {
    public NewPollQuestion question;

    public NewPollAnswer(String text, NewPollQuestion question) {
        super(text);
        this.question = question;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
