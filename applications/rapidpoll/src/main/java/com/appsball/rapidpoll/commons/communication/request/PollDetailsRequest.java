package com.appsball.rapidpoll.commons.communication.request;

import static org.apache.commons.lang3.Validate.notNull;

public class PollDetailsRequest {
    public final String userId;
    public final String pollId;
    public final String code;

    private PollDetailsRequest(String userId, String pollId, String code) {
        this.userId = notNull(userId, "userId must not be null");
        this.pollId = notNull(pollId, "pollId must not be null");
        this.code = notNull(code, "code must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String pollId;
        private String code;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public PollDetailsRequest build() {
            return new PollDetailsRequest(userId, pollId, code);
        }


    }
}
