package com.appsball.rapidpoll.newpoll.listadapter;

import com.appsball.rapidpoll.newpoll.model.NewPollAddAnswer;
import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;

public interface PollAnswerToAdapterAdder {
    void addAnswerToAdapter(NewPollAnswer newAnswer, NewPollAddAnswer newPollAddAnswer);
}
