package com.appsball.rapidpoll.searchpolls.model;

import com.appsball.rapidpoll.commons.model.PollState;
import com.google.common.base.Optional;

public class SearchPollsItemData {

    public final String id;
    public final String name;
    public final boolean isPublic;
    public final boolean anonymous;
    public final boolean allowComment;
    public final String expirationDate;
    public final String ownerId;
    public final PollState state;
    public final String publicationDate;
    public final Optional<String> closedDate;
    public final int numberOfQuestions;
    public final int answeredQuestionsByUser;
    public final int answeredQuestionsRatioFor100;
    public final int numberOfVotes;
    public final String votesText;


    public SearchPollsItemData(String id,
                               String name,
                               boolean isPublic,
                               boolean anonymous,
                               boolean allowComment,
                               String expirationDate,
                               String ownerId,
                               PollState state,
                               String publicationDate,
                               Optional<String> closedDate,
                               int numberOfQuestions,
                               int answeredQuestionsByUser,
                               int numberOfVotes,
                               int answeredQuestionsRatioFor100,
                               String votesText) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.anonymous = anonymous;
        this.allowComment = allowComment;
        this.expirationDate = expirationDate;
        this.ownerId = ownerId;
        this.state = state;
        this.publicationDate = publicationDate;
        this.closedDate = closedDate;
        this.numberOfQuestions = numberOfQuestions;
        this.answeredQuestionsByUser = answeredQuestionsByUser;
        this.numberOfVotes = numberOfVotes;
        this.answeredQuestionsRatioFor100 = answeredQuestionsRatioFor100;
        this.votesText = votesText;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private boolean isPublic;
        private boolean anonymous;
        private boolean allowComment;
        private String expirationDate;
        private String ownerId;
        private PollState state;
        private String publicationDate;
        private Optional<String> closedDate = Optional.absent();
        private int numberOfQuestions;
        private int answeredQuestionsByUser;
        private int numberOfVotes;
        private int answeredQuestionsRatioFor100;
        private String votesText;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Builder withAnonymous(boolean anonymous) {
            this.anonymous = anonymous;
            return this;
        }

        public Builder withAllowComment(boolean allowComment) {
            this.allowComment = allowComment;
            return this;
        }

        public Builder withExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withOwnerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder withState(PollState state) {
            this.state = state;
            return this;
        }

        public Builder withPublicationDate(String publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public Builder withClosedDate(String closedDate) {
            this.closedDate = Optional.fromNullable(closedDate);
            return this;
        }

        public Builder withNumberOfQuestions(int numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }

        public Builder withAnsweredQuestionsByUser(int answeredQuestionsByUser) {
            this.answeredQuestionsByUser = answeredQuestionsByUser;
            return this;
        }

        public Builder withNumberOfVotes(int numberOfVotes) {
            this.numberOfVotes = numberOfVotes;
            return this;
        }

        public Builder withAnsweredQuestionsRatioFor100(int answeredQuestionsRatioFor100) {
            this.answeredQuestionsRatioFor100 = answeredQuestionsRatioFor100;
            return this;
        }

        public Builder withVotesText(String votesText) {
            this.votesText = votesText;
            return this;
        }

        public SearchPollsItemData build() {
            return new SearchPollsItemData(id,
                    name,
                    isPublic,
                    anonymous,
                    allowComment,
                    expirationDate,
                    ownerId,
                    state,
                    publicationDate,
                    closedDate,
                    numberOfQuestions,
                    answeredQuestionsByUser,
                    numberOfVotes,
                    answeredQuestionsRatioFor100,
                    votesText);
        }
    }
}
