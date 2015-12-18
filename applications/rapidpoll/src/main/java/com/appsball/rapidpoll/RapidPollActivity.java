package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.createRapidPollRestService;

public class RapidPollActivity extends AppCompatActivity {

    private Object savedInstanceState;
    private RapidPollRestService rapidPollRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();
        rapidPollRestService = createRapidPollRestService(this);
        initHawk();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        final View container = findViewById(R.id.container);
        toAllPolls();
//        RestCaller restCaller  =  new RestCaller(this);
//        restCaller.doPoll();
//        restCaller.createPoll();
//        restCaller.getPollDetails();
//        restCaller.getPollResult();
//        restCaller.getPolls();
//        restCaller.searchPoll();
//        restCaller.updatePollState();
    }

    private void initHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.HIGHEST)
                .setPassword("rapidMimecs76Ps")
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .setCallback(new HawkBuilder.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                })
                .build();
    }

    public RapidPollRestService getRestService() {
        return rapidPollRestService;
    }

    public void toAllPolls() {
        if (!checkIsOnlineAndShowDialog()) {
            return;
        }
        final Fragment fragment = new AllPollsFragment();
        changeToFragment(fragment, false);
    }

    private void changeToFragment(Fragment fragment, boolean addToBackStack) {
        final String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, backStateName);
        if (addToBackStack) {
            tx.addToBackStack(backStateName);
        }
        tx.commit();
    }

    public boolean checkIsOnlineAndShowDialog() {
        if (!Utils.isOnline(this)) {
            DialogsBuilder.showNoNetConnectionDialog(this);
            return false;
        }
        return true;
    }
}
