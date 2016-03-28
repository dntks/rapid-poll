package com.appsball.rapidpoll.pollresultvotes;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.model.ResultAlternativeDetails;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.pollresultvotes.model.ResultVotesListItem;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

public class ResultVotesFragment extends RapidPollFragment {
    public static final int POLLRESULT_LAYOUT = R.layout.result_votes_layout;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(POLLRESULT_LAYOUT, container, false);
        List<ResultAlternativeDetails> resultAnswerList = getArguments().getParcelableArrayList(Constants.RESULT_ANSWERS);
        PollIdentifierData pollIdentifierData = getArguments().getParcelable(Constants.POLL_ID_DATA);
        getRapidPollActivity().setHomeTitle(pollIdentifierData.pollTitle);
        ResultVoteListItemCreator resultVoteListItemCreator = new ResultVoteListItemCreator();
        initializeList(resultVoteListItemCreator.createResultVoteListItems(resultAnswerList));

        return rootView;
    }

    private void initializeList(List<ResultVotesListItem> resultVotesListItems) {

        UltimateRecyclerView questionsList = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        questionsList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        questionsList.setLayoutManager(linearLayoutManager);

        questionsList.setAdapter(new ResultVotesAdapter(resultVotesListItems));
    }
}
