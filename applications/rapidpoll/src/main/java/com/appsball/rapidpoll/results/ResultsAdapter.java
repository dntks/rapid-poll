package com.appsball.rapidpoll.results;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.listeners.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.appsball.rapidpoll.searchpolls.view.SearchPollsItemViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

public class ResultsAdapter extends SimpleAdapter<SearchPollsItemData, SearchPollsItemViewHolder> {
    private final PollItemClickListener pollItemClickListener;
    private final DateStringFormatter dateStringFormatter;

    public ResultsAdapter(List<SearchPollsItemData> items,
                          PollItemClickListener pollItemClickListener,
                          DateStringFormatter dateStringFormatter) {
        super(items);
        this.pollItemClickListener = pollItemClickListener;
        this.dateStringFormatter = dateStringFormatter;
    }

    @Override
    public void onBindViewHolder(final UltimateRecyclerviewViewHolder viewHolder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= items.size() : position < items.size()) && (customHeaderView != null ? position > 0 : true)) {
            SearchPollsItemViewHolder holder = (SearchPollsItemViewHolder) viewHolder;
            int location = customHeaderView != null ? position - 1 : position;
            final SearchPollsItemData searchPollsItemData = items.get(location);
            holder.nameTextView.setText(searchPollsItemData.name);
            holder.votesTextView.setText(searchPollsItemData.votesText);
            holder.answeredQuestionsBar.setProgress(searchPollsItemData.answeredQuestionsRatioFor100);
            setLocketImage(holder, searchPollsItemData);

            setDaysAgoText(holder, searchPollsItemData);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pollItemClickListener.pollItemClicked(searchPollsItemData);
                }
            });
        }
    }

    private void setDaysAgoText(SearchPollsItemViewHolder holder, SearchPollsItemData searchPollsItemData) {
        if (searchPollsItemData.closedDate.isPresent()) {
            String publicatedDaysAgoText = dateStringFormatter.createClosedDaysAgoFormatFromDate(searchPollsItemData.closedDate.get());
            holder.startedTextView.setText(publicatedDaysAgoText);
        }
        else{
            holder.startedTextView.setText("");
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
        SearchPollsItemViewHolder vh = new SearchPollsItemViewHolder(v, true);
        return vh;
    }

    @Override
    public long generateHeaderId(int position) {
        if (getItem(position).name.length() > 0) {
            return getItem(position).name.charAt(0);
        } else return -1;
    }
}