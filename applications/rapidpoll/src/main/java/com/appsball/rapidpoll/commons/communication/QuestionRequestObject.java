package com.appsball.rapidpoll.commons.communication;

import java.util.List;

public class QuestionRequestObject {

    public final String name;
    public final String multichoice;
    public final List<AlternativeRequestObject> alternatives;

    public QuestionRequestObject(String name, String multichoice, List<AlternativeRequestObject> alternatives) {
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
        private List<AlternativeRequestObject> alternatives;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMultichoice(String multichoice) {
            this.multichoice = multichoice;
            return this;
        }

        public Builder withAlternatives(List<AlternativeRequestObject> alternatives) {
            this.alternatives = alternatives;
            return this;
        }

        public QuestionRequestObject build() {
            return new QuestionRequestObject(name, multichoice, alternatives);
        }
    }
}
