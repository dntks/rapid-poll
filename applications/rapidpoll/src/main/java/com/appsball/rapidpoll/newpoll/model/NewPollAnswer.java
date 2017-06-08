package com.appsball.rapidpoll.newpoll.model;

public class NewPollAnswer extends NewPollListItem {

    public final NewPollQuestion question;
    public final boolean isCreatedFromUI;
    private String answer;

    protected NewPollAnswer(String answer, NewPollQuestion question, boolean isCreatedFromUI) {
        this.answer = answer;
        this.question = question;
        this.isCreatedFromUI = isCreatedFromUI;
    }

    public static NewPollAnswer newPollAnswer(String answer, NewPollQuestion question) {
        return new NewPollAnswer(answer, question, false);
    }


    public static NewPollAnswer newPollAnswerFromUI(NewPollQuestion question) {
        return new NewPollAnswer("", question, true);
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
