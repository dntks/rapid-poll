package com.appsball.rapidpoll.pollresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.fillpoll.model.FillPollDetails;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsAnswersTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsQuestionsTransformer;
import com.appsball.rapidpoll.fillpoll.transformer.PollDetailsResponseTransformer;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.appsball.rapidpoll.pollresult.model.PollResult;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;
import com.appsball.rapidpoll.pollresult.transformer.PollResultAnswerTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultQuestionTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultTransformer;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.ArrayList;
import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_TITLE;

public class PollResultFragment extends RapidPollFragment {
    public static final int POLLRESULT_LAYOUT = R.layout.pollresult_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private RapidPollRestService service;
    private PollDetailsResponseTransformer pollDetailsResponseTransformer;
    private FillPollDetails fillPollDetails;
    private RequestCreator requestCreator;
    private PollResultTransformer resultTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(POLLRESULT_LAYOUT, container, false);
        initializeList(savedInstanceState);
        String pollCode = getArguments().getString(POLL_CODE);
        String pollId = getArguments().getString(POLL_ID);
        String pollTitle = getArguments().getString(POLL_TITLE);
        getRapidPollActivity().setHomeTitle("Results " + pollTitle);
        pollDetailsResponseTransformer = new PollDetailsResponseTransformer(new PollDetailsQuestionsTransformer(new PollDetailsAnswersTransformer()));
        requestCreator = new RequestCreator();
        resultTransformer = createPollResultTransformer();
        callPollResult(requestCreator.createPollResultRequest(pollId, pollCode));

        return rootView;
    }

    private void callPollResult(PollResultRequest pollResultRequest) {
        service.pollResult(pollResultRequest, new Callback<ResponseContainer<PollResultResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<PollResultResponse> responseContainer) {
                PollResult pollResult = resultTransformer.transformPollResult(responseContainer.result);
                initializeListWithQuestions(pollResult.questions);
            }

            @Override
            public void onError(WaspError error) {

            }
        });
    }

    private void initializeListWithQuestions(List<PollResultQuestionItem> questions) {
        PollResultAdapter pollResultAdapter = new PollResultAdapter(questions, new ArrayList<CommentItem>());
        questionsList.setAdapter(pollResultAdapter);
    }

    private PollResultTransformer createPollResultTransformer() {
        return new PollResultTransformer(new PollResultQuestionTransformer(new PollResultAnswerTransformer()));
    }

    private void initializeList(Bundle savedInstanceState) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fillpoll_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_poll:
                tryToEditPoll();
                return true;
            case R.id.share_poll:
                sharePoll();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void sharePoll() {

    }

    private void tryToEditPoll() {

    }
}
