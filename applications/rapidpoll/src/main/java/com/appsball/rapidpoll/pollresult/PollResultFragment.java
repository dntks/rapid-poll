package com.appsball.rapidpoll.pollresult;

import android.content.DialogInterface;
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
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.model.ResultAlternativeDetails;
import com.appsball.rapidpoll.commons.utils.PollSharer;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.pollresult.model.PollResult;
import com.appsball.rapidpoll.pollresult.transformer.PollResultAnswerTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultCommentTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultQuestionTransformer;
import com.appsball.rapidpoll.pollresult.transformer.PollResultTransformer;
import com.google.common.primitives.Ints;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.util.List;

import static com.appsball.rapidpoll.commons.utils.Constants.POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_TITLE;
import static com.appsball.rapidpoll.commons.utils.Constants.PUBLIC_POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.USER_ID_KEY;

public class PollResultFragment extends RapidPollFragment implements PollResultQuestionItemClickListener {
    public static final int POLLRESULT_LAYOUT = R.layout.pollresult_layout;

    private UltimateRecyclerView questionsList;

    private View rootView;
    private View centeredLoadingView;
    private RapidPollRestService service;
    private RequestCreator requestCreator;
    private PollResultTransformer resultTransformer;
    private boolean isAnonymousPoll = true;
    private boolean isMyPoll = false;
    private PollIdentifierData pollIdentifierData;
    private PollSharer pollSharer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(POLLRESULT_LAYOUT, container, false);
        centeredLoadingView = rootView.findViewById(R.id.centered_loading_view);
        initializeList(savedInstanceState);
        pollSharer = new PollSharer(getRapidPollActivity());
        pollIdentifierData = PollIdentifierData.builder()
                .withPollCode(getArguments().getString(POLL_CODE))
                .withPollId(getArguments().getString(POLL_ID))
                .withPollTitle(getArguments().getString(POLL_TITLE)).build();
        getRapidPollActivity().setHomeTitle("Results " + pollIdentifierData.pollTitle);
        requestCreator = new RequestCreator();
        resultTransformer = createPollResultTransformer();
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

    private void exportPoll(final PollIdentifierData pollIdentifierData, ExportType exportType) {
        ExportPollResultRequest exportPollResultRequest = requestCreator.createExportPollResultRequest(pollIdentifierData, exportType);
        service.exportPollResult(exportPollResultRequest, new ResponseContainerCallback<File>() {
            @Override
            public void onSuccess(File file) {
                pollSharer.shareFileInIntent(file, pollIdentifierData);
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
                if (PollResultFragment.this.isDetached()) {
                    return;
                }
                isAnonymousPoll = response.anonymous == 1;
                PollResult pollResult = resultTransformer.transformPollResult(response);
                getRapidPollActivity().setHomeTitle("Results " + pollResult.pollName);
                pollIdentifierData = PollIdentifierData.builder()
                        .withPollCode(pollIdentifierData.pollCode)
                        .withPollId(pollIdentifierData.pollId)
                        .withPollTitle(pollResult.pollName).build();
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
                showExportTypeDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showExportTypeDialog() {
        DialogsBuilder.showErrorDialog(getRapidPollActivity(),
                getString(R.string.exportDialogQuestion),
                getString(R.string.exportPDF),
                getString(R.string.exportXLS),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exportPoll(pollIdentifierData, ExportType.PDF);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exportPoll(pollIdentifierData, ExportType.XLS);
                    }
                }
        );
    }

    private void sharePoll() {
        String pollCode = pollIdentifierData.pollCode;
        String shareString = String.format(getString(R.string.poll_invite_without_code), pollIdentifierData.pollTitle);
        if (!PUBLIC_POLL_CODE.equals(pollCode)) {
            shareString = String.format(getString(R.string.poll_invite), pollIdentifierData.pollTitle, pollIdentifierData.pollCode);
        }
        pollSharer.inviteFriendsForPoll(pollIdentifierData, shareString);
    }

    private void tryToEditPoll() {
        getFragmentSwitcher().toManagePoll(pollIdentifierData.pollId);
    }

    @Override
    public void onPollResultQuestionItemClicked(List<ResultAlternativeDetails> alternatives) {
        if (!isAnonymousPoll) {
            getFragmentSwitcher().toResultVotes(pollIdentifierData, alternatives);
        }
    }
}
