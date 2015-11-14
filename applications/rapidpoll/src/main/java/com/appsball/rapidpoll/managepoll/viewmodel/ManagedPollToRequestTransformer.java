package com.appsball.rapidpoll.managepoll.viewmodel;

import com.appsball.rapidpoll.commons.communication.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.PollRequestObject;

public class ManagedPollToRequestTransformer {

    public ManagePollRequest transform(ManagedPoll managedPoll){
        ManagePollRequest.Builder managePollRequestBuilder = ManagePollRequest.builder();
        managePollRequestBuilder.withUserId("");
        managePollRequestBuilder.withPoll(transformPollToRequestObject(managedPoll));
        managePollRequestBuilder.withAction("");
        return managePollRequestBuilder.build();
    }

    private PollRequestObject transformPollToRequestObject(ManagedPoll managedPoll) {
        PollRequestObject.Builder builder = PollRequestObject.builder();
        builder.withQuestions(null);
        builder.withName("");
        builder.withIsPublic("");
        builder.withAnonymous("");
        builder.withAllow_comment("");
        builder.withAllow_uncomplete_result("");
        return builder.build();
    }
}
