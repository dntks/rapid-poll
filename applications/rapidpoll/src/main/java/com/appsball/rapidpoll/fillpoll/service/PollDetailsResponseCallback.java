package com.appsball.rapidpoll.fillpoll.service;

import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;

public interface PollDetailsResponseCallback {
    void onWrongCodeGiven();

    void onSuccess(PollDetailsResponse pollDetailsResponse);

    void onError(String errorMessage);
}
