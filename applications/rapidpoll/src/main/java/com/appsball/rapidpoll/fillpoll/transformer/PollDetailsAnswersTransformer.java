package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsAlternative;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.fillpoll.model.FillPollAlternative.fillPollAlternative;
import static java.lang.String.valueOf;

public class PollDetailsAnswersTransformer {
    public List<FillPollAlternative> transformAnswers(List<PollDetailsAlternative> alternatives) {
        return Lists.transform(alternatives, new Function<PollDetailsAlternative, FillPollAlternative>() {
            @Override
            public FillPollAlternative apply(PollDetailsAlternative input) {
                return transformAnswer(input);
            }
        });
    }

    private FillPollAlternative transformAnswer(PollDetailsAlternative input) {
        return fillPollAlternative(valueOf(input.alternative_id), input.alternative_name);
    }
}
