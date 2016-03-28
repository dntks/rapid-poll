package com.appsball.rapidpoll.pollresult;

import com.appsball.rapidpoll.commons.model.ResultAlternativeDetails;

import java.util.List;

public interface PollResultQuestionItemClickListener {
    void onPollResultQuestionItemClicked(List<ResultAlternativeDetails> resultAlternativeDetailsList);
}
