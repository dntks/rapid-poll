package com.appsball.rapidpoll.commons.communication.request;

public class AlternativeRequestModel {

    public final String name;

    private AlternativeRequestModel(String name) {
        this.name = name;
    }

    public static AlternativeRequestModel alternativeRequestObject(String name) {
        return new AlternativeRequestModel(name);
    }
}
