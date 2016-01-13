package com.appsball.rapidpoll.pollresult.transformer;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultAlternative;
import com.appsball.rapidpoll.pollresult.model.PollResultAnswer;

public class PollResultAnswerTransformer {

    public PollResultAnswer transformAnswer(PollResultAlternative pollResultAlternative){
        return new PollResultAnswer();
    }
}
