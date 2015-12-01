package com.appsball.rapidpoll.commons.communication.request.managepoll;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ManagePoll {

    public final String name;
    @SerializedName("public")
    public final String isPublic;
    public final String anonymous;
    public final String allow_comment;
    public final String allow_uncomplete_result;
    public final List<ManagePollQuestion> questions;

    private ManagePoll(String name, String isPublic, String anonymous, String allow_comment, String allow_uncomplete_result, List<ManagePollQuestion> questions) {
        this.name = name;
        this.isPublic = isPublic;
        this.anonymous = anonymous;
        this.allow_comment = allow_comment;
        this.allow_uncomplete_result = allow_uncomplete_result;
        this.questions = questions;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String isPublic;
        private String anonymous;
        private String allow_comment;
        private String allow_uncomplete_result;
        private List<ManagePollQuestion> questions;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIsPublic(String isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Builder withAnonymous(String anonymous) {
            this.anonymous = anonymous;
            return this;
        }

        public Builder withAllow_comment(String allow_comment) {
            this.allow_comment = allow_comment;
            return this;
        }

        public Builder withAllow_uncomplete_result(String allow_uncomplete_result) {
            this.allow_uncomplete_result = allow_uncomplete_result;
            return this;
        }

        public Builder withQuestions(List<ManagePollQuestion> questions) {
            this.questions = questions;
            return this;
        }

        public ManagePoll build() {
            return new ManagePoll(name, isPublic, anonymous, allow_comment, allow_uncomplete_result, questions);
        }
    }
}
