package com.appsball.rapidpoll.fillpoll.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class FillPollAlternative extends FillPollListItem {
    public final String id;
    public final String name;
    public FillPollQuestion question;

    private FillPollAlternative(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FillPollAlternative fillPollAlternative(String id, String name) {
        return new FillPollAlternative(id, name);
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
