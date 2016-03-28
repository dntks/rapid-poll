package com.appsball.rapidpoll.commons.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appsball.rapidpoll.commons.communication.response.pollresult.PollResultEmail;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class ResultAlternativeDetails implements Parcelable{

    public final String percentage;
    public final String nameWithOrder;
    public final Integer answerColor;
    public final ImmutableList<PollResultEmail> pollResultEmailList;

    private ResultAlternativeDetails(String percentage, String nameWithOrder, Integer answerColor, ImmutableList<PollResultEmail> pollResultEmailList) {
        this.percentage = percentage;
        this.nameWithOrder = nameWithOrder;
        this.answerColor = answerColor;
        this.pollResultEmailList = pollResultEmailList;
    }

    protected ResultAlternativeDetails(Parcel in) {
        percentage = in.readString();
        nameWithOrder = in.readString();
        answerColor = in.readByte() == 0x00 ? null : in.readInt();
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
        dest.writeString(percentage);
        dest.writeString(nameWithOrder);
        if (answerColor == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(answerColor);
        }
        if (pollResultEmailList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(pollResultEmailList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ResultAlternativeDetails> CREATOR = new Parcelable.Creator<ResultAlternativeDetails>() {
        @Override
        public ResultAlternativeDetails createFromParcel(Parcel in) {
            return new ResultAlternativeDetails(in);
        }

        @Override
        public ResultAlternativeDetails[] newArray(int size) {
            return new ResultAlternativeDetails[size];
        }
    };

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String percentage;
        private String nameWithOrder;
        private Integer answerColor;
        private ImmutableList.Builder<PollResultEmail> emailList = ImmutableList.builder();

        public Builder withPercentage(String percentage) {
            this.percentage = percentage;
            return this;
        }

        public Builder withNameWithOrder(String nameWithOrder) {
            this.nameWithOrder = nameWithOrder;
            return this;
        }

        public Builder withAnswerColor(Integer answerColor) {
            this.answerColor = answerColor;
            return this;
        }

        public Builder addEmails(List<PollResultEmail> pollResultEmailList) {
            if(pollResultEmailList!=null){
                emailList.addAll(pollResultEmailList);
            }
            return this;
        }

        public ResultAlternativeDetails build() {
            return new ResultAlternativeDetails(percentage, nameWithOrder, answerColor, emailList.build());
        }
    }
}
