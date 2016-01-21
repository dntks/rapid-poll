package com.appsball.rapidpoll.commons.communication.service;

public interface ResponseCallback {
    void onSuccess();

    void onFailure();

    void onError(String errorMessage);
}
