package com.appsball.rapidpoll.commons.communication.response.pollresult;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

import java.util.List;

public class PollResultQuestion extends ResponseBase {
    public long question_id;
    public String question_name;
    public List<PollResultAlternative> alternatives;
}
