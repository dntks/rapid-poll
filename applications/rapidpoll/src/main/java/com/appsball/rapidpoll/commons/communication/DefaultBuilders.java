package com.appsball.rapidpoll.commons.communication;

import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.AlternativeRequestObject.alternativeRequestObject;

public class DefaultBuilders {
    public static ManagePollRequest createManagePollRequest() {
        ManagePollRequest.Builder managePollRequestBuilder = ManagePollRequest.builder();
        managePollRequestBuilder.withAction("CREATE");
        managePollRequestBuilder.withUserId("11E58A684CDE15E49E7502000029BDFD");
        managePollRequestBuilder.withPoll(createPollRequestObject());
        return managePollRequestBuilder.build();
    }

    private static PollRequestObject createPollRequestObject() {
        PollRequestObject.Builder registerRequestBuilder = PollRequestObject.builder();
        registerRequestBuilder.withAllow_uncomplete_result("1");
        registerRequestBuilder.withAnonymous("0");
        registerRequestBuilder.withIsPublic("1");
        registerRequestBuilder.withName("WestEndChristmas");
        registerRequestBuilder.withQuestions(createQuestions());
        registerRequestBuilder.withAllow_comment("1");
        return registerRequestBuilder.build();
    }

    private static List<QuestionRequestObject> createQuestions() {
        QuestionRequestObject.Builder questionRequestObjectBuilder = QuestionRequestObject.builder();
        questionRequestObjectBuilder.withName("What is your favourite shop in WestEnd?");
        questionRequestObjectBuilder.withMultichoice("1");
        questionRequestObjectBuilder.withAlternatives(createAlternatives1());
        QuestionRequestObject.Builder questionRequestObjectBuilder2 = QuestionRequestObject.builder();
        questionRequestObjectBuilder2.withName("How much do you usually spend at West End?");
        questionRequestObjectBuilder2.withMultichoice("0");
        questionRequestObjectBuilder2.withAlternatives(createAlternatives2());
        return Lists.newArrayList(questionRequestObjectBuilder.build(), questionRequestObjectBuilder2.build());
    }

    private static List<AlternativeRequestObject> createAlternatives1() {
        AlternativeRequestObject requestObject1 = alternativeRequestObject("Media Markt");
        AlternativeRequestObject requestObject2 = alternativeRequestObject("Nike");
        AlternativeRequestObject requestObject3 = alternativeRequestObject("Budmil");
        return Lists.newArrayList(requestObject1, requestObject2, requestObject3);
    }

    private static List<AlternativeRequestObject> createAlternatives2() {
        AlternativeRequestObject requestObject1 = alternativeRequestObject("1-1000HUF");
        AlternativeRequestObject requestObject2 = alternativeRequestObject("1000-5000HUF");
        AlternativeRequestObject requestObject3 = alternativeRequestObject("5000-10000");
        AlternativeRequestObject requestObject4 = alternativeRequestObject("10000-");
        return Lists.newArrayList(requestObject1, requestObject2, requestObject3, requestObject4);
    }
}
