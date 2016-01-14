package com.appsball.rapidpoll.pollresult.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

public class CommentItem implements PollResultListItem {
    public final String ownerEmail;
    public final String text;

    public CommentItem(String ownerEmail, String text) {
        this.ownerEmail = ownerEmail;
        this.text = text;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.COMMENT;
    }
}
