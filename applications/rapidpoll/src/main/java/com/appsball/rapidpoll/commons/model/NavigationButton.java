package com.appsball.rapidpoll.commons.model;

import com.appsball.rapidpoll.R;

public enum NavigationButton {
    POLLS_BUTTON(R.id.polls_button),
    RESULTS_BUTTON(R.id.results_button),
    MYPOLLS_BUTTON(R.id.my_polls_button);

    public int buttonId;
    NavigationButton(int buttonId) {
        this.buttonId = buttonId;
    }
}
