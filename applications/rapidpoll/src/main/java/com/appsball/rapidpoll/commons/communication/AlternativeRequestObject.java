package com.appsball.rapidpoll.commons.communication;

public class AlternativeRequestObject {

    public final String name;

    private AlternativeRequestObject(String name) {
        this.name = name;
    }

    public static AlternativeRequestObject alternativeRequestObject(String name) {
        return new AlternativeRequestObject(name);
    }
}
