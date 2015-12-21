package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.createRapidPollRestService;

public class RapidPollActivity extends AppCompatActivity {

    public static final String userid = "11E5A76DC503770C9E7502000029BDFD";
    private RapidPollRestService rapidPollRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setIcon(R.drawable.logo);
        Logger.init();
        rapidPollRestService = createRapidPollRestService(this);
        initHawk();
        Hawk.put("userId", userid);
        final View container = findViewById(R.id.container);
        toAllPolls();
        RestCaller restCaller  =  new RestCaller(this);
//        restCaller.doPoll();
        restCaller.createPoll();
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
                .build();
    }

    public RapidPollRestService getRestService() {
        return rapidPollRestService;
    }

    public void toAllPolls() {
        final Fragment fragment = new AllPollsFragment();
        changeToFragment(fragment, false);
    }

    private void changeToFragment(Fragment fragment, boolean addToBackStack) {
        final String backStateName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, backStateName);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStateName);
        }
        fragmentTransaction.commit();
    }

}