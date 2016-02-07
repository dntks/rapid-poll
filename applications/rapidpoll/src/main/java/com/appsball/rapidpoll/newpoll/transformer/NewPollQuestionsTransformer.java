package com.appsball.rapidpoll.newpoll.transformer;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsAlternative;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsQuestion;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.google.common.base.Function;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class NewPollQuestionsTransformer {

    public List<NewPollQuestion> transformQuestions(PollDetailsResponse pollDetailsResponse) {
        return transform(pollDetailsResponse.questions, new Function<PollDetailsQuestion, NewPollQuestion>() {
            @Override
            public NewPollQuestion apply(PollDetailsQuestion input) {
                return transformQuestion(input);
            }
        });
    }

    private NewPollQuestion transformQuestion(PollDetailsQuestion input) {
        NewPollQuestion newPollQuestion = new NewPollQuestion(input.question);
        newPollQuestion.setMultichoice(input.multichoice==1);
        for(PollDetailsAlternative alternative : input.alternatives){
            NewPollAnswer newPollAnswer = new NewPollAnswer(alternative.alternative_name, newPollQuestion);
            newPollQuestion.addAnswer(newPollQuestion.getAnswers().size(), newPollAnswer);
        }
        return newPollQuestion;
    }

}
