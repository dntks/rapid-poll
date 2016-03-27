package com.appsball.rapidpoll;

import android.os.Parcel;
import android.os.Parcelable;

public class PollIdentifierData implements Parcelable {
    public final String pollId;
    public final String pollCode;
    public final String pollTitle;

    private PollIdentifierData(String pollId, String pollCode, String pollTitle) {
        this.pollId = pollId;
        this.pollCode = pollCode;
        this.pollTitle = pollTitle;
    }

    protected PollIdentifierData(Parcel in) {
        pollId = in.readString();
        pollCode = in.readString();
        pollTitle = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pollId);
        dest.writeString(pollCode);
        dest.writeString(pollTitle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PollIdentifierData> CREATOR = new Parcelable.Creator<PollIdentifierData>() {
        @Override
        public PollIdentifierData createFromParcel(Parcel in) {
            return new PollIdentifierData(in);
        }

        @Override
        public PollIdentifierData[] newArray(int size) {
            return new PollIdentifierData[size];
        }
    };

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String pollId;
        private String pollCode;
        private String pollTitle;

        public Builder withPollId(String pollId) {
            this.pollId = pollId;
            return this;
        }

        public Builder withPollCode(String pollCode) {
            this.pollCode = pollCode;
            return this;
        }

        public Builder withPollTitle(String pollTitle) {
            this.pollTitle = pollTitle;
            return this;
        }

        public PollIdentifierData build() {
            return new PollIdentifierData(pollId, pollCode, pollTitle);
        }
    }
}

