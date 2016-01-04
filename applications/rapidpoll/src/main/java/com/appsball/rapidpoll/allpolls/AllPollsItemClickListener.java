package com.appsball.rapidpoll.allpolls;

import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.fillpoll.service.PollDetailsResponseCallback;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;

import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showEditTextDialog;

public class AllPollsItemClickListener implements PollItemClickListener{
    private final RapidPollActivity rapidPollActivity;
    private final RequestCreator requestCreator;
    private final RapidPollRestService service;
    public static final String PUBLIC_POLL_CODE = "NONE";

    public AllPollsItemClickListener(RapidPollActivity rapidPollActivity, RequestCreator requestCreator, RapidPollRestService service) {
        this.rapidPollActivity = rapidPollActivity;
        this.requestCreator = requestCreator;
        this.service = service;
    }

    @Override
    public void pollItemClicked(final SearchPollsItemData searchPollsItemData) {
        if (searchPollsItemData.isPublic) {
            rapidPollActivity.toFillPoll(searchPollsItemData.id, PUBLIC_POLL_CODE);
        } else {
            showEnterPollCodeDialog(searchPollsItemData.id);
        }
    }

    private void showEnterPollCodeDialog(final String id) {
        showEditTextDialog(rapidPollActivity, "This is a restricted poll, please enter poll code to open", "Poll code", new TextEnteredListener() {
            @Override
            public void textEntered(String text) {
                callGetDetails(id, text);
            }
        });
    }

    private void callGetDetails(final String id, final String code) {
        service.pollDetails(requestCreator.createPollDetailsRequest(id, code), new PollDetailsResponseCallback() {
            @Override
            public void onWrongCodeGiven() {
                showEnterPollCodeDialog(id);
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                rapidPollActivity.toFillPoll(id, code);
            }

            @Override
            public void onError(String errorMessage) {
                DialogsBuilder.showErrorDialog(rapidPollActivity, errorMessage);
            }
        });
    }
}
