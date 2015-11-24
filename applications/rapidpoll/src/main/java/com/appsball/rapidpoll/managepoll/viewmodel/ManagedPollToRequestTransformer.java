package com.appsball.rapidpoll.managepoll.viewmodel;

import com.appsball.rapidpoll.commons.communication.request.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.request.PollRequestModel;

public class ManagedPollToRequestTransformer {

    public ManagePollRequest transform(ManagedPoll managedPoll){
        ManagePollRequest.Builder managePollRequestBuilder = ManagePollRequest.builder();
        managePollRequestBuilder.withUserId("");
        managePollRequestBuilder.withPoll(transformPollToRequestObject(managedPoll));
        managePollRequestBuilder.withAction("");
        return managePollRequestBuilder.build();
    }

    private PollRequestModel transformPollToRequestObject(ManagedPoll managedPoll) {
        PollRequestModel.Builder builder = PollRequestModel.builder();
        builder.withQuestions(null);
        builder.withName("");
        builder.withIsPublic("");
        builder.withAnonymous("");
        builder.withAllow_comment("");
        builder.withAllow_uncomplete_result("");
        return builder.build();
    }
}
