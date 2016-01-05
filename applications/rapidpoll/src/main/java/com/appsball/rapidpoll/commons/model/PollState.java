package com.appsball.rapidpoll.commons.model;

public enum PollState {
    DRAFT(0),
    CLOSED(1),
    PUBLISHED(2);

    public int value;

    PollState(int value) {
        this.value = value;
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
