package com.appsball.rapidpoll.newpoll.model;

import com.appsball.rapidpoll.commons.model.ManagePollActionType;
import com.appsball.rapidpoll.commons.model.PollState;

public class PollSettings {

    private String id = "";
    private boolean isPublic;
    private boolean isAnonymous;
    private boolean isAllowedToComment;
    private boolean acceptCompleteOnly;
    private PollState pollState = PollState.DRAFT;
    private ManagePollActionType managePollActionType = ManagePollActionType.CREATE;

    public String getId() {
        return id;
    }

    public PollSettings setId(String id) {
        this.id = id;
        return this;
    }

    public ManagePollActionType getManagePollActionType() {
        return managePollActionType;
    }

    public PollSettings setManagePollActionType(ManagePollActionType managePollActionType) {
        this.managePollActionType = managePollActionType;
        return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean isAcceptCompleteOnly() {
        return acceptCompleteOnly;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public boolean isAllowedToComment() {
        return isAllowedToComment;
    }

    public void setIsAllowedToComment(boolean isAllowedToComment) {
        this.isAllowedToComment = isAllowedToComment;
    }

    public void setAcceptCompleteOnly(boolean acceptCompleteOnly) {
        this.acceptCompleteOnly = acceptCompleteOnly;
    }

    public PollState getPollState() {
        return pollState;
    }

    public PollSettings setPollState(PollState pollState) {
        this.pollState = pollState;
        return this;
    }
}
