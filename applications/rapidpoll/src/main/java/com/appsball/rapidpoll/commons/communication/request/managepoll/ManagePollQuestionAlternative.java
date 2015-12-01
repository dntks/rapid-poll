package com.appsball.rapidpoll.commons.communication.request.managepoll;

public class ManagePollQuestionAlternative {

    public final String name;

    private ManagePollQuestionAlternative(String name) {
        this.name = name;
    }

    public static ManagePollQuestionAlternative alternativeRequestObject(String name) {
        return new ManagePollQuestionAlternative(name);
    }
}
