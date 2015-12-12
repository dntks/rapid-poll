package com.appsball.rapidpoll.commons.communication.service;

import com.google.gson.Gson;
import com.orhanobut.wasp.parsers.GsonParser;

public class RapidPollRestParser extends GsonParser {
    @Override
    public String toBody(Object body) {
        if (body == null) {
            return null;
        }
        String jsonPart = new Gson().toJson(body);
        return "inputjson: {\"user_id\":\"11E58407B7A5FDDC9D0B8675BA421DCB\"}";

    }
}
