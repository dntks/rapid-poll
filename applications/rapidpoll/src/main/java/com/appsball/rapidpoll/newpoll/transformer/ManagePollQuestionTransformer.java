package com.appsball.rapidpoll.newpoll.transformer;

import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestion;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class ManagePollQuestionTransformer {

    private final ManagePollQuestionAlternativeTransformer alternativeTransformer;

    public ManagePollQuestionTransformer(ManagePollQuestionAlternativeTransformer alternativeTransformer) {
        this.alternativeTransformer = alternativeTransformer;
    }

    public List<ManagePollQuestion> transformPollQuestions(List<NewPollQuestion> questions){
        return Lists.transform(questions, new Function<NewPollQuestion, ManagePollQuestion>() {
            @Override
            public ManagePollQuestion apply(NewPollQuestion input) {
                return transformPollQuestion(input);
            }
        });
    }

    private ManagePollQuestion transformPollQuestion(NewPollQuestion input) {
        ManagePollQuestion.Builder builder = ManagePollQuestion.builder();
        builder.withName(input.getQuestion());
        builder.withId(input.getId());
        builder.withMultichoice(input.isMultichoice()?"1":"0");
        builder.withAlternatives(alternativeTransformer.transformAlternatives(input.getAnswers()));
        return builder.build();
    }
}
