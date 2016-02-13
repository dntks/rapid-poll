package com.appsball.rapidpoll.newpoll.model;

public class NewPollAnswer extends NewPollListItem {

    public NewPollQuestion question;
    public String answer;

    public NewPollAnswer(String answer, NewPollQuestion question) {
        this.answer = answer;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void textChanged(String text) {
        this.answer = text;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
