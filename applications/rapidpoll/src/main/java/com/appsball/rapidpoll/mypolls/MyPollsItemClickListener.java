package com.appsball.rapidpoll.mypolls;

import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;

public class MyPollsItemClickListener implements PollItemClickListener {
    private final RapidPollActivity rapidPollActivity;
    private final RequestCreator requestCreator;
    private final RapidPollRestService service;

    public MyPollsItemClickListener(RapidPollActivity rapidPollActivity, RequestCreator requestCreator, RapidPollRestService service) {
        this.rapidPollActivity = rapidPollActivity;
        this.requestCreator = requestCreator;
        this.service = service;

    }

    @Override
    public void pollItemClicked(SearchPollsItemData searchPollsItemData) {
        if(searchPollsItemData.state == PollState.DRAFT){
            rapidPollActivity.toManagePoll(searchPollsItemData.id);
        }else{
            rapidPollActivity.toPollResult(searchPollsItemData.id);
        }
    }
}
