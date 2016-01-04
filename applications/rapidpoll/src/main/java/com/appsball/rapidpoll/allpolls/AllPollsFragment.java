package com.appsball.rapidpoll.allpolls;

import com.appsball.rapidpoll.commons.communication.request.enums.ListType;
import com.appsball.rapidpoll.commons.model.NavigationButton;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.SearchPollsFragment;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.google.common.collect.Lists;

public class AllPollsFragment extends SearchPollsFragment {

    private AllPollsAdapter allPollsAdapter;

    @Override
    protected NavigationButton getActiveButton() {
        return NavigationButton.POLLS_BUTTON;
    }

    @Override
    protected PollItemClickListener createPollItemClickListener() {
        return new AllPollsItemClickListener(getRapidPollActivity(), requestCreator, service);
    }

    @Override
    protected void createSearchPollsAdapter(PollItemClickListener pollItemClickListener) {
        allPollsAdapter = new AllPollsAdapter(Lists.<SearchPollsItemData>newArrayList(), pollItemClickListener);
    }

    @Override
    protected SimpleAdapter getSearchPollsAdapter() {
        return allPollsAdapter;
    }

    @Override
    protected ListType getListType() {
        return ListType.ALL;
    }
}
