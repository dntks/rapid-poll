package com.appsball.rapidpoll.commons.communication.request;

import static org.apache.commons.lang3.Validate.notNull;

public class PollResultRequest {
    public final String userId;
    public final String pollId;
    public final String pollCode;

    private PollResultRequest(String userId, String pollId, String pollCode) {
        this.pollCode = pollCode;
        this.userId = notNull(userId, "userId must not be null");
        this.pollId = notNull(pollId, "pollId must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String pollId;
        private String pollCode;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withPollCode(String pollCode) {
            this.pollCode = pollCode;
            return this;
        }

        public PollResultRequest build() {
            return new PollResultRequest(userId, pollId, pollCode);
        }
    }
}
