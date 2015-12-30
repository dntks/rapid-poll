package com.appsball.rapidpoll.newpoll;

public class NewPollAnswer extends NewPollListItem {
    NewPollQuestion question;

    public NewPollAnswer(String text, NewPollQuestion question) {
        super(text);
        this.question = question;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
