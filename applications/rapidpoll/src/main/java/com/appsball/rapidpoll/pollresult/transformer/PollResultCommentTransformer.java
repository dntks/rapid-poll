package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.pollresult.model.CommentItem.commentItem;

public class PollResultCommentTransformer {
    public List<CommentItem> transformComments(List<String> comments) {

        return Lists.transform(comments, new Function<String, CommentItem>() {
            @Override
            public CommentItem apply(String input) {
                return transformComment(input);
            }
        });
    }

    private CommentItem transformComment(String comment) {
        return commentItem(comment);
    }
}
