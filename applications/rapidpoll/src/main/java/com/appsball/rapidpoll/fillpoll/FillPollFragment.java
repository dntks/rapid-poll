package com.appsball.rapidpoll.fillpoll;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.request.dopoll.DoPollRequest;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.commons.utils.PollSharer;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.fillpoll.adapter.FillPollAdapter;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.transformer.FillPollDetailsToDoPollRequestTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;
import com.google.common.base.Optional;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.hawk.Hawk;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import static com.appsball.rapidpoll.commons.utils.Constants.POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_TITLE;
import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showEmailInputDialog;
import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showErrorDialog;

public class FillPollFragment extends RapidPollFragment {
    public static final int FILLPOLL_LAYOUT = R.layout.fillpoll_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private RapidPollRestService service;
    private FillPollDetails fillPollDetails;
    private String pollCode;
    private PollSharer pollSharer;
    @Inject
    PollDetailsResponseTransformer pollDetailsResponseTransformer;
    @Inject
    RequestCreator requestCreator;
    @Inject
    FillPollDetailsToDoPollRequestTransformer requestTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(FILLPOLL_LAYOUT, container, false);
        initializeList(savedInstanceState);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FillPollComponent component = DaggerFillPollComponent.builder()
                .fillPollModule(new FillPollModule())
                .build();
        component.inject(this);
        pollCode = getArguments().getString(POLL_CODE);
        String pollId = getArguments().getString(POLL_ID);
        String pollTitle = getArguments().getString(POLL_TITLE);
        if (!StringUtils.isEmpty(pollTitle)) {
            getRapidPollActivity().setHomeTitle(pollTitle);
        }
        pollSharer = new PollSharer(getRapidPollActivity());
        callPollDetails(requestCreator.createPollDetailsRequest(pollId, pollCode));
    }

    private void callPollDetails(PollDetailsRequest pollDetailsRequest) {
        service.pollDetails(pollDetailsRequest, new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onFailure() {
                getFragmentSwitcher().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                getRapidPollActivity().setHomeTitle(pollDetailsResponse.name);
                pollCode = pollDetailsResponse.code;
                Hawk.put(String.valueOf(pollDetailsResponse.id), pollCode);
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
        FillPollAdapter fillPollAdapter = new FillPollAdapter(listItemCreator.createItemsFromDetails(fillPollDetails));
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
        if (!fillPollDetails.allowUncompleteResult && !fillPollDetails.areAllQuestionsAnswered()) {
            showErrorDialog(getActivity(), getString(R.string.notCompletedPoll));
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
                PollIdentifierData.Builder builder = PollIdentifierData.builder();
                builder.withPollTitle(fillPollDetails.name);
                builder.withPollId(fillPollDetails.pollId);
                builder.withPollCode(fillPollDetails.code.or("NONE"));
                inviteFriendsForPoll(builder.build());
                getFragmentSwitcher().toAllPolls();
            }
        });
    }

    private void inviteFriendsForPoll(PollIdentifierData pollIdentifierData) {
        String shareString = String.format(getString(R.string.poll_voted_invite), pollIdentifierData.pollTitle);
        pollSharer.inviteFriendsForPoll(pollIdentifierData, shareString);
    }

    private void submitPoll(String emailAddress) {
        submitPoll(requestTransformer.transform(fillPollDetails, Optional.fromNullable(pollCode), Optional.of(emailAddress)));
    }

    private void showEmailDialog() {
        showEmailInputDialog(getActivity(),
                             getString(R.string.enter_email),
                             fillPollDetails.email.or(""),
                             new TextEnteredListener() {
                                 @Override
                                 public void textEntered(String text) {
                                     Hawk.put(Constants.EMAIL_KEY, text);
                                     submitPoll(text);
                                 }
                             });
    }

}
