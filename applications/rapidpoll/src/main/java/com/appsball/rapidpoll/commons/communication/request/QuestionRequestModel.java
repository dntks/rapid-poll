package com.appsball.rapidpoll.commons.communication.request;

import java.util.List;

public class QuestionRequestModel {

    public final String name;
    public final String multichoice;
    public final List<AlternativeRequestModel> alternatives;

    public QuestionRequestModel(String name, String multichoice, List<AlternativeRequestModel> alternatives) {
        this.name = name;
        this.multichoice = multichoice;
        this.alternatives = alternatives;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String multichoice;
        private List<AlternativeRequestModel> alternatives;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMultichoice(String multichoice) {
            this.multichoice = multichoice;
            return this;
        }

        public Builder withAlternatives(List<AlternativeRequestModel> alternatives) {
            this.alternatives = alternatives;
            return this;
        }

        public QuestionRequestModel build() {
            return new QuestionRequestModel(name, multichoice, alternatives);
        }
    }
}
