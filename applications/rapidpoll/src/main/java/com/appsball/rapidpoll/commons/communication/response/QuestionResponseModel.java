package com.appsball.rapidpoll.commons.communication.response;

import java.util.List;

public class QuestionResponseModel extends ResponseBase{
    public long question_id;
    public String question;
    public int multichoice;
    public List<AlternativeResponseModel> alternatives;
}
