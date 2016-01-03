package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollAnswer;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollAnswer.doPollAnswer;

public class FillPollAlternativesToDoPollAnswersTransformer {

    public List<DoPollAnswer> transformAlternatives(List<FillPollAlternative> alternatives){
       return  Lists.transform(alternatives, new Function<FillPollAlternative, DoPollAnswer>() {
            @Override
            public DoPollAnswer apply(FillPollAlternative input) {
                return transformAlternative(input);
            }
        });
    }

    private DoPollAnswer transformAlternative(FillPollAlternative input) {
        return doPollAnswer(input.id);
    }
}
