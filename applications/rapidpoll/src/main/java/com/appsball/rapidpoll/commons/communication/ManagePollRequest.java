package com.appsball.rapidpoll.commons.communication;

public class ManagePollRequest {

    public final String user_id;
    public final String action;
    public final PollRequestObject poll;

    private ManagePollRequest(String user_id, String action, PollRequestObject poll) {
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
        private PollRequestObject poll;

        public Builder withUserId(String user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder withAction(String action) {
            this.action = action;
            return this;
        }

        public Builder withPoll(PollRequestObject poll) {
            this.poll = poll;
            return this;
        }

        public ManagePollRequest build() {
            return new ManagePollRequest(user_id, action, poll);
        }
    }

}
