package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.utils.Utils;
import com.appsball.rapidpoll.commons.view.DialogsBuilder;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private Object savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();

        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        final View container = findViewById(R.id.container);
        toAllPolls();
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
