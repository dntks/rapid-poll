package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.RapidPollActivity.USER_ID_KEY;

public class RequestCreator {

    public PollsRequest createAllPollsRequest(SearchPollsDataState searchPollsDataState) {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withPage(String.valueOf(searchPollsDataState.actualPage));
        builder.withOrderType(searchPollsDataState.chosenOrderType);
        builder.withOrderKey(searchPollsDataState.chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPageSize(String.valueOf(searchPollsDataState.numberOfRequestedPolls));
        return builder.build();
    }

    public SearchPollRequest createSearchPollRequest(String searchPhrase, SearchPollsDataState searchPollsDataState) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(searchPollsDataState.actualPage));
        builder.withOrderType(searchPollsDataState.chosenOrderType);
        builder.withOrderKey(searchPollsDataState.chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPageSize(String.valueOf(searchPollsDataState.numberOfRequestedPolls));
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
