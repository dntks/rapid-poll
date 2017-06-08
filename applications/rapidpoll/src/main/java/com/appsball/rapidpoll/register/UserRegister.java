package com.appsball.rapidpoll.register;

import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.communication.service.ResponseContainerCallback;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.commons.communication.request.RegisterRequest.registerRequest;

public class UserRegister {


    private final RapidPollRestService rapidPollRestService;
    private final OnRegisterListener onRegisterListener;

    public UserRegister(RapidPollRestService rapidPollRestService, OnRegisterListener onRegisterListener) {
        this.rapidPollRestService = rapidPollRestService;
        this.onRegisterListener = onRegisterListener;
    }

    public void registerUser(String gcmToken) {
        rapidPollRestService.registerUser(registerRequest(gcmToken), new ResponseContainerCallback<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse response) {
                Hawk.put(Constants.USER_ID_KEY, response.user_id);
                onRegisterListener.succesfulRegister();
            }

            @Override
            public void onFailure() {
                onRegisterListener.failedRegister();
            }

            @Override
            public void onError(String errorMessage) {
                onFailure();
            }
        });
    }

    public void gcmFailed() {
        onRegisterListener.failedRegister();
    }

    public interface OnRegisterListener {
        void succesfulRegister();

        void failedRegister();
    }
}
