package com.appsball.rapidpoll.commons.communication.response.pollresult;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

public class PollResultAlternative extends ResponseBase {
    public long alternative_id;
    public String alternative_name;
    public long count;
}
