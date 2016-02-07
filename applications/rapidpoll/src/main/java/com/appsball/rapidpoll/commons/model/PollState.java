package com.appsball.rapidpoll.commons.model;

public enum PollState {
    DRAFT(0, "Draft", "Publish"),
    CLOSED(1, "Closed", "Reopen"),
    PUBLISHED(2, "Live", "Close");

    public int value;
    public String shownName;
    public String nextStateCommand;

    PollState(int value, String shownName, String nextStateCommand) {
        this.value = value;
        this.shownName = shownName;
        this.nextStateCommand = nextStateCommand;
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

    public PollState nextState(){
        if(this == PUBLISHED){
            return CLOSED;
        }else if(this == CLOSED){
            return PUBLISHED;
        }else {
            return DRAFT;
        }
    }
}
