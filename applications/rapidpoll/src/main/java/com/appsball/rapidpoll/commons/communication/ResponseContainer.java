package com.appsball.rapidpoll.commons.communication;

import java.util.List;

public class ResponseContainer<T> {
    T result;
    String status;
    List<String> messages;
}
