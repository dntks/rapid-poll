package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.model.PollState;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.appsball.rapidpoll.searchpolls.view.SearchPollsItemViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;

import java.util.List;

public class MyPollsAdapter extends SimpleAdapter<SearchPollsItemData, SearchPollsItemViewHolder> {
    public static final int LISTITEM_DRAFT_GREY_COLOR = R.color.listitem_draft_grey;
    public static final int LISTITEM_BACKGROUND_COLOR = R.color.listitem_purple;
    public static final int SWIPE_CLOSE_TEXT_ID = R.string.close;
    public static final int SWIPE_REOPEN_TEXT_ID = R.string.reopen;

    private final PollItemClickListener pollItemClickListener;
    private final DateStringFormatter dateStringFormatter;
    private final PollCloser pollCloser;
    private final PollReopener pollReopener;

    public MyPollsAdapter(List<SearchPollsItemData> items,
                          PollItemClickListener pollItemClickListener,
                          DateStringFormatter dateStringFormatter,
                          PollCloser pollCloser,
                          PollReopener pollReopener) {
        super(items);
        this.pollItemClickListener = pollItemClickListener;
        this.dateStringFormatter = dateStringFormatter;
        this.pollCloser = pollCloser;
        this.pollReopener = pollReopener;
    }

    @Override
    public void onBindViewHolder(final UltimateRecyclerviewViewHolder viewHolder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= items.size() : position < items.size()) && (customHeaderView != null ? position > 0 : true)) {
            SearchPollsItemViewHolder holder = (SearchPollsItemViewHolder) viewHolder;
            int location = customHeaderView != null ? position - 1 : position;
            final SearchPollsItemData searchPollsItemData = items.get(location);

            holder.nameTextView.setText(searchPollsItemData.name);
            setDaysAgoText(holder, searchPollsItemData);

            holder.votesTextView.setText(searchPollsItemData.votesText);
            holder.answeredQuestionsBar.setProgress(searchPollsItemData.answeredQuestionsRatioFor100);
            setLocketImage(holder, searchPollsItemData);
            int backgroundColor = searchPollsItemData.state == PollState.DRAFT ? LISTITEM_DRAFT_GREY_COLOR : LISTITEM_BACKGROUND_COLOR;
            holder.listitemLayout.setBackgroundColor(holder.listitemLayout.getResources().getColor(backgroundColor));

            setSwipeViewProperties(holder, searchPollsItemData);

        }
    }

    private void setDaysAgoText(SearchPollsItemViewHolder holder, SearchPollsItemData searchPollsItemData) {
        if (searchPollsItemData.state == PollState.CLOSED) {
            if (searchPollsItemData.closedDate.isPresent()) {
                String closedDaysAgoText = dateStringFormatter.createClosedDaysAgoFormatFromDate(searchPollsItemData.closedDate.get());
                holder.startedTextView.setText(closedDaysAgoText);
            } else {
                holder.startedTextView.setText("");
            }
        } else {
            String startedDaysAgoText = dateStringFormatter.createStartedDaysAgoFormatFromDate(searchPollsItemData.publicationDate);
            holder.startedTextView.setText(startedDaysAgoText);
        }
    }

    private void setSwipeViewProperties(SearchPollsItemViewHolder holder, final SearchPollsItemData searchPollsItemData) {
        SwipeLayout swipeLayout = holder.swipeLayout;
        swipeLayout.findViewById(R.id.listitem_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollItemClickListener.pollItemClicked(searchPollsItemData);
            }
        });
        if (searchPollsItemData.state != PollState.DRAFT) {
            final boolean isInClosedState = searchPollsItemData.state == PollState.CLOSED;
            holder.swipeViewText.setText(isInClosedState ? SWIPE_REOPEN_TEXT_ID : SWIPE_CLOSE_TEXT_ID);
            holder.swipeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isInClosedState) {
                        pollReopener.reopenPoll(searchPollsItemData.id);
                    } else {
                        pollCloser.closePoll(searchPollsItemData.id);
                    }
                }
            });
        } else {
            swipeLayout.setRightSwipeEnabled(false);
            swipeLayout.setLeftSwipeEnabled(false);
        }
    }

    @Override
    public SearchPollsItemData getItem(int position) {
        return items.get(position);
    }


    @Override
    public SearchPollsItemViewHolder getViewHolder(View view) {
        return new SearchPollsItemViewHolder(view, false);
    }


    @Override
    public SearchPollsItemViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allpolls_item, parent, false);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_polls_swipeable_item, parent, false);
        SearchPollsItemViewHolder vh = new SearchPollsItemViewHolder(view, true);
        return vh;
    }

    @Override
    public long generateHeaderId(int position) {
        SearchPollsItemData itemData = getItem(position);
        return itemData.state.value;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mypolls_listheader, viewGroup, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position < items.size()) {
            SearchPollsItemData itemData = getItem(position);
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.headerTextView.setText(itemData.state.shownName);
        }
    }

    class HeaderViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTextView = (TextView) itemView.findViewById(R.id.header_text);
        }
    }
}
