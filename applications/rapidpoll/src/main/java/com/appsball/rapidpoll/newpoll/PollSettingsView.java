package com.appsball.rapidpoll.newpoll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.model.PollSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PollSettingsView {

    private boolean isAnimating = false;
    private final PollSettings pollSettings;

    @BindView(R.id.list_size_helper)
    View listSizeHelper;
    @BindView(R.id.new_poll_settings_layout)
    View settingsLayout;
    @BindView(R.id.questions_list_view)
    View pagingView;
    @BindView(R.id.settings_button)
    ImageView settingsButton;
    @BindView(R.id.poll_settings_button_row)
    View settingsButtonRow;

    @BindView(R.id.allowcomment_checkbox)
    CheckBox allowCommentCheckBox;
    @BindView(R.id.anonymous_checkbox)
    CheckBox anonymousCheckBox;
    @BindView(R.id.public_checkbox)
    CheckBox publicCheckBox;
    @BindView(R.id.acceptcompleteonly_checkbox)
    CheckBox acceptCompleteOnlyCheckBox;

    public PollSettingsView(PollSettings pollSettings, View rootView) {
        this.pollSettings = pollSettings;
        ButterKnife.bind(this, rootView);
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
        allowCommentCheckBox = (CheckBox) settingsLayout.findViewById(R.id.allowcomment_checkbox);
        anonymousCheckBox = (CheckBox) settingsLayout.findViewById(R.id.anonymous_checkbox);
        publicCheckBox = (CheckBox) settingsLayout.findViewById(R.id.public_checkbox);
        acceptCompleteOnlyCheckBox = (CheckBox) settingsLayout.findViewById(R.id.acceptcompleteonly_checkbox);

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
        acceptCompleteOnlyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pollSettings.setAcceptCompleteOnly(isChecked);
            }
        });
        refreshView();
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

    public void refreshView() {
        allowCommentCheckBox.setChecked(pollSettings.isAllowedToComment());
        anonymousCheckBox.setChecked(pollSettings.isAnonymous());
        publicCheckBox.setChecked(pollSettings.isPublic());
        acceptCompleteOnlyCheckBox.setChecked(pollSettings.isAcceptCompleteOnly());
    }
}
