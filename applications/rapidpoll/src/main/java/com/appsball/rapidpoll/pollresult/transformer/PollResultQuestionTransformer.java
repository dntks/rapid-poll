package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultQuestion;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;

public class PollResultQuestionTransformer {

    public PollResultQuestionItem transformQuestion(PollResultQuestion pollResultQuestion) {


        return new PollResultQuestionItem();
    }
}
