package com.appsball.rapidpoll.commons.communication.response.pollresult;

import android.os.Parcel;
import android.os.Parcelable;

public class PollResultEmail implements Parcelable {
    public String email;
    public String comment;

    protected PollResultEmail(Parcel in) {
        email = in.readString();
        comment = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(comment);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PollResultEmail> CREATOR = new Parcelable.Creator<PollResultEmail>() {
        @Override
        public PollResultEmail createFromParcel(Parcel in) {
            return new PollResultEmail(in);
        }

        @Override
        public PollResultEmail[] newArray(int size) {
            return new PollResultEmail[size];
        }
    };
}
