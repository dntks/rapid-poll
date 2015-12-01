package com.appsball.rapidpoll.commons.communication.request.dopoll;

import com.google.common.base.Optional;

import java.util.List;

public class DoPollRequest {
    public final String user_id;
    public final String poll_id;
    public final List<DoPollQuestion> questions;
    public final String comment;

    private DoPollRequest(String user_id,
                          String poll_id,
                          List<DoPollQuestion> questions,
                          String comment) {
        this.user_id = user_id;
        this.poll_id = poll_id;
        this.questions = questions;
        this.comment = comment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String user_id;
        private String poll_id;
        private List<DoPollQuestion> questions;
        private Optional<String> comment = Optional.absent();

        public Builder withUser_id(String user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder withPoll_id(String poll_id) {
            this.poll_id = poll_id;
            return this;
        }

        public Builder withQuestions(List<DoPollQuestion> questions) {
            this.questions = questions;
            return this;
        }

        public Builder withComment(Optional<String> comment) {
            this.comment = comment;
            return this;
        }

        public DoPollRequest build() {
            return new DoPollRequest(user_id, poll_id, questions, comment.or(""));
        }
    }
}
