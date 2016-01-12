package com.appsball.rapidpoll.commons.communication.response.polldetails;

import com.appsball.rapidpoll.commons.communication.response.ResponseBase;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PollDetailsResponse extends ResponseBase {
    public long id;
    public String name;

    @SerializedName("public")
    public int isPublic;
    public int anonymous;
    public int allow_comment;
    public String state;
    public String publication_time;
    public String expiration_date;
    public String closed_date;
    public String owner_id;
    public String code;
    public int allow_uncomplete_answer;
    public String comment;
    public String email;
    public List<PollDetailsQuestion> questions;
}
