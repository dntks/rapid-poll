package com.appsball.rapidpoll.commons.communication.service;

public interface ResponseContainerCallback<T> {
    void onSuccess(T response);

    void onFailure();

    void onError(String errorMessage);
}
