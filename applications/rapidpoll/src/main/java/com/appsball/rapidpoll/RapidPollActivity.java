package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.fillpoll.FillPollFragment;
import com.appsball.rapidpoll.mypolls.MyPollsFragment;
import com.appsball.rapidpoll.newpoll.NewPollFragment;
import com.appsball.rapidpoll.results.ResultsFragment;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.createRapidPollRestService;

public class RapidPollActivity extends AppCompatActivity {

    public static final String userid = "11E5B3A41655D669886502000029BDFD";
    public static final String POLL_CODE = "poll_code";
    public static final String POLL_ID = "poll_id";
    public static final String USER_ID_KEY = "userId";
    public static final String POLL_TITLE = "poll_title";
    private RapidPollRestService rapidPollRestService;
    private EditText editableTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        editableTitle = (EditText) findViewById(R.id.titleEditText);
        Logger.init();
        initHawk();
        Hawk.put("userId", userid);
        final View container = findViewById(R.id.container);
        rapidPollRestService = createRapidPollRestService(this);
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

    public void setHomeTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(title);
            editableTitle.setVisibility(View.GONE);
        }
    }

    public EditText getEditableTitle() {
        return editableTitle;
    }

    public void hideBackButton() {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_s);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("");
            editableTitle.setVisibility(View.GONE);
        }
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

    public void toResults() {
        final Fragment fragment = new ResultsFragment();
        changeToFragment(fragment, false);
    }

    public void toMyPolls() {
        final Fragment fragment = new MyPollsFragment();
        changeToFragment(fragment, false);
    }

    public void toCreatePoll() {
        final Fragment fragment = new NewPollFragment();
        changeToFragment(fragment, true);
    }

    public void toFillPoll(String pollId, String pollCode, String pollTitle) {
        final Fragment fragment = new FillPollFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POLL_CODE, pollCode);
        bundle.putString(POLL_ID, pollId);
        bundle.putString(POLL_TITLE, pollTitle);
        fragment.setArguments(bundle);
        changeToFragment(fragment, true);
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

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
