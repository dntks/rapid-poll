package com.appsball.rapidpoll.allpolls;

import com.appsball.rapidpoll.commons.model.PollState;

public class AllPollsItemData {

    public long id;
    public String name;
    public boolean isPublic;
    public boolean anonymous;
    public boolean allowComment;
    public String expirationDate;
    public String ownerId;
    public PollState state;
    public String publication_date;
    public int number_of_questions;
    public int number_of_answered_questions_by_the_user;
    public int number_of_votes;
}
