package com.appsball.rapidpoll.commons.communication.request.managepoll;

import java.util.List;

public class ManagePollQuestion {

    public final String name;
    public final String multichoice;
    public final List<ManagePollQuestionAlternative> alternatives;

    public ManagePollQuestion(String name, String multichoice, List<ManagePollQuestionAlternative> alternatives) {
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
        private List<ManagePollQuestionAlternative> alternatives;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMultichoice(String multichoice) {
            this.multichoice = multichoice;
            return this;
        }

        public Builder withAlternatives(List<ManagePollQuestionAlternative> alternatives) {
            this.alternatives = alternatives;
            return this;
        }

        public ManagePollQuestion build() {
            return new ManagePollQuestion(name, multichoice, alternatives);
        }
    }
}
