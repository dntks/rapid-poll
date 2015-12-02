package com.appsball.rapidpoll.commons.communication.response.pollresult;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

import java.util.List;

public class PollResultResponse extends ResponseBase {

    public String id;
    public List<PollResultQuestion> questions;
}
