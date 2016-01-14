package com.appsball.rapidpoll.mypolls;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;

import static com.appsball.rapidpoll.RapidPollActivity.PUBLIC_POLL_CODE;

public class MyPollsItemClickListener implements PollItemClickListener {
    private final RapidPollActivity rapidPollActivity;

    public MyPollsItemClickListener(RapidPollActivity rapidPollActivity) {
        this.rapidPollActivity = rapidPollActivity;
    }

    public void pollItemClicked(SearchPollsItemData searchPollsItemData) {
        if (searchPollsItemData.state == PollState.DRAFT) {
            rapidPollActivity.toManagePoll(searchPollsItemData.id);
        } else {
            PollIdentifierData pollIdentifierData = PollIdentifierData.builder()
                    .withPollId(searchPollsItemData.id)
                    .withPollCode(PUBLIC_POLL_CODE)
                    .withPollTitle(searchPollsItemData.name).build();
            rapidPollActivity.toPollResult(pollIdentifierData);
        }
    }
}
