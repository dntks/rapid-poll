package com.appsball.rapidpoll.pollresult.model;

import java.util.List;

public class PollResult {

    public final String id;
    public final String ownerId;
    public final String pollName;
    public final List<PollResultQuestionItem> questions;

    public PollResult(String id, String ownerId, String pollName, List<PollResultQuestionItem> questions) {
        this.id = id;
        this.ownerId = ownerId;
        this.pollName = pollName;
        this.questions = questions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String ownerId;
        private String pollName;
        private List<PollResultQuestionItem> questions;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withOwnerId(String ownerId_id) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withPollName(String pollName) {
            this.pollName = pollName;
            return this;
        }

        public Builder withQuestions(List<PollResultQuestionItem> questions) {
            this.questions = questions;
            return this;
        }

        public PollResult build() {
            return new PollResult(id, ownerId, pollName, questions);
        }
    }

}
