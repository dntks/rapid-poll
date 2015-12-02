package com.appsball.rapidpoll.commons.communication.request.managepoll;

import static org.apache.commons.lang3.Validate.notNull;

public class ManagePollQuestionAlternative {

    public final String name;

    private ManagePollQuestionAlternative(String name) {
        this.name = notNull(name, "name must not be null");
    }

    public static ManagePollQuestionAlternative alternativeRequestObject(String name) {
        return new ManagePollQuestionAlternative(name);
    }
}
