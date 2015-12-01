package com.appsball.rapidpoll.commons.communication.request.dopoll;

public class DoPollAnswer {
    public final String alternative_id;

    private DoPollAnswer(String alternative_id) {
        this.alternative_id = alternative_id;
    }

    public static DoPollAnswer doPollAnswer(String alternative_id) {
        return new DoPollAnswer(alternative_id);
    }
}
