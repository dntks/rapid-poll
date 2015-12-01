package com.appsball.rapidpoll.commons.communication.request.managepoll;

public class ManagePollRequest {

    public final String user_id;
    public final String action;
    public final ManagePoll poll;

    private ManagePollRequest(String user_id, String action, ManagePoll poll) {
        this.user_id = user_id;
        this.action = action;
        this.poll = poll;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String user_id;
        private String action;
        private ManagePoll poll;

        public Builder withUserId(String user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder withAction(String action) {
            this.action = action;
            return this;
        }

        public Builder withPoll(ManagePoll poll) {
            this.poll = poll;
            return this;
        }

        public ManagePollRequest build() {
            return new ManagePollRequest(user_id, action, poll);
        }
    }

}
