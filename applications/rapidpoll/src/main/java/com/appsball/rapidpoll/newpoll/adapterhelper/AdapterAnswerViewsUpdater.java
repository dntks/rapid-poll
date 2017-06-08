package com.appsball.rapidpoll.newpoll.adapterhelper;

import com.appsball.rapidpoll.newpoll.model.NewPollAnswer;

import java.util.List;

public interface AdapterAnswerViewsUpdater {
    void updateAnswerViews(List<NewPollAnswer> answers);
}
