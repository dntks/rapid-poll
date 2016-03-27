package com.appsball.rapidpoll.pollresult.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class PollResultAnswer implements Parcelable{

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

    protected PollResultAnswer(Parcel in) {
        alternativeId = in.readLong();
        name = in.readString();
        percentageValue = in.readFloat();
        if (in.readByte() == 0x01) {
            pollResultEmailList = ImmutableList.copyOf(in.readArrayList(PollResultEmail.class.getClassLoader()));
        } else {
            pollResultEmailList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(alternativeId);
        dest.writeString(name);
        dest.writeFloat(percentageValue);
        if (pollResultEmailList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(pollResultEmailList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PollResultAnswer> CREATOR = new Parcelable.Creator<PollResultAnswer>() {
        @Override
        public PollResultAnswer createFromParcel(Parcel in) {
            return new PollResultAnswer(in);
        }

        @Override
        public PollResultAnswer[] newArray(int size) {
            return new PollResultAnswer[size];
        }
    };

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
            emailList.addAll(pollResultEmailList);
            return this;
        }

        public PollResultAnswer build() {
            return new PollResultAnswer(alternativeId, alternativeName, percentageValue, emailList.build());
        }
    }
}
