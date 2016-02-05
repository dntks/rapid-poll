package com.appsball.rapidpoll.newpoll;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.PollDetailsRequest;
import com.appsball.rapidpoll.commons.communication.request.RequestCreator;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePoll;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.ManagePollResponse;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.newpoll.listadapter.NewPollQuestionsAdapter;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.appsball.rapidpoll.newpoll.model.PollSettings;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionAlternativeTransformer;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionTransformer;
import com.appsball.rapidpoll.newpoll.transformer.NewPollQuestionsTransformer;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.PUBLIC_POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.USER_ID_KEY;
import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showEditTextDialog;
import static com.google.common.collect.Lists.newArrayList;

public class ManagePollFragment extends RapidPollFragment {

    public static final int NEWPOLL_LAYOUT = R.layout.newpoll_layout;

    private UltimateRecyclerView ultimateRecyclerView;

    private View rootView;
    private RapidPollRestService service;
    private NewQuestionCreator newQuestionCreator;
    private PollSettings pollSettings;
    private List<NewPollQuestion> pollQuestions;
    private ManagePollQuestionTransformer managePollQuestionTransformer;
    private EditText editableTitle;
    private NewPollQuestionsTransformer newPollQuestionsTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeTitle("New Poll");
        setupHomeTitleEditable();
        service = getRapidPollActivity().getRestService();

        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);
        newQuestionCreator = new NewQuestionCreator();
        RequestCreator requestCreator = new RequestCreator();

        pollSettings = new PollSettings();
        PollSettingsView pollSettingsView = new PollSettingsView(pollSettings, rootView);
        pollSettingsView.initSettingsButtonListeners();

        managePollQuestionTransformer = new ManagePollQuestionTransformer(new ManagePollQuestionAlternativeTransformer());
        newPollQuestionsTransformer = new NewPollQuestionsTransformer();

        initializeList(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.getString(POLL_ID) != null) {
            loadExistingPoll(requestCreator.createPollDetailsRequest(arguments.getString(POLL_ID), PUBLIC_POLL_CODE));
        } else {
            initializeListWithNewPoll();
        }

        return rootView;
    }

    private void loadExistingPoll(PollDetailsRequest pollDetailsRequest) {
        service.pollDetails(pollDetailsRequest, new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onFailure() {
                getFragmentSwitcher().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                pollQuestions = newPollQuestionsTransformer.transformQuestions(pollDetailsResponse);
                setupAdapterWithQuestions();
                setHomeTitleName(pollDetailsResponse.name);
                pollSettings.setIsAllowedToComment(pollDetailsResponse.allow_comment == 1);
                pollSettings.setIsPublic(pollDetailsResponse.isPublic == 1);
                pollSettings.setIsAnonymous(pollDetailsResponse.anonymous == 1);
                pollSettings.setAcceptCompleteOnly(pollDetailsResponse.allow_uncomplete_answer == 0);
            }

            @Override
            public void onError(String errorMessage) {
                getFragmentSwitcher().toAllPolls();
            }
        });
    }

    private void setupHomeTitleEditable() {
        editableTitle = getRapidPollActivity().getEditableTitle();
        editableTitle.setHint("New Poll");
        editableTitle.setText("");
        editableTitle.setVisibility(View.VISIBLE);
    }

    private void setHomeTitleName(String existingName) {
        editableTitle.setText(existingName);
    }


    public void initializeList(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        ultimateRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initializeListWithNewPoll() {
        NewPollQuestion question = newQuestionCreator.createNewQuestion(1);
        pollQuestions = newArrayList(question);
        setupAdapterWithQuestions();
    }

    private void setupAdapterWithQuestions() {
        NewPollQuestionsAdapter newPollAdapter = new NewPollQuestionsAdapter(pollQuestions, newQuestionCreator);
        ultimateRecyclerView.setAdapter(newPollAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newpoll_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publish:
                showNameDialog();
                return true;
            default:
                hideKeyboard();
                return super.onOptionsItemSelected(item);

        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }


    private void showNameDialog() {
        showEditTextDialog(getActivity(), "You must set Poll title!", "Poll title", new TextEnteredListener() {
            @Override
            public void textEntered(String text) {
                publishPoll(text, false);
            }
        });
    }

    private void publishPoll(String name, boolean draft) {
        ManagePoll managePoll = buildPoll(name, draft);
        service.managePoll(createManagePollRequest(managePoll), new ResponseContainerCallback<ManagePollResponse>() {
            @Override
            public void onSuccess(ManagePollResponse managePollResponse) {
                DialogsBuilder.showErrorDialog(getActivity(), "Success", "Poll published successfully.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ManagePollFragment.this.getFragmentManager().popBackStack();
                            }
                        });
            }

            @Override
            public void onFailure() {
                DialogsBuilder.showErrorDialog(getActivity(), "Failure", "Couldn't publish poll. Please try again.");
            }

            @Override
            public void onError(String errorMessage) {
                onFailure();
            }

        });
    }

    private ManagePollRequest createManagePollRequest(ManagePoll managePoll) {
        return ManagePollRequest.builder().withPoll(managePoll).withAction("CREATE").withUserId(Hawk.<String>get(USER_ID_KEY)).build();
    }

    private ManagePoll buildPoll(String name, boolean draft) {
        ManagePoll.Builder builder = ManagePoll.builder();
        builder.withAllowComment(pollSettings.isAllowedToComment() ? "1" : "0");
        builder.withAnonymous(pollSettings.isAnonymous() ? "1" : "0");
        builder.withIsPublic(pollSettings.isPublic() ? "1" : "0");
        builder.withAllowUncompleteAnswer(pollSettings.isAcceptCompleteOnly() ? "0" : "1");
        builder.withQuestions(managePollQuestionTransformer.transformPollQuestions(pollQuestions));
        builder.withName(name);
        builder.withDraft(draft ? "1" : "0");
        return builder.build();
    }
}
