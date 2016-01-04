package com.appsball.rapidpoll.searchpolls.service;

import com.appsball.rapidpoll.commons.communication.response.PollsResponse;

import java.util.List;

public interface OnPollsReceivedListener {
    void onPollsReceived(List<PollsResponse> pollsResponses);
}
