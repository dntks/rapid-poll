package com.appsball.rapidpoll.allpolls.service;

import com.appsball.rapidpoll.commons.communication.response.PollsResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

public class SearchPollsCallback implements Callback<ResponseContainer<List<PollsResponse>>> {

    private List<OnPollsReceivedListener> onPollsReceivedListeners;

    public SearchPollsCallback(List<OnPollsReceivedListener> onPollsReceivedListeners) {
        this.onPollsReceivedListeners = onPollsReceivedListeners;
    }

    @Override
    public void onSuccess(Response response, ResponseContainer<List<PollsResponse>> listResponseContainer) {
        for(OnPollsReceivedListener listener : onPollsReceivedListeners){
            listener.onPollsReceived(listResponseContainer.result);
        }
    }

    @Override
    public void onError(WaspError error) {

    }
}

