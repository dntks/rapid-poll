package com.appsball.rapidpoll.fillpoll;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.fillpoll.adapter.FillPollAdapter;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.appsball.rapidpoll.fillpoll.service.PollDetailsResponseCallback;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollAlternativesToDoPollAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollDetailsToDoPollRequestTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollQuestionsToDoPollQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_TITLE;
import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showErrorDialog;

public class FillPollFragment extends RapidPollFragment {
    public static final int FILLPOLL_LAYOUT = R.layout.fillpoll_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private RapidPollRestService service;
    private PollDetailsResponseTransformer pollDetailsResponseTransformer;
    private FillPollDetails fillPollDetails;
    private RequestCreator requestCreator;
    private FillPollDetailsToDoPollRequestTransformer requestTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(FILLPOLL_LAYOUT, container, false);
        initializeList(savedInstanceState);
        String pollCode = getArguments().getString(POLL_CODE);
        String pollId = getArguments().getString(POLL_ID);
        String pollTitle = getArguments().getString(POLL_TITLE);
        getRapidPollActivity().setHomeTitle(pollTitle);
        pollDetailsResponseTransformer = new PollDetailsResponseTransformer(new PollDetailsQuestionsTransformer(new PollDetailsAnswersTransformer()));
        requestCreator = new RequestCreator();
        requestTransformer = createDoPollRequestTransformer();
        callPollDetails(requestCreator.createPollDetailsRequest(pollId, pollCode));

        return rootView;
    }

    private FillPollDetailsToDoPollRequestTransformer createDoPollRequestTransformer() {
        return new FillPollDetailsToDoPollRequestTransformer(new FillPollQuestionsToDoPollQuestionsTransformer(new FillPollAlternativesToDoPollAnswersTransformer()));
    }

    private void callPollDetails(PollDetailsRequest pollDetailsRequest) {
        service.pollDetails(pollDetailsRequest, new PollDetailsResponseCallback() {
            @Override
            public void onWrongCodeGiven() {
                getRapidPollActivity().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                fillPollDetails = pollDetailsResponseTransformer.transform(pollDetailsResponse);
                initializeListWithDetails(fillPollDetails);
            }

            @Override
            public void onError(String errorMessage) {
                getRapidPollActivity().toAllPolls();
            }
        });
    }

    private void initializeListWithDetails(FillPollDetails fillPollDetails) {
        FillPollListItemCreator listItemCreator = new FillPollListItemCreator();
        FillPollAdapter fillPollAdapter = new FillPollAdapter(fillPollDetails, listItemCreator.createItemsFromDetails(fillPollDetails));
        questionsList.setAdapter(fillPollAdapter);
    }


    private void initializeList(Bundle savedInstanceState) {
        questionsList = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        questionsList.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        questionsList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fillpoll_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit:
                tryToSubmitPoll();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void tryToSubmitPoll() {
        if (!fillPollDetails.allowUncompleteResult && !areAllQuestionsAnswered()) {
            showNotCompletePollDialog();
        } else if (!fillPollDetails.isAnonymous) {
            showEmailDialog();
        } else {
            submitPoll();
        }
    }

    private void submitPoll() {
        DoPollRequest doPollRequest = requestTransformer.transform(fillPollDetails);
        service.doPoll(doPollRequest, new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {
                if(RapidPollRestService.SUCCESS_MESSAGE.equals(objectResponseContainer.status)){
                    showSuccessDialog();
                }
            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    private void showSuccessDialog() {

        DialogsBuilder.showErrorDialog(getActivity(), getString(R.string.submit_success), getString(R.string.invite), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getRapidPollActivity().toAllPolls();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inviteFriendsForPoll();
                getRapidPollActivity().toAllPolls();
            }
        });
    }

    private void inviteFriendsForPoll() {

    }


    private void submitPoll(String emailAddress) {
        submitPoll();
    }

    private void showEmailDialog() {
        DialogsBuilder.showEditTextDialog(getActivity(),
                getString(R.string.enter_email),
                getString(R.string.email_hint),
                new TextEnteredListener() {
                    @Override
                    public void textEntered(String text) {
                        submitPoll(text);
                    }
                });
    }

    private void showNotCompletePollDialog() {
        showErrorDialog(getActivity(), getString(R.string.notCompletedPoll));
    }

    private boolean areAllQuestionsAnswered() {
        boolean anyQuestionUnanswered = false;
        for (FillPollQuestion question : fillPollDetails.questions) {
            anyQuestionUnanswered = !question.getCheckedAnswers().isEmpty();
        }
        return anyQuestionUnanswered;
    }
}