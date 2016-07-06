package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePoll;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.model.ManagePollActionType;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsDataState;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.commons.utils.Constants.USER_ID_KEY;

public class RequestCreator {

    public PollsRequest createAllPollsRequest(SearchPollsDataState searchPollsDataState, ListType listType) {
        PollsRequest.Builder builder = PollsRequest.builder();
        builder.withPage(String.valueOf(searchPollsDataState.actualPage));
        builder.withOrderType(searchPollsDataState.chosenSortType.orderType);
        builder.withOrderKey(searchPollsDataState.chosenSortType.orderKey);
        builder.withListType(listType);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPageSize(String.valueOf(searchPollsDataState.numberOfRequestedPolls));
        return builder.build();
    }

    public SearchPollRequest createSearchPollRequest(String searchPhrase, SearchPollsDataState searchPollsDataState, ListType listType) {
        SearchPollRequest.Builder builder = SearchPollRequest.builder();
        builder.withPage(String.valueOf(searchPollsDataState.actualPage));
        builder.withOrderType(searchPollsDataState.chosenSortType.orderType);
        builder.withOrderKey(searchPollsDataState.chosenSortType.orderKey);
        builder.withListType(listType);
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

    public UpdatePollStateRequest createUpdatePollStateRequest(String pollId, PollState pollState) {
        UpdatePollStateRequest.Builder builder = UpdatePollStateRequest.builder();
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPollId(pollId);
        builder.withPollState(pollState);
        return builder.build();
    }

    public PollResultRequest createPollResultRequest(PollIdentifierData pollIdentifierData) {
        PollResultRequest.Builder builder = PollResultRequest.builder();
        builder.withPollId(pollIdentifierData.pollId);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPollCode(pollIdentifierData.pollCode);
        return builder.build();
    }

    public ExportPollResultRequest createExportPollResultRequest(PollIdentifierData pollIdentifierData, ExportType exportType) {
        ExportPollResultRequest.Builder builder = ExportPollResultRequest.builder();
        builder.withPollId(pollIdentifierData.pollId);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withCode(pollIdentifierData.pollCode);
        builder.withExportType(exportType);
        return builder.build();
    }

    public UpdatePollStateRequest createUpdatePollStateRequest(PollState pollState, String pollId) {
        UpdatePollStateRequest.Builder builder = UpdatePollStateRequest.builder();
        builder.withPollId(pollId);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        builder.withPollState(pollState);
        return builder.build();
    }

    public ManagePollRequest createManagePollRequest(ManagePoll managePoll, ManagePollActionType managePollActionType) {
        ManagePollRequest.Builder builder = ManagePollRequest.builder();
        builder.withPoll(managePoll);
        builder.withAction(managePollActionType);
        builder.withUserId(Hawk.<String>get(USER_ID_KEY));
        return builder.build();
    }
}
