package com.appsball.rapidpoll.commons.communication.service;

import com.appsball.rapidpoll.commons.communication.response.ResponseContainer;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.orhanobut.wasp.Callback;
import com.orhanobut.wasp.WaspError;

public class EmptyResponseCallback implements Callback<ResponseContainer<Object>> {
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String FAILURE_MESSAGE = "FAILURE";
    public static final String GENERAL_ERROR_MESSAGE = "Unknown error occured while getting details.";
    protected final ResponseCallback callback;

    public EmptyResponseCallback(ResponseCallback callback) {
        this.callback = callback;
    }
    @Override
    public void onSuccess(com.orhanobut.wasp.Response response, ResponseContainer<Object> responseContainer) {
        DialogsBuilder.hideLoadingDialog();
        if(SUCCESS_MESSAGE.equals(responseContainer.status)){
            callback.onSuccess();
        }
        else if(FAILURE_MESSAGE.equals(responseContainer.status)){
            callback.onFailure();
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