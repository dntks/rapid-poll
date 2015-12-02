package com.appsball.rapidpoll.commons.communication.request;

import com.appsball.rapidpoll.commons.communication.request.enums.PollState;

import static org.apache.commons.lang3.Validate.notNull;

public class UpdatePollStateRequest {
    public final String user_id;
    public final String poll_id;
    public final String newstate;

    private UpdatePollStateRequest(String user_id, String poll_id, String newstate) {
        this.user_id = notNull(user_id, "user_id must not be null");
        this.poll_id = notNull(poll_id, "poll_id must not be null");
        this.newstate = notNull(newstate, "newstate must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String pollId;
        private PollState newstate;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withPollState(PollState newState) {
            this.newstate = newState;
            return this;
        }

        public UpdatePollStateRequest build() {
            return new UpdatePollStateRequest(userId, pollId, newstate.name());
        }
    }
}
