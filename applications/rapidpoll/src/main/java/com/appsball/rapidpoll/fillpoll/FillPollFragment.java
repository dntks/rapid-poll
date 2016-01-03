package com.appsball.rapidpoll.fillpoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.service.PollDetailsResponseCallback;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;
import com.appsball.rapidpoll.newpoll.NewQuestionCreator;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;

public class FillPollFragment extends RapidPollFragment {
    public static final int FILLPOLL_LAYOUT = R.layout.fillpoll_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private RapidPollRestService service;
    private NewQuestionCreator newQuestionCreator;
    private List<NewPollQuestion> pollQuestions;
    private PollDetailsResponseTransformer pollDetailsResponseTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeButtonVisibility(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(FILLPOLL_LAYOUT, container, false);
        initializeList(savedInstanceState);
        String pollCode = getArguments().getString(POLL_CODE);
        String pollId = getArguments().getString(POLL_ID);
        pollDetailsResponseTransformer = new PollDetailsResponseTransformer(new PollDetailsQuestionsTransformer(new PollDetailsAnswersTransformer()));
        RequestCreator requestCreator = new RequestCreator();
        callPollDetails(requestCreator.createPollDetailsRequest(pollId, pollCode));

        return rootView;
    }


    private void callPollDetails(PollDetailsRequest pollDetailsRequest) {
        service.pollDetails(pollDetailsRequest, new PollDetailsResponseCallback() {
            @Override
            public void onWrongCodeGiven() {
                getRapidPollActivity().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                FillPollDetails fillPollDetails = pollDetailsResponseTransformer.transform(pollDetailsResponse);
                initializeListWithDetails(fillPollDetails);
            }

            @Override
            public void onError(String errorMessage) {
                getRapidPollActivity().toAllPolls();
            }
        });
    }

    private void initializeListWithDetails(FillPollDetails fillPollDetails) {

    }


    private void initializeList(Bundle savedInstanceState) {
        questionsList = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
    }
}
