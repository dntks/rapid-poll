package com.appsball.rapidpoll.fillpoll.model;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import static com.appsball.rapidpoll.fillpoll.model.FillPollComment.optionalFillPollComment;
import static org.apache.commons.lang3.Validate.notNull;

public class FillPollDetails {
    public final String pollId;
    public final String name;
    public final Optional<String> code;
    public final Optional<String> email;
    public final boolean isPublic;
    public final boolean isAnonymous;
    public final boolean allowComment;
    public final boolean allowUncompleteResult;
    public final Optional<FillPollComment> comment;
    public ImmutableList<FillPollQuestion> questions;

    private FillPollDetails(String pollId,
                            String name,
                            Optional<String> code,
                            Optional<String> email,
                            boolean isPublic,
                            boolean isAnonymous,
                            boolean allowComment,
                            boolean allowUncompleteResult, Optional<FillPollComment> comment,
                            ImmutableList<FillPollQuestion> questions) {
        this.code = notNull(code, "code must not be null!");
        this.email = notNull(email, "email must not be null!");
        this.allowUncompleteResult = allowUncompleteResult;
        this.pollId = notNull(pollId, "pollId must not be null!");
        this.name = notNull(name, "name must not be null!");
        this.isPublic = isPublic;
        this.isAnonymous = isAnonymous;
        this.allowComment = allowComment;
        this.comment = notNull(comment, "comment must not be null!");
        this.questions = notNull(questions, "questions must not be null!");
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String pollId;
        private String name;
        private Optional<String> code = Optional.absent();
        private Optional<String> email = Optional.absent();
        private boolean isPublic;
        private boolean isAnonymous;
        private boolean allowComment;
        private boolean allowUncompleteResult;
        private Optional<FillPollComment> comment = Optional.absent();
        private ImmutableList.Builder<FillPollQuestion> questionsBuilder = ImmutableList.builder();

        public Builder withEmail(String email) {
            this.email = Optional.fromNullable(email);
            return this;
        }

        public Builder withCode(String code) {
            this.code = Optional.fromNullable(code);
            return this;
        }

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withCommentOptional(String comment) {
            this.comment = optionalFillPollComment(comment);
            return this;
        }

        public Builder withQuestions(Iterable<FillPollQuestion> questions) {
            this.questionsBuilder.addAll(questions);
            return this;
        }

        public Builder withAllowComment(boolean allowComment) {
            this.allowComment = allowComment;
            if (allowComment && !comment.isPresent()) {
                comment = optionalFillPollComment("");
            }
            return this;
        }

        public Builder withIsAnonymous(boolean isAnonymous) {
            this.isAnonymous = isAnonymous;
            return this;
        }

        public Builder withIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Builder withAllowUncompleteResult(boolean allowUncompleteResult) {
            this.allowUncompleteResult = allowUncompleteResult;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public FillPollDetails build() {
            return new FillPollDetails(pollId, name, code, email, isPublic, isAnonymous, allowComment, allowUncompleteResult, comment, questionsBuilder.build());
        }
    }
}
