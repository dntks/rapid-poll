package com.appsball.rapidpoll;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.pushnotification.RegistrationAsyncTask;
import com.appsball.rapidpoll.pushnotification.RegistrationIntentService;
import com.appsball.rapidpoll.register.UserRegister;
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
    private RapidPollRestService rapidPollRestService;
    private EditText editableTitle;
    private FragmentSwitcher fragmentSwitcher;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        editableTitle = (EditText) findViewById(R.id.titleEditText);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        Logger.init();
        initHawk();
        fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager());

        rapidPollRestService = createRapidPollRestService(this);
//        Hawk.put(USER_ID_KEY, USER_ID);
//        registerGCM();
        Bundle extras = getIntent().getExtras();

        if (!isRegistered()) {
            registerGCM();
        } else if (extras != null) {
            hideRegisterViews();
            fragmentSwitcher.toFragmentScreenByBundle(extras);
        } else {
            hideRegisterViews();
            fragmentSwitcher.toAllPolls();
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
        return !Constants.NO_ID.equals(Hawk.get(Constants.USER_ID_KEY, Constants.NO_ID));
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
                    fragmentSwitcher.toAllPolls();
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
                new IntentFilter(Constants.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

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


    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }
}
