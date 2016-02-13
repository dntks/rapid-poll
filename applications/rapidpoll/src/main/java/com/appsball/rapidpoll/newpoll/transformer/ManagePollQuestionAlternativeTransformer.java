package com.appsball.rapidpoll.newpoll.transformer;

import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestionAlternative;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestionAlternative.managePollQuestionAlternative;

public class ManagePollQuestionAlternativeTransformer {
    public List<ManagePollQuestionAlternative> transformAlternatives(List<NewPollAnswer> answers) {
        return Lists.transform(answers, new Function<NewPollAnswer, ManagePollQuestionAlternative>() {
            @Override
            public ManagePollQuestionAlternative apply(NewPollAnswer input) {
                return transformAlternative(input);
            }
        });
    }

    private ManagePollQuestionAlternative transformAlternative(NewPollAnswer input) {
        return managePollQuestionAlternative(input.getAnswer());
    }
}
