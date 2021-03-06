package com.appsball.rapidpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.appsball.rapidpoll.allpolls.AllPollsFragment;
import com.appsball.rapidpoll.commons.model.ResultAlternativeDetails;
import com.appsball.rapidpoll.commons.utils.Constants;
import com.appsball.rapidpoll.fillpoll.FillPollFragment;
import com.appsball.rapidpoll.mypolls.MyPollsFragment;
import com.appsball.rapidpoll.newpoll.ManagePollFragment;
import com.appsball.rapidpoll.pollresult.PollResultFragment;
import com.appsball.rapidpoll.pollresultvotes.ResultVotesFragment;
import com.appsball.rapidpoll.results.ResultsFragment;
import com.google.common.collect.Lists;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import static com.appsball.rapidpoll.commons.utils.Constants.POLL_CODE;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_ID;
import static com.appsball.rapidpoll.commons.utils.Constants.POLL_TITLE;

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

    public void toResultVotes(PollIdentifierData pollIdentifierData, List<ResultAlternativeDetails> resultAlternativeDetailsList) {
        final Fragment fragment = new ResultVotesFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.POLL_ID_DATA, pollIdentifierData);
        arguments.putParcelableArrayList(Constants.RESULT_ANSWERS, Lists.newArrayList(resultAlternativeDetailsList));
        fragment.setArguments(arguments);
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
        String fragmentName = extras.getString(Constants.FRAGMENT_NAME);
        String pollTitle = extras.getString(Constants.POLL_TITLE);
        String pollCode = extras.getString(Constants.POLL_CODE);
        String pollId = extras.getString(Constants.POLL_ID);
        Hawk.get(pollId);
        if(ScreenFragment.MY_POLLS.apiName.equals(fragmentName)){
            toMyPolls();
        }
        else if (ScreenFragment.POLL_RESULT.apiName.equals(fragmentName)) {
            PollIdentifierData pollIdentifierData = PollIdentifierData.builder().withPollCode(pollCode).withPollId(pollId).withPollTitle(pollTitle).build();
            toPollResult(pollIdentifierData);
        } else if (ScreenFragment.FILL_POLL.apiName.equals(fragmentName)) {
            PollIdentifierData pollIdentifierData = PollIdentifierData.builder().withPollCode(pollCode).withPollId(pollId).withPollTitle(pollTitle).build();
            toFillPoll(pollIdentifierData);
        } else {
            toAllPolls();
        }
    }
}
