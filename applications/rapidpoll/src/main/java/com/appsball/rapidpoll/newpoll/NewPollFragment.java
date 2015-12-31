package com.appsball.rapidpoll.newpoll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.view.BottomBarNavigationFragment;
import com.appsball.rapidpoll.newpoll.listadapter.NewPollQuestionsAdapter;
import com.appsball.rapidpoll.newpoll.model.NewPollQuestion;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import static com.google.common.collect.Lists.newArrayList;

public class NewPollFragment extends BottomBarNavigationFragment {

    public static final int NEWPOLL_LAYOUT = R.layout.newpoll_layout;

    private UltimateRecyclerView ultimateRecyclerView;

    private View rootView;
    private RapidPollRestService service;
    private View listSizeHelper;
    private View settingsLayout;

    public boolean isAnimating = false;
    private NewQuestionCreator newQuestionCreator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        service = getRapidPollActivity().getRestService();
        rootView = inflater.inflate(NEWPOLL_LAYOUT, container, false);
        newQuestionCreator = new NewQuestionCreator();
        initializeList(savedInstanceState);
        settingsLayout = rootView.findViewById(R.id.new_poll_settings_layout);
        listSizeHelper = rootView.findViewById(R.id.list_size_helper);
        setSettingsButtonListener();
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

    private void setSettingsButtonListener() {
        rootView.findViewById(R.id.poll_settings_button_row).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSettingsVisible()) {
                    hideSettingsLayout();
                } else {
                    showSettingsLayout();
                }
            }
        });
    }

    private void hideSettingsLayout() {
        final View pagingView = rootView.findViewById(R.id.questions_list_view);
        if (!isSettingsVisible() || isAnimating) {
            return;
        }
        isAnimating = true;
        int height = settingsLayout.getHeight();
        listSizeHelper.setVisibility(View.GONE);
        pagingView.setTranslationY(height);
        pagingView.animate()
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        settingsLayout.setVisibility(View.INVISIBLE);
                        isAnimating = false;
                        ImageView settingsButton = (ImageView) rootView.findViewById(R.id.settings_button);
                        settingsButton.setImageResource(R.drawable.lefele);
                    }
                });

    }

    private void showSettingsLayout() {
        final View pagingView = rootView.findViewById(R.id.questions_list_view);
        if (isSettingsVisible() || isAnimating) {
            return;
        }
        isAnimating = true;
        settingsLayout.setVisibility(View.VISIBLE);
        int height = settingsLayout.getHeight();
        pagingView.animate()
                .translationY(height)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        listSizeHelper.setVisibility(View.VISIBLE);
                        pagingView.setTranslationY(0);
                        isAnimating = false;
                        final ImageView settingsButton = (ImageView) rootView.findViewById(R.id.settings_button);
                        settingsButton.setImageResource(R.drawable.felfele);
                    }
                });

    }

    private boolean isSettingsVisible() {
        return settingsLayout.getVisibility() == View.VISIBLE
                && listSizeHelper.getVisibility() == View.VISIBLE;
    }
}
