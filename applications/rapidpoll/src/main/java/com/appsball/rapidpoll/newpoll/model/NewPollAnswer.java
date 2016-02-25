package com.appsball.rapidpoll.newpoll.model;

public class NewPollAnswer extends NewPollListItem {

    public NewPollQuestion question;
    public String answer;

    protected NewPollAnswer(String answer, NewPollQuestion question) {
        this.answer = answer;
        this.question = question;
    }

    public static NewPollAnswer newPollAnswer(String answer, NewPollQuestion question) {
        return new NewPollAnswer(answer, question);
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
