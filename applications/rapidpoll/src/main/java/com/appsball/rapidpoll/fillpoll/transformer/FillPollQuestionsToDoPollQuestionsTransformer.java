package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollAnswer;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollQuestion;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class FillPollQuestionsToDoPollQuestionsTransformer {

    private final FillPollAlternativesToDoPollAnswersTransformer alternativesTransformer;

    public FillPollQuestionsToDoPollQuestionsTransformer(FillPollAlternativesToDoPollAnswersTransformer alternativesTransformer) {
        this.alternativesTransformer = alternativesTransformer;
    }

    public List<DoPollQuestion> transformQuestions(List<FillPollQuestion> question){
       return  Lists.transform(question, new Function<FillPollQuestion, DoPollQuestion>() {
            @Override
            public DoPollQuestion apply(FillPollQuestion input) {
                return transformQuestion(input);
            }
        });
    }

    private DoPollQuestion transformQuestion(FillPollQuestion input) {
        List<DoPollAnswer> answers = alternativesTransformer.transformAlternatives(Lists.newArrayList(input.getCheckedAnswers()));
        return DoPollQuestion.doPollQuestion(input.id, answers);
    }
}
