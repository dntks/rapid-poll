package com.appsball.rapidpoll.managepoll.communication;

import java.util.List;

public class PollInput {

    String name;
    boolean isPublic;
    boolean anonymous;
    boolean allow_comment;
    boolean allow_uncomplete_result;
    List<Question> questions;
}
