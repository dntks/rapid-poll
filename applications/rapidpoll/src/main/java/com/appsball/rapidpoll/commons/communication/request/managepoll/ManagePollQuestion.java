package com.appsball.rapidpoll.commons.communication.request.managepoll;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class ManagePollQuestion {

    public final String name;
    public final String multichoice;
    public final List<ManagePollQuestionAlternative> alternatives;

    public ManagePollQuestion(String name, String multichoice, List<ManagePollQuestionAlternative> alternatives) {
        this.name = notNull(name, "name must not be null");
        this.multichoice = notNull(multichoice, "multichoice must not be null");
        this.alternatives = notNull(alternatives, "alternatives must not be null");
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
