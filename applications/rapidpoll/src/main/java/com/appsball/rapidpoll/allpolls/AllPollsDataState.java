package com.appsball.rapidpoll.allpolls;

import com.appsball.rapidpoll.commons.communication.request.PollsRequest;
import com.appsball.rapidpoll.commons.communication.request.SearchPollRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.orhanobut.hawk.Hawk;

public class AllPollsDataState {
    public static final int NUMBER_OF_REQUESTED_POLLS = 10;
    public OrderType chosenOrderType = OrderType.DESC;
    public OrderKey chosenOrderKey = OrderKey.DATE;
    public int actualPage = 1;

    public PollsRequest createAllPollsRequest() {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withPage(String.valueOf(actualPage));
        builder.withOrderType(chosenOrderType);
        builder.withOrderKey(chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get("userId"));
        builder.withPageSize(String.valueOf(NUMBER_OF_REQUESTED_POLLS));
        return builder.build();
    }

    public SearchPollRequest createSearchPollRequest(String searchPhrase) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(actualPage));
        builder.withOrderType(chosenOrderType);
        builder.withOrderKey(chosenOrderKey);
        builder.withListType(ListType.ALL);
        builder.withUserId(Hawk.<String>get("userId"));
        builder.withPageSize(String.valueOf(NUMBER_OF_REQUESTED_POLLS));
        builder.withSearchItem(searchPhrase);
        return builder.build();
    }

    public void setDateSort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.DATE;
    }

    public void setTitleSort(){
        chosenOrderType = OrderType.ASC;
        chosenOrderKey = OrderKey.TITLE;
    }

    public void setStatusSort(){
        chosenOrderType = OrderType.ASC;
        chosenOrderKey = OrderKey.STATUS;
    }

    public void setVotesSort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.VOTES;
    }

    public void setPublicitySort(){
        chosenOrderType = OrderType.DESC;
        chosenOrderKey = OrderKey.PUBLIC;
    }
}
