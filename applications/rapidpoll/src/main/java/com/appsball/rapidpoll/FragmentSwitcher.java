package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.fillpoll.FillPollFragment;
import com.appsball.rapidpoll.mypolls.MyPollsFragment;
import com.appsball.rapidpoll.newpoll.ManagePollFragment;
import com.appsball.rapidpoll.pollresult.PollResultFragment;
import com.appsball.rapidpoll.results.ResultsFragment;

import static com.appsball.rapidpoll.RapidPollActivity.POLL_CODE;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_ID;
import static com.appsball.rapidpoll.RapidPollActivity.POLL_TITLE;

public class FragmentSwitcher {
    private final FragmentManager fragmentManager;

    public FragmentSwitcher(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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

    public void toManagePoll() {
        final Fragment fragment = new ManagePollFragment();
        switchToFragment(fragment, true);
    }

    public void toManagePoll(String pollId) {
        final Fragment fragment = new ManagePollFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POLL_ID, pollId);
        fragment.setArguments(bundle);
        switchToFragment(fragment, true);
    }

    public void toPollResult(PollIdentifierData pollIdentifierData) {
        final Fragment fragment = new PollResultFragment();
        fragment.setArguments(createPollIdentifierBundle(pollIdentifierData));
        switchToFragment(fragment, true);
    }

    public void toFillPoll(PollIdentifierData pollIdentifierData) {
        final Fragment fragment = new FillPollFragment();
        fragment.setArguments(createPollIdentifierBundle(pollIdentifierData));
        switchToFragment(fragment, true);
    }

    private Bundle createPollIdentifierBundle(PollIdentifierData pollIdentifierData) {
        Bundle bundle = new Bundle();
        bundle.putString(POLL_CODE, pollIdentifierData.pollCode);
        bundle.putString(POLL_ID, pollIdentifierData.pollId);
        bundle.putString(POLL_TITLE, pollIdentifierData.pollTitle);
        return bundle;
    }

    private void switchToFragment(Fragment fragment, boolean addToBackStack) {
        final String backStateName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, backStateName);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStateName);
        }
        fragmentTransaction.commit();
    }

    public void toFragmentScreenByBundle(Bundle extras) {
        String fragmentName = extras.getString(RapidPollActivity.FRAGMENT_NAME);
        String pollTitle = extras.getString(RapidPollActivity.POLL_TITLE);
        String pollCode = extras.getString(RapidPollActivity.POLL_CODE);
        String pollId = extras.getString(RapidPollActivity.POLL_ID);
        if (fragmentName.equals(ScreenFragment.POLL_RESULT.apiName)) {
            PollIdentifierData pollIdentifierData = PollIdentifierData.builder().withPollCode(pollCode).withPollId(pollId).withPollTitle(pollTitle).build();
            toPollResult(pollIdentifierData);
        }
    }
}
