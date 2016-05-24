package com.appsball.rapidpoll;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsball.rapidpoll.commons.communication.service.RapidPollRestService;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.appsball.rapidpoll.register.GCMRegister;
import com.appsball.rapidpoll.register.UserRegister;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.appsball.rapidpoll.commons.communication.service.RapidPollRestService.createRapidPollRestService;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;
import static com.appsball.rapidpoll.commons.utils.Utils.isRegistered;

public class RapidPollActivity extends AppCompatActivity {
    public static final String ServerAPIKey = "AIzaSyAkliInYloQCi9nUVFZzL-N73dO32p-h9c";
    public static final String SenderID = "73756231339";
    private RapidPollRestService rapidPollRestService;
    private FragmentSwitcher fragmentSwitcher;

    private static final String TAG = "MainActivity";

    @BindView(R.id.titleEditText)  EditText editableTitle;
    @BindView(R.id.registrationProgressBar) ProgressBar mRegistrationProgressBar;
    @BindView(R.id.informationTextView) TextView mInformationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        ButterKnife.bind(this);
        Logger.init();
        initHawk();
        fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager());

        rapidPollRestService = createRapidPollRestService(this);
//        Hawk.put(USER_ID_KEY, "123456");

        Bundle extras = getIntent().getExtras();

        checkIsOnlineAndShowSimpleDialog(extras);

    }

    private void registerGcmOrLoadFragment(Bundle extras) {
        if (!isRegistered()) {
            registerUser();
        } else if (extras != null && extras.getString(POLL_ID) != null) {
            hideRegisterViews();
            fragmentSwitcher.toFragmentScreenByBundle(extras);
        } else {
            hideRegisterViews();
            fragmentSwitcher.toAllPolls();
        }
    }

    public void registerUser() {
        UserRegister userRegister = new UserRegister(rapidPollRestService, createOnRegisterListener());
        GCMRegister gcmRegister = new GCMRegister(this, userRegister);
        gcmRegister.registerGCM();
    }

    @NonNull
    private UserRegister.OnRegisterListener createOnRegisterListener() {
        return new UserRegister.OnRegisterListener() {
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
    }

    public void checkIsOnlineAndShowSimpleDialog(final Bundle extras) {
        if (!Utils.isOnline(getApplicationContext())) {
            DialogsBuilder.showNoNetConnectionDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkIsOnlineAndShowSimpleDialog(extras);
                    dialog.dismiss();
                }
            });
        } else {
            registerGcmOrLoadFragment(extras);
        }
    }

    private void hideRegisterViews() {
        mRegistrationProgressBar.setVisibility(View.GONE);
        mInformationTextView.setVisibility(View.GONE);
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

    public void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeButtonEnabled(true);
        int actionbarInt = ActionBar.DISPLAY_HOME_AS_UP;
        getSupportActionBar().setIcon(null);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
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
    public void onBackPressed() {
        navigateBack();
    }

    private void navigateBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (findViewById(R.id.paging_list_view) != null) {
                finish();
            } else {
                fragmentSwitcher.toAllPolls();
            }
        } else {
            if (findViewById(R.id.paging_list_view) != null) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigateBack();
        return true;
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}
