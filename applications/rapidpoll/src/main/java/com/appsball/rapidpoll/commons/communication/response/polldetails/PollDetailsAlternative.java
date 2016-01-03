package com.appsball.rapidpoll.commons.communication.response.polldetails;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

public class PollDetailsAlternative extends ResponseBase {
    public long alternative_id;
    public String alternative_name;
    public String current_user_answer;
}
