package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;

public class PollDetailsResponseTransformer {

    private final PollDetailsQuestionsTransformer pollDetailsQuestionsTransformer;

    public PollDetailsResponseTransformer(PollDetailsQuestionsTransformer pollDetailsQuestionsTransformer) {
        this.pollDetailsQuestionsTransformer = pollDetailsQuestionsTransformer;
    }

    public FillPollDetails transform(PollDetailsResponse pollDetailsResponse){
        FillPollDetails.Builder builder = FillPollDetails.builder();
        builder.withPollId(String.valueOf(pollDetailsResponse.id));
        builder.withQuestions(pollDetailsQuestionsTransformer.transformQuestions(pollDetailsResponse.questions));
        builder.withName(pollDetailsResponse.name);
        builder.withAllowComment(pollDetailsResponse.allow_comment == 1);
        builder.withAllowUncompleteResult(true);
        builder.withCommentOptional(pollDetailsResponse.comment);
        builder.withCode(pollDetailsResponse.code);
        builder.withEmail(pollDetailsResponse.email);
        builder.withIsAnonymous(pollDetailsResponse.anonymous==1);
        builder.withIsPublic(pollDetailsResponse.isPublic==1);
        return builder.build();
    }
}
