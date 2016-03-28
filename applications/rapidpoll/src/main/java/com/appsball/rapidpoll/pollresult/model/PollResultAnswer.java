package com.appsball.rapidpoll.pollresult.model;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class PollResultAnswer{

    public final long alternativeId;
    public final String name;
    public final float percentageValue;
    public final ImmutableList<PollResultEmail> pollResultEmailList;

    private PollResultAnswer(long alternativeId, String name, float percentageValue, ImmutableList<PollResultEmail> pollResultEmailList) {
        this.alternativeId = alternativeId;
        this.name = name;
        this.percentageValue = percentageValue;
        this.pollResultEmailList = pollResultEmailList;
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
        private ImmutableList.Builder<PollResultEmail> emailList = ImmutableList.builder();

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

        public Builder addEmails(List<PollResultEmail> pollResultEmailList) {
            if(pollResultEmailList!=null){
                emailList.addAll(pollResultEmailList);
            }
            return this;
        }

        public PollResultAnswer build() {
            return new PollResultAnswer(alternativeId, alternativeName, percentageValue, emailList.build());
        }
    }
}
