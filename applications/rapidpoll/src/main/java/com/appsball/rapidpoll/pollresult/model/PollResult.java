package com.appsball.rapidpoll.pollresult.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class PollResult {

    public final String id;
    public final String ownerId;
    public final String pollName;
    public final ImmutableList<PollResultQuestionItem> questions;
    public final ImmutableList<CommentItem> comments;

    private PollResult(String id,
                       String ownerId,
                       String pollName,
                       ImmutableList<PollResultQuestionItem> questions,
                       ImmutableList<CommentItem> comments) {
        this.id = notNull(id, "id must not be null!");
        this.ownerId = notNull(ownerId, "ownerId must not be null!");
        this.pollName = notNull(pollName, "pollName must not be null!");
        this.questions = notNull(questions, "questions must not be null!");
        this.comments = notNull(comments, "comments must not be null!");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String ownerId;
        private String pollName;
        private ImmutableList.Builder<PollResultQuestionItem> questionsBuilder = ImmutableList.builder();
        private ImmutableList.Builder<CommentItem> commentsBuilder = ImmutableList.builder();

        public Builder withComments(List<CommentItem> comments) {
            this.commentsBuilder.addAll(comments);
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withPollName(String pollName) {
            this.pollName = pollName;
            return this;
        }

        public Builder withQuestions(List<PollResultQuestionItem> questions) {
            this.questionsBuilder.addAll(questions);
            return this;
        }

        public PollResult build() {
            return new PollResult(id, ownerId, pollName, questionsBuilder.build(), commentsBuilder.build());
        }
    }

}
