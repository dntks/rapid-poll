package com.appsball.rapidpoll.newpoll.adapterhelper;

import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;

public interface QuestionItemRemover {
    void removeQuestion(NewPollQuestion newPollQuestion);
    boolean isOnlyQuestionRemaining(NewPollQuestion newPollQuestion);
}
