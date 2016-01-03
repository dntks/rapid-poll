package com.appsball.rapidpoll.fillpoll.model;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class FillPollDetails {
    public final String pollId;
    private Optional<String> comment;
    public ImmutableList<FillPollQuestion> questions;

    public FillPollDetails(String pollId, Optional<String> comment, ImmutableList<FillPollQuestion> questions) {
        this.pollId = pollId;
        this.comment = comment;
        this.questions = questions;
    }

    public Optional<String> getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if(isNotBlank(comment)){
            this.comment=Optional.of(comment);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String pollId;
        private Optional<String> comment = Optional.absent();
        private ImmutableList.Builder<FillPollQuestion> questionsBuilder = ImmutableList.builder();

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withCommentOptional(Optional<String> comment) {
            this.comment = comment;
            return this;
        }
        public Builder withComment(String comment) {
            this.comment = Optional.fromNullable(comment);
            return this;
        }

        public Builder withQuestions(Iterable<FillPollQuestion> questions) {
            this.questionsBuilder.addAll(questions);
            return this;
        }

        public FillPollDetails build() {
            return new FillPollDetails(pollId, comment, questionsBuilder.build());
        }
    }
}
