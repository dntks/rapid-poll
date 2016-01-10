package com.appsball.rapidpoll.commons.communication.request;

import static org.apache.commons.lang3.Validate.notNull;

public class RegisterRequest {
    public final String device_id;
    public final String platform="ANDROID";

    private RegisterRequest(String device_id) {
        this.device_id = notNull(device_id, "device_id must not be null");
    }

    public static RegisterRequest registerRequest(String device_id) {
        return new RegisterRequest(device_id);
    }
}
