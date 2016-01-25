package com.appsball.rapidpoll.commons.communication.response.pollresult;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;

import java.util.List;

public class PollResultResponse extends ResponseBase {

    public String id;
    public String owner_id;
    public String poll_name;
    public int anonymous;
    public String closed_date;
    public List<PollResultQuestion> questions;
    public List<String> emails;
    public List<String> comments;
}
