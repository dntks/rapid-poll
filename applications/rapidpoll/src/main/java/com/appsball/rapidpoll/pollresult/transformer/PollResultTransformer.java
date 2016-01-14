package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.pollresult.model.PollResult;

public class PollResultTransformer {
    private final PollResultQuestionTransformer pollResultQuestionTransformer;

    public PollResultTransformer(PollResultQuestionTransformer pollResultQuestionTransformer) {
        this.pollResultQuestionTransformer = pollResultQuestionTransformer;
    }

    public PollResult transformPollResult(PollResultResponse pollResultResponse){
        PollResult.Builder builder = PollResult.builder();
        builder.withId(pollResultResponse.id);
        builder.withOwnerId(pollResultResponse.owner_id);
        builder.withPollName(pollResultResponse.poll_name);
        builder.withQuestions(pollResultQuestionTransformer.transformQuestions(pollResultResponse.questions));
        return builder.build();
    }
}
