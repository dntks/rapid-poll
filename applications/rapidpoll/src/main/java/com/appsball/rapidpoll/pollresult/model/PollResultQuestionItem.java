package com.appsball.rapidpoll.pollresult.model;

import com.appsball.rapidpoll.newpoll.model.ViewType;

import java.util.List;

public class PollResultQuestionItem implements PollResultListItem{

    public final long questionId;
    public final String questionName;
    public final List<PollResultAnswer> alternatives;

    public PollResultQuestionItem(long questionId, String questionName, List<PollResultAnswer> alternatives) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.alternatives = alternatives;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.QUESTION;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long questionId;
        private String questionName;
        private List<PollResultAnswer> alternatives;

        public Builder withQuestionId(long questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder withQuestionName(String questionName) {
            this.questionName = questionName;
            return this;
        }

        public Builder withAlternatives(List<PollResultAnswer> alternatives) {
            this.alternatives = alternatives;
            return this;
        }

        public PollResultQuestionItem build() {
            return new PollResultQuestionItem(questionId, questionName, alternatives);
        }
    }
}
