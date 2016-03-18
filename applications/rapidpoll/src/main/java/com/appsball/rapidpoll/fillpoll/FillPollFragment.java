package com.appsball.rapidpoll.fillpoll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.ScreenFragment;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.fillpoll.adapter.FillPollAdapter;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.model.FillPollQuestion;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollAlternativesToDoPollAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollDetailsToDoPollRequestTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollQuestionsToDoPollQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;
import com.google.common.base.Optional;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.appsball.rapidpoll.commons.utils.Constants.POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_TITLE;
import static com.appsball.rapidpoll.commons.utils.Utils.ON_SLASH_JOINER;
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
    private String pollCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(FILLPOLL_LAYOUT, container, false);
        initializeList(savedInstanceState);
        pollCode = getArguments().getString(POLL_CODE);
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
        service.pollDetails(pollDetailsRequest, new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onFailure() {
                getFragmentSwitcher().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                pollCode = pollDetailsResponse.code;
                fillPollDetails = pollDetailsResponseTransformer.transform(pollDetailsResponse);
                initializeListWithDetails(fillPollDetails);
            }

            @Override
            public void onError(String errorMessage) {
                getFragmentSwitcher().toAllPolls();
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
            submitPoll(requestTransformer.transformAnonymPoll(fillPollDetails, Optional.fromNullable(pollCode)));
        }
    }

    private void submitPoll(DoPollRequest doPollRequest) {
        service.doPoll(doPollRequest, new ResponseCallback() {
            @Override
            public void onSuccess() {
                showSuccessDialog();
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void showSuccessDialog() {

        DialogsBuilder.showErrorDialog(getActivity(), getString(R.string.submit_success), getString(R.string.invite), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentSwitcher().toAllPolls();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inviteFriendsForPoll();
                getFragmentSwitcher().toAllPolls();
            }
        });
    }

    private void inviteFriendsForPoll() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        PollIdentifierData pollIdentifierData = PollIdentifierData.builder().withPollTitle(fillPollDetails.name).withPollId(fillPollDetails.pollId).withPollCode(fillPollDetails.code.or("NONE")).build();
        String pollResultLink = createLinkForScreen(ScreenFragment.FILL_POLL, pollIdentifierData);
        String shareString = String.format(getString(R.string.poll_voted_invite), fillPollDetails.name, pollResultLink);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Invite to poll"));
    }

    private String createLinkForScreen(ScreenFragment screenFragment, PollIdentifierData pollIdentifierData) {
        String encodedTitle = pollIdentifierData.pollTitle;
        try {
            encodedTitle = URLEncoder.encode(pollIdentifierData.pollTitle, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ON_SLASH_JOINER.join(" rapidpoll.appsball.com",
                screenFragment.apiName,
                encodedTitle,
                pollIdentifierData.pollId,
                pollIdentifierData.pollCode);
    }

    private void submitPoll(String emailAddress) {
        submitPoll(requestTransformer.transform(fillPollDetails, Optional.fromNullable(pollCode), Optional.of(emailAddress)));
    }

    private void showEmailDialog() {
        DialogsBuilder.showEmailInputDialog(getActivity(),
                getString(R.string.enter_email),
                fillPollDetails.email.or(""),
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
