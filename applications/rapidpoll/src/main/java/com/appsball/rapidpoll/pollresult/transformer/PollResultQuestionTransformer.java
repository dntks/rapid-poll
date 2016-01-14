package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultQuestion;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class PollResultQuestionTransformer {

    private final PollResultAnswerTransformer pollResultAnswerTransformer;

    public PollResultQuestionTransformer(PollResultAnswerTransformer pollResultAnswerTransformer) {
        this.pollResultAnswerTransformer = pollResultAnswerTransformer;
    }

    public List<PollResultQuestionItem> transformQuestions(List<PollResultQuestion> pollResultQuestions) {

        return Lists.transform(pollResultQuestions, new Function<PollResultQuestion, PollResultQuestionItem>() {
            @Override
            public PollResultQuestionItem apply(PollResultQuestion input) {
                return transformQuestion(input);
            }
        });
    }

    private PollResultQuestionItem transformQuestion(PollResultQuestion pollResultQuestion) {
        PollResultQuestionItem.Builder builder = PollResultQuestionItem.builder();
        builder.withAlternatives(pollResultAnswerTransformer.transformAnswers(pollResultQuestion.alternatives));
        builder.withQuestionId(pollResultQuestion.question_id);
        builder.withQuestionName(pollResultQuestion.question_name);
        return builder.build();
    }
}
