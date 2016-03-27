package com.appsball.rapidpoll.pollresultvotes.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class UserListItem extends ResultVotesListItem {
    public final String email;

    public UserListItem(String email) {
        this.email = email;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.USER;
    }
}