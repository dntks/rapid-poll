package com.appsball.rapidpoll.mypolls;

import com.appsball.rapidpoll.FragmentSwitcher;
import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.orhanobut.hawk.Hawk;

import static com.appsball.rapidpoll.commons.utils.Constants.PUBLIC_POLL_CODE;

public class MyPollsItemClickListener implements PollItemClickListener {
    private final FragmentSwitcher fragmentSwitcher;

    public MyPollsItemClickListener(FragmentSwitcher fragmentSwitcher) {
        this.fragmentSwitcher = fragmentSwitcher;
    }

    public void pollItemClicked(SearchPollsItemData searchPollsItemData) {
        if (searchPollsItemData.state == PollState.DRAFT) {
            fragmentSwitcher.toManagePoll(searchPollsItemData.id);
        } else {
            String code = PUBLIC_POLL_CODE;
            if (Hawk.contains(searchPollsItemData.id)) {
                code = Hawk.get(searchPollsItemData.id);
            }
            PollIdentifierData pollIdentifierData = PollIdentifierData.builder()
                    .withPollId(searchPollsItemData.id)
                    .withPollCode(code)
                    .withPollTitle(searchPollsItemData.name).build();
            fragmentSwitcher.toPollResult(pollIdentifierData);
        }
    }
}
