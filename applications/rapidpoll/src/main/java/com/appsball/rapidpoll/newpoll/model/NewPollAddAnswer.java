package com.appsball.rapidpoll.newpoll.model;

public class NewPollAddAnswer extends NewPollAnswer {

    public NewPollAddAnswer(String text, NewPollQuestion question) {
        super(text, question);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ADD_ANSWER;
    }
}