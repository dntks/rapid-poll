package com.appsball.rapidpoll.pollresult.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.base.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        if (StringUtils.isEmpty(email)) {
            return new CommentItem(text, Optional.<String>absent());
        } else {
            return new CommentItem(text, Optional.of(email));
        }
    }

    @Override
    public ViewType getViewType() {
        return ViewType.COMMENT;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
