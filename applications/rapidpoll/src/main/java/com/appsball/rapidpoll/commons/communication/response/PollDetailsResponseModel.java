package com.appsball.rapidpoll.commons.communication.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PollDetailsResponseModel extends ResponseBase {
    public long id;
    public String name;

    @SerializedName("public")
    public int isPublic;
    public int anonymous;
    public int allow_comment;
    public String state;
    public String publication_time;
    public String expiration_date;
    public String owner_id;
    public List<QuestionResponseModel> questions;
}
