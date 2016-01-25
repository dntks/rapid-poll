package com.appsball.rapidpoll.commons.communication.request.dopoll;

import com.google.common.base.Optional;

import java.util.List;

import static com.appsball.rapidpoll.RapidPollActivity.PUBLIC_POLL_CODE;
import static org.apache.commons.lang3.Validate.notNull;

public class DoPollRequest {
    public final String user_id;
    public final String poll_id;
    public final String email;
    public final String code;
    public final List<DoPollQuestion> questions;
    public final String comment;

    private DoPollRequest(String user_id,
                          String poll_id,
                          List<DoPollQuestion> questions,
                          String comment,
                          String email,
                          String code) {
        this.code = notNull(code, "code must not be null!");
        this.user_id = notNull(user_id, "user_id must not be null");
        this.poll_id = notNull(poll_id, "poll_id must not be null");
        this.questions = notNull(questions, "questions must not be null");
        this.comment = notNull(comment, "comment must not be null");
        this.email = notNull(email, "email must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String userId;
        private String pollId;
        private Optional<String> email = Optional.absent();
        private String code = PUBLIC_POLL_CODE;
        private List<DoPollQuestion> questions;
        private Optional<String> comment = Optional.absent();

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
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

        public Builder withEmail(Optional<String> email) {
            this.email = email;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public DoPollRequest build() {
            return new DoPollRequest(userId, pollId, questions, comment.or(""), email.or(""), code);
        }
    }
}
