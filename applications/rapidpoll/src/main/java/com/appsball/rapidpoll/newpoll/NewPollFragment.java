package com.appsball.rapidpoll.newpoll;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.appsball.rapidpoll.newpoll.listadapter.NewPollQuestionsAdapter;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.appsball.rapidpoll.newpoll.model.PollSettings;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import static com.google.common.collect.Lists.newArrayList;

public class NewPollFragment extends BottomBarNavigationFragment {

    public static final int NEWPOLL_LAYOUT = R.layout.newpoll_layout;

    private UltimateRecyclerView ultimateRecyclerView;

    private View rootView;
    private RapidPollRestService service;
    private NewQuestionCreator newQuestionCreator;
    private PollSettings pollSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);
        newQuestionCreator = new NewQuestionCreator();
        initializeList(savedInstanceState);
        pollSettings = new PollSettings();
        PollSettingsView pollSettingsView = new PollSettingsView(pollSettings, rootView);
        pollSettingsView.initSettingsButtonListeners();
        return rootView;
    }


    public void initializeList(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        ultimateRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        NewPollQuestion question = newQuestionCreator.createNewQuestion(1);
        NewPollQuestionsAdapter newPollAdapter = new NewPollQuestionsAdapter(newArrayList(question), newQuestionCreator);
        ultimateRecyclerView.setAdapter(newPollAdapter);
    }



}
