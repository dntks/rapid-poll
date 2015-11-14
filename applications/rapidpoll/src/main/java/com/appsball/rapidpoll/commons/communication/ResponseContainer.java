package com.appsball.rapidpoll.commons.communication;

import java.util.List;

public class ResponseContainer<T> {
   public T result;
   public String status;
   public List<String> messages;
}
