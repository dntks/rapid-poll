package com.appsball.rapidpoll.pollresultvotes.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class AnswerListItem extends ResultVotesListItem {
    public final String name;
    public final String percentageValue;

    public AnswerListItem(String name, String percentageValue) {
        this.name = name;
        this.percentageValue = percentageValue;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.ANSWER;
    }
}
