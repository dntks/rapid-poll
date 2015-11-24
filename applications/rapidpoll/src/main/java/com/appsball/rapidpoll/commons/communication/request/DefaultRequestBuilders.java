package com.appsball.rapidpoll.commons.communication.request;

import com.google.common.collect.Lists;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.AlternativeRequestModel.alternativeRequestObject;

public class DefaultRequestBuilders {

    public static ManagePollRequest createManagePollRequest() {
        ManagePollRequest.Builder managePollRequestBuilder = ManagePollRequest.builder();
        managePollRequestBuilder.withAction("CREATE");
        managePollRequestBuilder.withUserId("11E592F746A409999E7502000029BDFD");
        managePollRequestBuilder.withPoll(createPollRequestObject());
        return managePollRequestBuilder.build();
    }

    private static PollRequestModel createPollRequestObject() {
        PollRequestModel.Builder registerRequestBuilder = PollRequestModel.builder();
        registerRequestBuilder.withAllow_uncomplete_result("1");
        registerRequestBuilder.withAnonymous("0");
        registerRequestBuilder.withIsPublic("1");
        registerRequestBuilder.withName("WestEndChristmas");
        registerRequestBuilder.withQuestions(createQuestions());
        registerRequestBuilder.withAllow_comment("1");
        return registerRequestBuilder.build();
    }

    private static List<QuestionRequestModel> createQuestions() {
        QuestionRequestModel.Builder questionRequestObjectBuilder = QuestionRequestModel.builder();
        questionRequestObjectBuilder.withName("What is your favourite shop in West End?");
        questionRequestObjectBuilder.withMultichoice("1");
        questionRequestObjectBuilder.withAlternatives(createAlternatives1());
        QuestionRequestModel.Builder questionRequestObjectBuilder2 = QuestionRequestModel.builder();
        questionRequestObjectBuilder2.withName("How much do you usually spend at West End?");
        questionRequestObjectBuilder2.withMultichoice("0");
        questionRequestObjectBuilder2.withAlternatives(createAlternatives2());
        return Lists.newArrayList(questionRequestObjectBuilder.build(), questionRequestObjectBuilder2.build());
    }

    private static List<AlternativeRequestModel> createAlternatives1() {
        AlternativeRequestModel requestObject1 = alternativeRequestObject("Media Markt");
        AlternativeRequestModel requestObject2 = alternativeRequestObject("Nike");
        AlternativeRequestModel requestObject3 = alternativeRequestObject("Budmil");
        return Lists.newArrayList(requestObject1, requestObject2, requestObject3);
    }

    private static List<AlternativeRequestModel> createAlternatives2() {
        AlternativeRequestModel requestObject1 = alternativeRequestObject("1-1000 HUF");
        AlternativeRequestModel requestObject2 = alternativeRequestObject("1000-5000 HUF");
        AlternativeRequestModel requestObject3 = alternativeRequestObject("5000-10000");
        AlternativeRequestModel requestObject4 = alternativeRequestObject("10000-");
        return Lists.newArrayList(requestObject1, requestObject2, requestObject3, requestObject4);
    }
}
