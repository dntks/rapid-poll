package com.appsball.rapidpoll.newpoll.model;

public class PollSettings {

    private boolean isPublic;
    private boolean isAnonymous;
    private boolean isAllowedToComment;
    private boolean acceptCompleteOnly;

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
}
