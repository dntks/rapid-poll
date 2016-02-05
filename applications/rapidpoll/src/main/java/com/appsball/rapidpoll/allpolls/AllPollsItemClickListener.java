package com.appsball.rapidpoll.allpolls;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.searchpolls.CommonPollItemClickListener;

public class AllPollsItemClickListener extends CommonPollItemClickListener {

    public AllPollsItemClickListener(RapidPollActivity rapidPollActivity, RequestCreator requestCreator, RapidPollRestService service) {
        super(rapidPollActivity,requestCreator,service);
    }

    protected void onItemSuccessfullyChosen(PollIdentifierData pollIdentifierData) {
        rapidPollActivity.getFragmentSwitcher().toFillPoll(pollIdentifierData);
    }
}
