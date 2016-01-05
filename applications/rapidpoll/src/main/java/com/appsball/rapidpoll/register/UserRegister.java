package com.appsball.rapidpoll.register;

import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.response.RegisterResponse;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.Response;
import com.orhanobut.wasp.WaspError;

import static com.appsball.rapidpoll.commons.communication.request.RegisterRequest.registerRequest;

public class UserRegister {

    private final RapidPollRestService rapidPollRestService;
    private final OnRegisterListener onRegisterListener;

    public UserRegister(RapidPollRestService rapidPollRestService, OnRegisterListener onRegisterListener) {
        this.rapidPollRestService = rapidPollRestService;
        this.onRegisterListener = onRegisterListener;
    }

    public void registerUser(String gcmToken){
        rapidPollRestService.registerUser(registerRequest(gcmToken), new Callback<ResponseContainer<RegisterResponse>>() {
            @Override
            public void onSuccess(Response response, ResponseContainer<RegisterResponse> responseContainer) {
                if(RapidPollRestService.SUCCESS_MESSAGE.equals(responseContainer.status)){
                    Hawk.put(RapidPollActivity.USER_ID_KEY, responseContainer.result.user_id);
                    onRegisterListener.succesfulRegister();
                }else {
                    onRegisterListener.failedRegister();
                }
            }

            @Override
            public void onError(WaspError error) {
                onRegisterListener.failedRegister();
            }
        });
    }

    public void gcmFailed(){
        onRegisterListener.failedRegister();
    }

    public interface OnRegisterListener {
        void succesfulRegister();
        void failedRegister();
    }
}
