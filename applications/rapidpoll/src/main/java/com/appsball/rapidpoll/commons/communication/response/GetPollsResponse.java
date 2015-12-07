package com.appsball.rapidpoll.commons.communication.response;

import com.google.gson.annotations.SerializedName;

public class GetPollsResponse extends ResponseBase {

    public long id;
    public String name;
    @SerializedName("public")
    public int isPublic;
    public int anonymous;
    public int allow_comment;
    public String expiration_date;
    public String owner_id;
    public String state;
    public String publication_date;
    public int number_of_questions;
    public int number_of_answered_questions_by_the_user;
    public int number_of_votes;

}
