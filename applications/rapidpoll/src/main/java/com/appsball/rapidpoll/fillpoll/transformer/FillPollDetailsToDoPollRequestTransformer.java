package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.fillpoll.model.FillPollComment;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.google.common.base.Optional;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.commons.utils.Constants.PUBLIC_POLL_CODE;

public class FillPollDetailsToDoPollRequestTransformer {

    public final FillPollQuestionsToDoPollQuestionsTransformer questionsTransformer;

    public FillPollDetailsToDoPollRequestTransformer(FillPollQuestionsToDoPollQuestionsTransformer questionsTransformer) {
        this.questionsTransformer = questionsTransformer;
    }

    public DoPollRequest transformAnonymPoll(FillPollDetails fillPollDetails, Optional<String> code) {
        return transform(fillPollDetails, code, Optional.<String>absent());
    }

    public DoPollRequest transform(FillPollDetails fillPollDetails, Optional<String> code, Optional<String> email) {
        DoPollRequest.Builder builder = DoPollRequest.builder();
        builder.withPollId(fillPollDetails.pollId);
        builder.withUserId(Hawk.<String>get(Constants.USER_ID_KEY));
        Optional<FillPollComment> comment = fillPollDetails.comment;
        if (comment.isPresent()) {
            builder.withComment(comment.get().getComment());
        }
        builder.withQuestions(questionsTransformer.transformQuestions(fillPollDetails.questions));
        builder.withEmail(email);
        builder.withCode(code.or(PUBLIC_POLL_CODE));
        return builder.build();
    }

}
