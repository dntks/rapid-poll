package com.appsball.rapidpoll.commons.communication;

public class RegisterRequest {
    public final String device_id;

    private RegisterRequest(String device_id) {
        this.device_id = device_id;
    }

    public static RegisterRequest registerRequest(String device_id) {
        return new RegisterRequest(device_id);
    }
}
