package com.appsball.rapidpoll;

public enum ScreenFragment {
    MANAGE_POLL("managepoll"),
    ALL_POLLS("polls"),
    MY_POLLS("mypolls"),
    POLL_RESULT("pollresult"),
    POLL_RESULT_VOTES("pollresultvotes"),
    RESULTS("results"),
    UNKNOWN("unknown");

    public String apiName;

    ScreenFragment(String apiName) {
        this.apiName = apiName;
    }

    public static ScreenFragment fromApiName(String apiName) {
        for (ScreenFragment screenFragment : ScreenFragment.values()) {
            if (screenFragment.apiName.equals(apiName)) {
                return screenFragment;
            }
        }
        return UNKNOWN;
    }
}
