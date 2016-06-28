package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static com.appsball.rapidpoll.pollresult.model.CommentItem.commentItem;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class PollResultCommentTransformer {
    public List<CommentItem> transformComments(List<PollResultEmail> emails) {
        if (emails == null || emails.isEmpty()) {
            return newArrayList();
        } else {
            Collection<PollResultEmail> notEmptyComments = Collections2.filter(emails, new Predicate<PollResultEmail>() {
                @Override
                public boolean apply(PollResultEmail input) {
                    return isNotEmpty(input.comment);
                }
            });
            return Lists.newArrayList(Collections2.transform(notEmptyComments, new Function<PollResultEmail, CommentItem>() {
                @Override
                public CommentItem apply(PollResultEmail input) {
                    return transformComment(input);
                }
            }));
        }
    }

    private CommentItem transformComment(PollResultEmail comment) {
        return commentItem(comment.comment, comment.email);
    }
}
