package com.appsball.rapidpoll.commons.communication.service;

import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.WaspError;

import static com.appsball.rapidpoll.commons.utils.Constants.INVALID_USERID;
import static com.appsball.rapidpoll.commons.utils.Constants.INVALID_USERID2;
import static com.appsball.rapidpoll.commons.utils.Constants.INVALID_USERID3;

public class DefaultResponseContainerCallback<T> implements Callback<ResponseContainer<T>> {
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String FAILURE_MESSAGE = "FAILURE";
    public static final String GENERAL_ERROR_MESSAGE = "Unknown error occured while getting details.";
    protected final ResponseContainerCallback<T> callback;
    protected final RapidPollActivity rapidPollActivity;

    public DefaultResponseContainerCallback(ResponseContainerCallback<T> callback, RapidPollActivity rapidPollActivity) {
        this.callback = callback;
        this.rapidPollActivity = rapidPollActivity;
    }
    @Override
    public void onSuccess(com.orhanobut.wasp.Response response, ResponseContainer<T> responseContainer) {
        DialogsBuilder.hideLoadingDialog();
        if(SUCCESS_MESSAGE.equals(responseContainer.status)){
            callback.onSuccess(responseContainer.result);
        }
        else if(FAILURE_MESSAGE.equals(responseContainer.status)){
            if (responseContainer.messages.contains(INVALID_USERID)
                    || responseContainer.messages.contains(INVALID_USERID2)
                    || responseContainer.messages.contains(INVALID_USERID3) ) {
                if(rapidPollActivity != null){
                    rapidPollActivity.registerUser();
                }
            } else {
                callback.onFailure();
            }
        }
        else if(!responseContainer.messages.isEmpty()){
            callback.onError(responseContainer.messages.get(0));
        }
        else{
            callback.onError(GENERAL_ERROR_MESSAGE);
        }
    }

    @Override
    public void onError(WaspError error) {
        DialogsBuilder.hideLoadingDialog();
        callback.onError(error.getErrorMessage());
    }
}
