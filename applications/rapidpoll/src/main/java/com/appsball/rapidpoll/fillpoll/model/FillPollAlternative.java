package com.appsball.rapidpoll.fillpoll.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class FillPollAlternative extends FillPollListItem {
    public final String id;
    public final String name;
    public final String letter;
    private FillPollQuestion question;

    private FillPollAlternative(String id, String name, String letter) {
        this.id = id;
        this.name = name;
        this.letter = letter;
    }

    public static FillPollAlternative fillPollAlternative(String id, String name, String letter) {
        return new FillPollAlternative(id, name, letter);
    }

    public FillPollQuestion getQuestion() {
        return question;
    }

    public void setQuestion(FillPollQuestion question) {
        this.question = question;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
