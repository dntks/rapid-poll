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
import com.appsball.rapidpoll.commons.communication.response.ManagePollResponse;
import com.appsball.rapidpoll.commons.communication.response.polldetails.PollDetailsResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseCallback;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.model.ManagePollActionType;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.commons.utils.Constants;
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

import java.util.List;

import static com.appsball.rapidpoll.commons.utils.Constants.PUBLIC_POLL_CODE;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
    private PollSettingsView pollSettingsView;
    private RequestCreator requestCreator;
    private String pollCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeTitle("New Poll");
        setupHomeTitleEditable();
        service = getRapidPollActivity().getRestService();

        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);
        newQuestionCreator = new NewQuestionCreator();
        requestCreator = new RequestCreator();

        pollSettings = new PollSettings();
        pollSettingsView = new PollSettingsView(pollSettings, rootView);
        pollSettingsView.initSettingsButtonListeners();

        managePollQuestionTransformer = new ManagePollQuestionTransformer(new ManagePollQuestionAlternativeTransformer());
        newPollQuestionsTransformer = new NewPollQuestionsTransformer();

        initializeList(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.getString(Constants.POLL_ID) != null) {
            loadExistingPoll(requestCreator.createPollDetailsRequest(arguments.getString(Constants.POLL_ID), PUBLIC_POLL_CODE));
        } else {
            initializeListWithNewPoll();
        }

        return rootView;
    }

    private void loadExistingPoll(final PollDetailsRequest pollDetailsRequest) {
        service.pollDetails(pollDetailsRequest, new ResponseContainerCallback<PollDetailsResponse>() {
            @Override
            public void onFailure() {
                getFragmentSwitcher().toAllPolls();
            }

            @Override
            public void onSuccess(PollDetailsResponse pollDetailsResponse) {
                pollCode = pollDetailsResponse.code;
                pollSettings.setManagePollActionType(ManagePollActionType.MODIFY);
                pollSettings.setId(String.valueOf(pollDetailsResponse.id));
                pollQuestions = newArrayList(newPollQuestionsTransformer.transformQuestions(pollDetailsResponse));
                setupAdapterWithQuestions();
                setHomeTitleName(pollDetailsResponse.name);
                pollSettings.setIsAllowedToComment(pollDetailsResponse.allow_comment == 1);
                pollSettings.setIsPublic(pollDetailsResponse.isPublic == 1);
                pollSettings.setPollState(PollState.valueOf(pollDetailsResponse.state));
                pollSettings.setIsAnonymous(pollDetailsResponse.anonymous == 1);
                pollSettings.setAcceptCompleteOnly(pollDetailsResponse.allow_uncomplete_answer == 0);
                pollSettingsView.refreshView();
                getRapidPollActivity().invalidateOptionsMenu();
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
        editableTitle.setEnabled(false);
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
        if (pollSettings.getPollState() != null) {
            menu.findItem(R.id.publish).setTitle(pollSettings.getPollState().nextStateCommand);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PollState pollState = pollSettings.getPollState();
        switch (item.getItemId()) {
            case R.id.publish:
                if (pollState == PollState.DRAFT) {
                    tryToPublishPoll(false);
                } else if (pollState == PollState.PUBLISHED || pollState == PollState.CLOSED) {
                    updatePollState(pollState.nextState());
                }
                return true;

            case android.R.id.home:
                if (pollState == PollState.DRAFT) {
                    showSaveModificationsCheckerDialog();
                    return true;
                } else {
                    return super.onOptionsItemSelected(item);
                }
            default:
                hideKeyboard();
                return super.onOptionsItemSelected(item);

        }
    }

    private void tryToPublishPoll(boolean isDraft) {
        String pollName = editableTitle.getText().toString();
        if (!isEmpty(pollName)) {
            publishPoll(pollName, isDraft);
        } else if (isAnyQuestionEmpty()) {
            showQuestionEmptyDialog();
        } else {
            showNameDialog(isDraft);
        }
    }

    private boolean isAnyQuestionEmpty() {
        for (NewPollQuestion newPollQuestion : pollQuestions) {
            if (isEmpty(newPollQuestion.getQuestion())) {
                return true;
            }
        }
        return false;
    }

    private void showSaveModificationsCheckerDialog() {
        DialogsBuilder.showErrorDialog(getActivity(), "Save modifications?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tryToPublishPoll(true);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    private void showQuestionEmptyDialog() {
        DialogsBuilder.showErrorDialog(getActivity(), "Empty question not allowed", "Please set question text for all questions!");
    }

    private void showNameDialog(final boolean isDraft) {
        DialogsBuilder.showEnterPollTitleDialog(getActivity(), "You must set Poll title!", "Poll title", new TextEnteredListener() {
            @Override
            public void textEntered(String text) {
                publishPoll(text, isDraft);
            }
        });
    }

    private void publishPoll(String name, boolean draft) {
        final ManagePoll managePoll = buildPoll(name, draft);
        service.managePoll(requestCreator.createManagePollRequest(managePoll, pollSettings.getManagePollActionType()), new ResponseContainerCallback<ManagePollResponse>() {
            @Override
            public void onSuccess(ManagePollResponse managePollResponse) {
                showSuccessDialogWithCode(managePollResponse.code);
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


    private void showSuccessDialogWithCode(int code) {
        String successWithCode = getString(R.string.successful_poll_submit);
        if (code != 0) {
            successWithCode = String.format(getString(R.string.successful_poll_submit_with_code), code);
        }
        DialogsBuilder.showErrorDialog(getActivity(), successWithCode,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManagePollFragment.this.getFragmentManager().popBackStack();
                    }
                });
    }

    private void updatePollState(final PollState toPollState) {
        service.updatePollState(requestCreator.createUpdatePollStateRequest(toPollState, pollSettings.getId()), new ResponseCallback() {
            @Override
            public void onSuccess() {
                if (toPollState == PollState.PUBLISHED) {
                    showReopenDialog();
                } else if (toPollState == PollState.CLOSED) {
                    showCloseDialog();
                }
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void showCloseDialog() {
        String closeMessage = getString(R.string.successful_close);
        if (!"NONE".equals(pollCode)) {
            closeMessage = String.format(getString(R.string.successful_close_with_code), pollCode);
        }
        DialogsBuilder.showErrorDialog(getActivity(), closeMessage, getString(R.string.share),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManagePollFragment.this.getFragmentManager().popBackStack();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManagePollFragment.this.getFragmentManager().popBackStack();
                    }
                });
    }

    private void showReopenDialog() {
        String reopenMessage = getString(R.string.successful_reopen);
        if (!"NONE".equals(pollCode)) {
            reopenMessage = String.format(getString(R.string.successful_reopen_with_code), pollCode);
        }
        DialogsBuilder.showErrorDialog(getActivity(), reopenMessage, getString(R.string.share),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManagePollFragment.this.getFragmentManager().popBackStack();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManagePollFragment.this.getFragmentManager().popBackStack();
                    }
                });
    }

    private ManagePoll buildPoll(String name, boolean draft) {
        ManagePoll.Builder builder = ManagePoll.builder();
        builder.withAllowComment(pollSettings.isAllowedToComment());
        builder.withAnonymous(pollSettings.isAnonymous());
        builder.withIsPublic(pollSettings.isPublic());
        builder.withAllowUncompleteAnswer(!pollSettings.isAcceptCompleteOnly());
        builder.withQuestions(managePollQuestionTransformer.transformPollQuestions(pollQuestions));
        builder.withName(name);
        builder.withId(pollSettings.getId());
        builder.withDraft(draft);
        return builder.build();
    }
}
