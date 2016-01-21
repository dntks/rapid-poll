package com.appsball.rapidpoll.searchpolls.service;

import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;

import java.util.List;

public class SearchPollsCallback implements ResponseContainerCallback<List<PollsResponse>> {

    private List<OnPollsReceivedListener> onPollsReceivedListeners;

    public SearchPollsCallback(List<OnPollsReceivedListener> onPollsReceivedListeners) {
        this.onPollsReceivedListeners = onPollsReceivedListeners;
    }

    @Override
    public void onSuccess(List<PollsResponse> responseList) {
        for(OnPollsReceivedListener listener : onPollsReceivedListeners){
            listener.onPollsReceived(responseList);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onError(String errorMessage) {
        onFailure();
    }

}

