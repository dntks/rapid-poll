package com.appsball.rapidpoll.newpoll;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePoll;
import com.appsball.rapidpoll.commons.communication.request.managepoll.ManagePollRequest;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.commons.view.RapidPollFragment;
import com.appsball.rapidpoll.commons.view.TextEnteredListener;
import com.appsball.rapidpoll.newpoll.listadapter.NewPollQuestionsAdapter;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.appsball.rapidpoll.newpoll.model.PollSettings;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionAlternativeTransformer;
import com.appsball.rapidpoll.newpoll.transformer.ManagePollQuestionTransformer;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import java.util.List;

import static com.appsball.rapidpoll.commons.view.DialogsBuilder.showEditTextDialog;
import static com.google.common.collect.Lists.newArrayList;

public class NewPollFragment extends RapidPollFragment {

    public static final int NEWPOLL_LAYOUT = R.layout.newpoll_layout;

    private UltimateRecyclerView ultimateRecyclerView;

    private View rootView;
    private RapidPollRestService service;
    private NewQuestionCreator newQuestionCreator;
    private PollSettings pollSettings;
    private List<NewPollQuestion> pollQuestions;
    private ManagePollQuestionTransformer managePollQuestionTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getRapidPollActivity().setHomeButtonVisibility(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);
        newQuestionCreator = new NewQuestionCreator();
        initializeList(savedInstanceState);
        pollSettings = new PollSettings();
        PollSettingsView pollSettingsView = new PollSettingsView(pollSettings, rootView);
        pollSettingsView.initSettingsButtonListeners();
        managePollQuestionTransformer = new ManagePollQuestionTransformer(new ManagePollQuestionAlternativeTransformer());
        return rootView;
    }


    public void initializeList(Bundle savedInstanceState) {
        ultimateRecyclerView = (UltimateRecyclerView) rootView.findViewById(R.id.questions_list_view);
        ultimateRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        NewPollQuestion question = newQuestionCreator.createNewQuestion(1);
        pollQuestions = newArrayList(question);
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
                return super.onOptionsItemSelected(item);

        }
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
        service.managePoll(createManagePollRequest(managePoll), new Callback<ResponseContainer<Object>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<Object> objectResponseContainer) {
                DialogsBuilder.showErrorDialog(getActivity(), "Success", "Poll published successfully.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewPollFragment.this.getFragmentManager().popBackStack();
                            }
                        });
            }

            @Override
            public void onError(WaspError error) {
                DialogsBuilder.showErrorDialog(getActivity(), "Failure", "Couldn't publish poll. Please try again.");
            }
        });
    }

    private ManagePollRequest createManagePollRequest(ManagePoll managePoll) {
        return ManagePollRequest.builder().withPoll(managePoll).withAction("CREATE").withUserId(Hawk.<String>get("userId")).build();
    }

    private ManagePoll buildPoll(String name, boolean draft) {
        ManagePoll.Builder builder = ManagePoll.builder();
        builder.withAllowComment(pollSettings.isAllowedToComment() ? "1" : "0");
        builder.withAnonymous(pollSettings.isAnonymous() ? "1" : "0");
        builder.withIsPublic(pollSettings.isPublic() ? "1" : "0");
        builder.withAllowUncompleteResult(pollSettings.isAcceptCompleteOnly() ? "0" : "1");
        builder.withQuestions(managePollQuestionTransformer.transformPollQuestions(pollQuestions));
        builder.withName(name);
        builder.withDraft(draft ? "1" : "0");
        return builder.build();
    }
}
