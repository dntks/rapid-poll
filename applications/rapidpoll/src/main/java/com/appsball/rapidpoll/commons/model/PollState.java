package com.appsball.rapidpoll.commons.model;

public enum PollState {
    DRAFT(0, "Draft"),
    CLOSED(1, "Closed"),
    PUBLISHED(2, "Live");

    public int value;
    public String shownName;

    PollState(int value, String shownName) {
        this.value = value;
        this.shownName = shownName;
    }

    public static PollState fromValue(int value){
        switch (value){
            case 1:
                return CLOSED;
            case 2:
                return PUBLISHED;
            case 0:
            default:
                return DRAFT;
        }
    }
}
