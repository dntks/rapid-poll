package com.appsball.rapidpoll.fillpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsAlternative;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.fillpoll.model.FillPollAlternative;
import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.fillpoll.model.FillPollAlternative.fillPollAlternative;
import static java.lang.String.valueOf;

public class PollDetailsAnswersTransformer {
    public List<FillPollAlternative> transformAnswers(List<PollDetailsAlternative> alternatives) {
        List<FillPollAlternative> fillPollAlternatives = Lists.newArrayList();
        for(int i=0;i<alternatives.size();i++){
            fillPollAlternatives.add(transformAnswer(alternatives.get(i), Utils.getLetterOfAlphabet(i)));
        }
        return fillPollAlternatives;
    }

    private FillPollAlternative transformAnswer(PollDetailsAlternative input, String letter) {
        return fillPollAlternative(valueOf(input.alternative_id), input.alternative_name, letter);
    }
}
