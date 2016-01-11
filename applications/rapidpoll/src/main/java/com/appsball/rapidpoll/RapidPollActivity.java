package com.appsball.rapidpoll;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.fillpoll.FillPollFragment;
import com.appsball.rapidpoll.mypolls.MyPollsFragment;
import com.appsball.rapidpoll.newpoll.NewPollFragment;
import com.appsball.rapidpoll.pushnotification.RegistrationAsyncTask;
import com.appsball.rapidpoll.pushnotification.RegistrationIntentService;
import com.appsball.rapidpoll.register.UserRegister;
import com.appsball.rapidpoll.results.ResultsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.createRapidPollRestService;

public class RapidPollActivity extends AppCompatActivity {
    public static final String ServerAPIKey = "AIzaSyAkliInYloQCi9nUVFZzL-N73dO32p-h9c";
    public static final String SenderID = "73756231339";
    public static final String USER_ID = "31000000000000000000000000000000";
    public static final String POLL_CODE = "poll_code";
    public static final String POLL_ID = "poll_id";
    public static final String USER_ID_KEY = "userId";
    public static final String POLL_TITLE = "poll_title";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String NO_ID = "no id";
    private RapidPollRestService rapidPollRestService;
    private EditText editableTitle;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        editableTitle = (EditText) findViewById(R.id.titleEditText);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        Logger.init();
        initHawk();


        rapidPollRestService = createRapidPollRestService(this);
        Hawk.put(USER_ID_KEY, USER_ID);
//        registerGCM();

        if (!isRegistered()) {
            registerGCM();
        } else {
            hideRegisterViews();
            toAllPolls();
        }



//        RestCaller restCaller  =  new RestCaller(this);
//        restCaller.doPoll();
//        restCaller.createPoll();
//        restCaller.getPollDetails();
//        restCaller.getPollResult();
//        restCaller.getPolls();
//        restCaller.searchPoll();
//        restCaller.updatePollState();
    }

    private void hideRegisterViews() {
        mRegistrationProgressBar.setVisibility(View.GONE);
        mInformationTextView.setVisibility(View.GONE);
    }

    private boolean isRegistered() {

        return !NO_ID.equals(Hawk.get(USER_ID_KEY, NO_ID));
    }

    public void registerGCM() {

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            UserRegister.OnRegisterListener registerListener = new UserRegister.OnRegisterListener() {
                @Override
                public void succesfulRegister() {
                    mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    hideRegisterViews();
                    toAllPolls();
                }

                @Override
                public void failedRegister() {
                    mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    mInformationTextView.setText(getString(R.string.token_error_message));

                }
            };
            UserRegister userRegister = new UserRegister(rapidPollRestService, registerListener);
            RegistrationAsyncTask registrationAsyncTask = new RegistrationAsyncTask(userRegister, this);
            registrationAsyncTask.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
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
        switchToFragment(fragment, false);
    }

    public void toResults() {
        final Fragment fragment = new ResultsFragment();
        switchToFragment(fragment, false);
    }

    public void toMyPolls() {
        final Fragment fragment = new MyPollsFragment();
        switchToFragment(fragment, false);
    }

    public void toCreatePoll() {
        final Fragment fragment = new NewPollFragment();
        switchToFragment(fragment, true);
    }

    public void toFillPoll(String pollId, String pollCode, String pollTitle) {
        final Fragment fragment = new FillPollFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POLL_CODE, pollCode);
        bundle.putString(POLL_ID, pollId);
        bundle.putString(POLL_TITLE, pollTitle);
        fragment.setArguments(bundle);
        switchToFragment(fragment, true);
    }

    private void switchToFragment(Fragment fragment, boolean addToBackStack) {
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
