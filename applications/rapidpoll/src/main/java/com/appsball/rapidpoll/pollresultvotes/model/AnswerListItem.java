package com.appsball.rapidpoll.pollresultvotes.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class AnswerListItem extends ResultVotesListItem {
    public final String name;
    public final String percentageValue;
    public final Integer color;

    public AnswerListItem(String name, String percentageValue, Integer color) {
        this.name = name;
        this.percentageValue = percentageValue;
        this.color = color;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
