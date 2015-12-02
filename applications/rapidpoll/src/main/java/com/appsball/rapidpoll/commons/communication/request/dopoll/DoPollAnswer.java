package com.appsball.rapidpoll.commons.communication.request.dopoll;

import static org.apache.commons.lang3.Validate.notNull;

public class DoPollAnswer {
    public final String alternative_id;

    private DoPollAnswer(String alternative_id) {
        this.alternative_id = notNull(alternative_id, "alternative_id must not be null");
    }

    public static DoPollAnswer doPollAnswer(String alternativeId) {
        return new DoPollAnswer(alternativeId);
    }
}
