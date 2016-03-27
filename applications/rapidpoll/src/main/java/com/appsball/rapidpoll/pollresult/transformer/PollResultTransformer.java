package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultAlternative;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultQuestion;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.appsball.rapidpoll.pollresult.model.PollResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;

public class PollResultTransformer {
    private final PollResultQuestionTransformer pollResultQuestionTransformer;
    private final PollResultCommentTransformer pollResultCommentTransformer;

    public PollResultTransformer(PollResultQuestionTransformer pollResultQuestionTransformer,
                                 PollResultCommentTransformer pollResultCommentTransformer) {
        this.pollResultQuestionTransformer = pollResultQuestionTransformer;
        this.pollResultCommentTransformer = pollResultCommentTransformer;
    }

    public PollResult transformPollResult(PollResultResponse pollResultResponse) {
        PollResult.Builder builder = PollResult.builder();
        builder.withId(pollResultResponse.id);
        builder.withOwnerId(pollResultResponse.owner_id);
        builder.withPollName(pollResultResponse.poll_name);
        builder.withQuestions(pollResultQuestionTransformer.transformQuestions(pollResultResponse.questions));
        List<CommentItem> comments = pollResultCommentTransformer.transformComments(pollResultResponse.emails);
        for (PollResultQuestion pollResultQuestion : pollResultResponse.questions) {
            for (PollResultAlternative pollResultAlternative : pollResultQuestion.alternatives) {
                comments.addAll(pollResultCommentTransformer.transformComments(pollResultAlternative.emails));
            }
        }
        builder.withComments(Lists.newArrayList(Sets.newHashSet(comments)));
        return builder.build();
    }
}
