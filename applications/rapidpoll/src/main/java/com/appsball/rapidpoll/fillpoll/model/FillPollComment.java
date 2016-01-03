package com.appsball.rapidpoll.fillpoll.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;
import com.google.common.base.Optional;

import org.apache.commons.lang3.StringUtils;

public class FillPollComment extends FillPollListItem{
    private String comment;

    private FillPollComment(String comment) {
        this.comment = comment;
    }

    public static Optional<FillPollComment> optionalFillPollComment(String comment) {
        return Optional.of(new FillPollComment(comment));
    }

    public Optional<String> getComment() {
        if (StringUtils.isEmpty(comment)) {
            return Optional.absent();
        } else {
            return Optional.of(comment);
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.COMMENT;
    }
}
