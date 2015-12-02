package com.appsball.rapidpoll.commons.communication.response.polldetails;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

import java.util.List;

public class PollDetailsQuestion extends ResponseBase {
    public long question_id;
    public String question;
    public int multichoice;
    public List<PollDetailsAlternative> alternatives;
}
