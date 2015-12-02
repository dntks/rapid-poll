package com.appsball.rapidpoll.commons.communication.request;

import static org.apache.commons.lang3.Validate.notNull;

public class PollResultRequest {
    public final String userId;
    public final String pollId;

    private PollResultRequest(String userId, String pollId) {
        this.userId = notNull(userId, "userId must not be null");
        this.pollId = notNull(pollId, "pollId must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String pollId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public PollResultRequest build() {
            return new PollResultRequest(userId, pollId);
        }
    }
}
