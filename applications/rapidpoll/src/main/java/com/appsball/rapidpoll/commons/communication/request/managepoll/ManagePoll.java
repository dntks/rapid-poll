package com.appsball.rapidpoll.commons.communication.request.managepoll;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class ManagePoll {

    public final String id;
    public final String name;
    @SerializedName("public")
    public final String isPublic;
    public final String anonymous;
    public final String allow_comment;
    public final String allow_uncomplete_answer;
    public final String draft;
    public final List<ManagePollQuestion> questions;

    private ManagePoll(String id, String name, String isPublic, String anonymous, String allow_comment, String allow_uncomplete_answer, String draft, List<ManagePollQuestion> questions) {
        this.id = notNull(id, "id must not be null!");
        this.draft = notNull(draft, "draft must not be null");
        this.name = notNull(name, "name must not be null");
        this.isPublic = notNull(isPublic, "isPublic must not be null");
        this.anonymous = notNull(anonymous, "anonymous must not be null");
        this.allow_comment = notNull(allow_comment, "allow_comment must not be null");
        this.allow_uncomplete_answer = notNull(allow_uncomplete_answer, "allow_uncomplete_answer must not be null");
        this.questions = notNull(questions, "questions must not be null");
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id="";
        private String name;
        private String isPublic;
        private String anonymous;
        private String allow_comment;
        private String allow_uncomplete_answer;
        private String draft;
        private List<ManagePollQuestion> questions;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIsPublic(boolean isPublic) {
            this.isPublic = isPublic ? "1" : "0";
            return this;
        }

        public Builder withAnonymous(boolean anonymous) {
            this.anonymous = anonymous ? "1" : "0";
            return this;
        }

        public Builder withAllowComment(boolean allowComment) {
            this.allow_comment = allowComment ? "1" : "0";
            return this;
        }

        public Builder withAllowUncompleteAnswer(boolean allow_uncomplete_answer) {
            this.allow_uncomplete_answer = allow_uncomplete_answer ? "1" : "0";
            return this;
        }

        public Builder withDraft(boolean draft) {
            this.draft = draft ? "1" : "0";
            return this;
        }

        public Builder withQuestions(List<ManagePollQuestion> questions) {
            this.questions = questions;
            return this;
        }

        public ManagePoll build() {
            return new ManagePoll(id, name, isPublic, anonymous, allow_comment, allow_uncomplete_answer, draft, questions);
        }
    }
}
