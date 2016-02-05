package com.appsball.rapidpoll.pollresult;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.ExportPollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.ExportType;
import com.appsball.rapidpoll.commons.communication.request.PollResultRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.pollresult.model.PollResult;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;
import com.appsball.rapidpoll.pollresult.transformer.PollResultAnswerTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultCommentTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultQuestionTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultTransformer;
import com.google.common.primitives.Ints;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_TITLE;
import static com.appsball.rapidpoll.RapidPollActivity.PUBLIC_POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.USER_ID_KEY;

public class PollResultFragment extends RapidPollFragment implements PollResultQuestionItemClickListener {
    public static final int POLLRESULT_LAYOUT = R.layout.pollresult_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private View centeredLoadingView;
    private RapidPollRestService service;
    private RequestCreator requestCreator;
    private PollResultTransformer resultTransformer;
    private boolean isAnonymousPoll;
    private boolean isMyPoll = false;
    private PollIdentifierData pollIdentifierData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(POLLRESULT_LAYOUT, container, false);
        centeredLoadingView = rootView.findViewById(R.id.centered_loading_view);
        initializeList(savedInstanceState);
        pollIdentifierData = PollIdentifierData.builder()
                .withPollCode(getArguments().getString(POLL_CODE))
                .withPollId(getArguments().getString(POLL_ID))
                .withPollTitle(getArguments().getString(POLL_TITLE)).build();
        getRapidPollActivity().setHomeTitle("Results " + pollIdentifierData.pollTitle);
        requestCreator = new RequestCreator();
        resultTransformer = createPollResultTransformer();
        isAnonymousPoll = pollIdentifierData.pollCode.equals(PUBLIC_POLL_CODE);
        callPollResult(requestCreator.createPollResultRequest(pollIdentifierData));

        return rootView;
    }

    private void setupPollShareView() {
        View pollShareView = rootView.findViewById(R.id.poll_share_row);
        pollShareView.setVisibility(isMyPoll ? View.VISIBLE : View.GONE);
        TextView pollCodeView = (TextView) rootView.findViewById(R.id.pollcode_value);
        pollCodeView.setText(pollIdentifierData.pollCode);
        pollShareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePoll();
            }
        });
    }

    private void exportPoll(PollIdentifierData pollIdentifierData) {
        ExportPollResultRequest exportPollResultRequest = requestCreator.createExportPollResultRequest(pollIdentifierData, ExportType.PDF);
        service.exportPollResult(exportPollResultRequest, new ResponseCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void callPollResult(PollResultRequest pollResultRequest) {
        service.pollResult(pollResultRequest, new ResponseContainerCallback<PollResultResponse>() {
            @Override
            public void onSuccess(PollResultResponse response) {
                PollResult pollResult = resultTransformer.transformPollResult(response);
                isMyPoll = pollResult.ownerId.equals(Hawk.<String>get(USER_ID_KEY));
                initializeListWithQuestions(pollResult);
                getRapidPollActivity().invalidateOptionsMenu();
                centeredLoadingView.setVisibility(View.GONE);
                setupPollShareView();
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onError(String errorMessage) {

            }

        });
    }

    private void initializeListWithQuestions(PollResult pollResult) {
        PollResultQuestionAdapter pollResultAdapter = new PollResultQuestionAdapter(pollResult, getAnswerColors(), isAnonymousPoll, this);
        questionsList.setAdapter(pollResultAdapter);
    }

    private List<Integer> getAnswerColors() {
        int[] colorArray = getContext().getResources().getIntArray(R.array.pie_colors);
        return Ints.asList(colorArray);
    }

    private PollResultTransformer createPollResultTransformer() {
        return new PollResultTransformer(new PollResultQuestionTransformer(new PollResultAnswerTransformer()), new PollResultCommentTransformer());
    }

    private void initializeList(Bundle savedInstanceState) {
        questionsList = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        questionsList.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        questionsList.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pollresult_menu, menu);

        menu.findItem(R.id.edit_poll).setVisible(isMyPoll);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_poll:
                tryToEditPoll();
                return true;
            case R.id.share_poll:
                sharePoll();
                exportPoll(pollIdentifierData);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void sharePoll() {

    }

    private void tryToEditPoll() {

    }

    @Override
    public void onPollResultQuestionItemClicked(PollResultQuestionItem pollResultQuestionItem) {
    }
}
