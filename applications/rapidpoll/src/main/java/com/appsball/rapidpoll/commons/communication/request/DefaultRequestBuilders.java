package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollQuestion;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderKey;
import com.appsball.rapidpoll.commons.communication.request.enums.OrderType;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePoll;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestion;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestionAlternative;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.model.PollState;
import com.google.common.base.Optional;

import java.util.List;

import static com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollAnswer.doPollAnswer;
import static com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollQuestion.doPollQuestion;
import static com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollQuestionAlternative.managePollQuestionAlternative;
import static com.appsball.rapidpoll.commons.utils.Constants.USER_ID;
import static com.google.common.collect.Lists.newArrayList;

public class DefaultRequestBuilders {


    public static ManagePollRequest createManagePollRequest() {
        ManagePollRequest.Builder managePollRequestBuilder = ManagePollRequest.builder();
        managePollRequestBuilder.withAction("CREATE");
        managePollRequestBuilder.withUserId(USER_ID);
        managePollRequestBuilder.withPoll(createPollRequestObject());
        return managePollRequestBuilder.build();
    }

    private static ManagePoll createPollRequestObject() {
        ManagePoll.Builder managePollBuilder = ManagePoll.builder();
        managePollBuilder.withAllowUncompleteAnswer("1");
        managePollBuilder.withAnonymous("0");
        managePollBuilder.withIsPublic("1");
        managePollBuilder.withName("kerdes");
        managePollBuilder.withQuestions(createManagePollQuestions());
        managePollBuilder.withAllowComment("1");
        managePollBuilder.withDraft("0");
        return managePollBuilder.build();
    }

    private static List<ManagePollQuestion> createManagePollQuestions() {
        ManagePollQuestion.Builder managePollQuestionBuilder = ManagePollQuestion.builder();
        managePollQuestionBuilder.withName("What is your favourite shop in West End?");
        managePollQuestionBuilder.withMultichoice("1");
        managePollQuestionBuilder.withAlternatives(createManagePollQuestionAlternatives1());
        ManagePollQuestion.Builder managePollQuestionBuilder2 = ManagePollQuestion.builder();
        managePollQuestionBuilder2.withName("How much do you usually spend at West End?");
        managePollQuestionBuilder2.withMultichoice("0");
        managePollQuestionBuilder2.withAlternatives(createManagePollQuestionAlternatives2());
        return newArrayList(managePollQuestionBuilder.build(), managePollQuestionBuilder2.build());
    }

    private static List<ManagePollQuestionAlternative> createManagePollQuestionAlternatives1() {
        ManagePollQuestionAlternative alternative1 = managePollQuestionAlternative("Media Markt");
        ManagePollQuestionAlternative alternative2 = managePollQuestionAlternative("Nike");
        ManagePollQuestionAlternative alternative3 = managePollQuestionAlternative("Budmil");
        return newArrayList(alternative1, alternative2, alternative3);
    }

    private static List<ManagePollQuestionAlternative> createManagePollQuestionAlternatives2() {
        ManagePollQuestionAlternative alternative1 = managePollQuestionAlternative("1-1000 HUF");
        ManagePollQuestionAlternative alternative2 = managePollQuestionAlternative("1000-5000 HUF");
        ManagePollQuestionAlternative alternative3 = managePollQuestionAlternative("5000-10000");
        ManagePollQuestionAlternative alternative4 = managePollQuestionAlternative("10000-");
        return newArrayList(alternative1, alternative2, alternative3, alternative4);
    }

    public static DoPollRequest createDoPollRequest() {
        DoPollRequest.Builder builder = DoPollRequest.builder();
        builder.withUserId(USER_ID);
        builder.withComment(Optional.of("Here is a comment."));
        builder.withPollId("1");
        builder.withEmail(Optional.of("email@oe.com"));
        builder.withCode("123456");
        builder.withQuestions(createDoPollQuestions());
        return builder.build();
    }

    private static List<DoPollQuestion> createDoPollQuestions() {
        DoPollQuestion question1 = doPollQuestion("1", newArrayList(doPollAnswer("1")));
        DoPollQuestion question2 = doPollQuestion("2", newArrayList(doPollAnswer("3")));
        DoPollQuestion question3 = doPollQuestion("3", newArrayList(doPollAnswer("7")));
        DoPollQuestion question4 = doPollQuestion("4", newArrayList(doPollAnswer("12")));
        return newArrayList(question1, question2, question3, question4);
    }

    public static UpdatePollStateRequest createUpdatePollStateRequest() {
        UpdatePollStateRequest.Builder builder = UpdatePollStateRequest.builder();
        builder.withUserId(USER_ID);
        builder.withPollId("1");
        builder.withPollState(PollState.PUBLISHED);
        return builder.build();
    }

    public static PollsRequest createAllPollsRequest() {
        PollsRequest.Builder pollsRequestBuilder = new PollsRequest.Builder();
        pollsRequestBuilder.withPageSize("25");
        pollsRequestBuilder.withPage("1");
        pollsRequestBuilder.withUserId(USER_ID);
        pollsRequestBuilder.withOrderKey(OrderKey.DATE);
        pollsRequestBuilder.withOrderType(OrderType.DESC);
        pollsRequestBuilder.withListType(ListType.ALL);
        return pollsRequestBuilder.build();
    }
}
