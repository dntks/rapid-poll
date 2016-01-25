package com.appsball.rapidpoll.pollresult.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.base.Optional;

public class CommentItem implements PollResultListItem {
    public final String text;
    public final Optional<String> email;

    private CommentItem(String text, Optional<String> email) {
        this.text = text;
        this.email = email;
    }

    public static CommentItem commentItem(String text) {
        return new CommentItem(text, Optional.<String>absent());
    }

    public static CommentItem commentItem(String text, String email) {
        return new CommentItem(text, Optional.of(email));
    }

    @Override
    public ViewType getViewType() {
        return ViewType.COMMENT;
    }
}
