package com.appsball.rapidpoll.newpoll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.model.PollSettings;

public class PollSettingsView {

    private final PollSettings pollSettings;
    private View listSizeHelper;
    private View settingsLayout;

    public boolean isAnimating = false;
    private final View pagingView;
    private final ImageView settingsButton;
    private final View settingsButtonRow;

    public PollSettingsView(PollSettings pollSettings, View rootView) {
        this.pollSettings = pollSettings;
        pagingView = rootView.findViewById(R.id.questions_list_view);
        settingsButton = (ImageView) rootView.findViewById(R.id.settings_button);
        settingsLayout = rootView.findViewById(R.id.new_poll_settings_layout);
        listSizeHelper = rootView.findViewById(R.id.list_size_helper);
        settingsButtonRow = rootView.findViewById(R.id.poll_settings_button_row);
    }

    public void initSettingsButtonListeners() {
        settingsButtonRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSettingsVisible()) {
                    hideSettingsLayout();
                } else {
                    showSettingsLayout();
                }
            }
        });
        CheckBox allowCommentCheckBox = (CheckBox) settingsLayout.findViewById(R.id.allowcomment_checkbox);
        CheckBox anonymousCheckBox = (CheckBox) settingsLayout.findViewById(R.id.anonymous_checkbox);
        CheckBox publicCheckBox = (CheckBox) settingsLayout.findViewById(R.id.public_checkbox);

        allowCommentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pollSettings.setIsAllowedToComment(isChecked);
            }
        });
        anonymousCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pollSettings.setIsAnonymous(isChecked);
            }
        });
        publicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pollSettings.setIsPublic(isChecked);
            }
        });
    }

    private void hideSettingsLayout() {
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
                        settingsButton.setImageResource(R.drawable.lefele);
                    }
                });

    }

    private void showSettingsLayout() {
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
                        settingsButton.setImageResource(R.drawable.felfele);
                    }
                });

    }

    private boolean isSettingsVisible() {
        return settingsLayout.getVisibility() == View.VISIBLE
                && listSizeHelper.getVisibility() == View.VISIBLE;
    }
}
