package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsQuestion;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class PollDetailsQuestionsTransformer {
    private final PollDetailsAnswersTransformer answersTransformer;

    public PollDetailsQuestionsTransformer(PollDetailsAnswersTransformer answersTransformer) {
        this.answersTransformer = answersTransformer;
    }

    public List<FillPollQuestion> transformQuestions(List<PollDetailsQuestion> questions) {
        return Lists.transform(questions, new Function<PollDetailsQuestion, FillPollQuestion>() {
            @Override
            public FillPollQuestion apply(PollDetailsQuestion input) {
                return transformQuestion(input);
            }
        });
    }

    private FillPollQuestion transformQuestion(PollDetailsQuestion input) {
        FillPollQuestion.Builder builder = FillPollQuestion.builder();
        List<FillPollAlternative> allAnswers = answersTransformer.transformAnswers(input.alternatives);
        builder.addAllAnswers(allAnswers);
        builder.withId(String.valueOf(input.question_id));
        builder.withQuestion(input.question);
        FillPollQuestion fillPollQuestion = builder.build();
        for(FillPollAlternative alternative : allAnswers){
            alternative.setQuestion(fillPollQuestion);
        }
        return fillPollQuestion;
    }
}
