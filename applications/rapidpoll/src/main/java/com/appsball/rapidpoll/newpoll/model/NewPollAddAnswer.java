package com.appsball.rapidpoll.newpoll.model;

public class NewPollAddAnswer extends NewPollAnswer {

    public NewPollAddAnswer(NewPollQuestion question) {
        super("", question, false);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ADD_ANSWER;
    }
}
