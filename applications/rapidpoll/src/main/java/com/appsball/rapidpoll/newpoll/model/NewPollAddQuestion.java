package com.appsball.rapidpoll.newpoll.model;

public class NewPollAddQuestion extends NewPollListItem {

    @Override
    public ViewType getViewType() {
        return ViewType.ADD_QUESTION;
    }

}
