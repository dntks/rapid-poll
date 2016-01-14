package com.appsball.rapidpoll.pollresult.model;

public class PollResultAnswer {

    public final long alternativeId;
    public final String alternativeName;
    public final float percentageValue;

    public PollResultAnswer(long alternativeId, String alternativeName, float percentageValue) {
        this.alternativeId = alternativeId;
        this.alternativeName = alternativeName;
        this.percentageValue = percentageValue;
    }

    public String getPercentageString(){
        int percentage = (int) (percentageValue*100);
        return percentage+"%";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long alternativeId;
        private String alternativeName;
        private float percentageValue;

        public Builder withAlternativeId(long alternativeId) {
            this.alternativeId = alternativeId;
            return this;
        }

        public Builder withAlternativeName(String alternativeName) {
            this.alternativeName = alternativeName;
            return this;
        }

        public Builder withPercentageValue(float percentageValue) {
            this.percentageValue = percentageValue;
            return this;
        }

        public PollResultAnswer build() {
            return new PollResultAnswer(alternativeId, alternativeName, percentageValue);
        }
    }
}
