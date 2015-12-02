package com.appsball.rapidpoll.commons.communication.response;

import java.util.List;

public class ResponseContainer<T> extends ResponseBase {
    public T result;
    public String status;
    public List<String> messages;
}
