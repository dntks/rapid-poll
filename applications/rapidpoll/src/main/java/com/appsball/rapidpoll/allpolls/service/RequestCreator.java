package com.appsball.rapidpoll.allpolls.service;

import com.appsball.rapidpoll.allpolls.model.AllPollsDataState;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.RapidPollActivity.USER_ID_KEY;

public class RequestCreator {

    public PollsRequest createAllPollsRequest(AllPollsDataState allPollsDataState) {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withPage(String.valueOf(allPollsDataState.actualPage));
        builder.withOrderType(allPollsDataState.chosenOrderType);
        builder.withOrderKey(allPollsDataState.chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPageSize(String.valueOf(allPollsDataState.numberOfRequestedPolls));
        return builder.build();
    }

    public SearchPollRequest createSearchPollRequest(String searchPhrase, AllPollsDataState allPollsDataState) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(allPollsDataState.actualPage));
        builder.withOrderType(allPollsDataState.chosenOrderType);
        builder.withOrderKey(allPollsDataState.chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPageSize(String.valueOf(allPollsDataState.numberOfRequestedPolls));
        builder.withSearchItem(searchPhrase);
        return builder.build();
    }
    public PollDetailsRequest createPollDetailsRequest(String pollId, String pollCode) {
        PollDetailsRequest.Builder builder = PollDetailsRequest.builder();
        builder.withPollId(pollId);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withCode(pollCode);
        return builder.build();
    }
}
