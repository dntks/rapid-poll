package com.appsball.rapidpoll.searchpolls.listeners;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.commons.utils.Constants.PUBLIC_POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.USER_ID_KEY;
import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showEnterCodeDialog;

public abstract class CommonPollItemClickListener implements PollItemClickListener {
    protected final RapidPollActivity rapidPollActivity;
    private final RequestCreator requestCreator;
    private final RapidPollRestService service;

    public CommonPollItemClickListener(RapidPollActivity rapidPollActivity, RequestCreator requestCreator, RapidPollRestService service) {
        this.rapidPollActivity = rapidPollActivity;
        this.requestCreator = requestCreator;
        this.service = service;
    }

    public void pollItemClicked(final SearchPollsItemData searchPollsItemData) {
        if (Hawk.contains(searchPollsItemData.id)) {
            PollIdentifierData pollIdentifierData = createPollIdentifierDataForCode(searchPollsItemData, Hawk.<String>get(searchPollsItemData.id));
            onItemSuccessfullyChosen(pollIdentifierData);
        } else if (searchPollsItemData.isPublic || isOwnPoll(searchPollsItemData)) {
            PollIdentifierData pollIdentifierData = createPollIdentifierDataForCode(searchPollsItemData, PUBLIC_POLL_CODE);
            onItemSuccessfullyChosen(pollIdentifierData);
        } else {
            showEnterPollCodeDialog(searchPollsItemData.id);
        }
    }

    private PollIdentifierData createPollIdentifierDataForCode(SearchPollsItemData searchPollsItemData, String code) {
        return PollIdentifierData.builder()
                .withPollId(searchPollsItemData.id)
                .withPollCode(code)
                .withPollTitle(searchPollsItemData.name).build();
    }

    protected abstract void onItemSuccessfullyChosen(PollIdentifierData pollIdentifierData);

    private boolean isOwnPoll(SearchPollsItemData searchPollsItemData) {
        return searchPollsItemData.ownerId.equals(Hawk.<String>get(USER_ID_KEY));
    }

    private void showEnterPollCodeDialog(final String id) {
        showEnterCodeDialog(rapidPollActivity, "This is a restricted poll, please enter poll code to open", "Poll code", new TextEnteredListener() {
            @Override
            public void textEntered(String text) {
                callGetDetails(id, text);
            }
        });
    }

    private void callGetDetails(final String id, final String code) {
        service.pollDetails(requestCreator.createPollDetailsRequest(id, code), new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onFailure() {
                showEnterPollCodeDialog(id);
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                Hawk.put(id, pollDetailsResponse.code);
                PollIdentifierData pollIdentifierData = PollIdentifierData.builder()
                        .withPollId(id)
                        .withPollCode(pollDetailsResponse.code)
                        .withPollTitle(pollDetailsResponse.name).build();
                onItemSuccessfullyChosen(pollIdentifierData);
            }

            @Override
            public void onError(String errorMessage) {
                DialogsBuilder.showErrorDialog(rapidPollActivity, errorMessage);
            }
        });
    }
}
