package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.pollresult.model.CommentItem.commentItem;
import static com.google.common.collect.Lists.newArrayList;

public class PollResultCommentTransformer {
    public List<CommentItem> transformComments(List<PollResultEmail> emails) {
        if (emails == null || emails.isEmpty()) {
            return newArrayList();
        } else {
            return Lists.transform(emails, new Function<PollResultEmail, CommentItem>() {
                @Override
                public CommentItem apply(PollResultEmail input) {
                    return transformComment(input);
                }
            });
        }
    }

    private CommentItem transformComment(PollResultEmail comment) {
        return commentItem(comment.comment, comment.email);
    }
}
