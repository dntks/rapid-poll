package com.appsball.rapidpoll;

public class PollIdentifierData {
    public final String pollId;
    public final String pollCode;
    public final String pollTitle;

    private PollIdentifierData(String pollId, String pollCode, String pollTitle) {
        this.pollId = pollId;
        this.pollCode = pollCode;
        this.pollTitle = pollTitle;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String pollId;
        private String pollCode;
        private String pollTitle;

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withPollCode(String pollCode) {
            this.pollCode = pollCode;
            return this;
        }

        public Builder withPollTitle(String pollTitle) {
            this.pollTitle = pollTitle;
            return this;
        }

        public PollIdentifierData build() {
            return new PollIdentifierData(pollId, pollCode, pollTitle);
        }
    }
}

