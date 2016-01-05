package com.appsball.rapidpoll.mypolls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.utils.DateStringFormatter;
import com.appsball.rapidpoll.searchpolls.PollItemClickListener;
import com.appsball.rapidpoll.searchpolls.SimpleAdapter;
import com.appsball.rapidpoll.searchpolls.model.SearchPollsItemData;
import com.appsball.rapidpoll.searchpolls.view.SearchPollsItemViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class MyPollsAdapter extends SimpleAdapter<SearchPollsItemData, SearchPollsItemViewHolder> {
    private final PollItemClickListener pollItemClickListener;
    private final DateStringFormatter dateStringFormatter;
    public static final int OPENED_LOCKET = R.drawable.nyitottlakat;
    public static final int CLOSED_LOCKET = R.drawable.lakat;
    public static final int RIGHT_ARROW = R.drawable.jobbranyil;

    public MyPollsAdapter(List<SearchPollsItemData> items,
                          PollItemClickListener pollItemClickListener,
                          DateStringFormatter dateStringFormatter) {
        super(items);
        this.pollItemClickListener = pollItemClickListener;
        this.dateStringFormatter = dateStringFormatter;
    }

    @Override
    public void onBindViewHolder(final SearchPollsItemViewHolder holder, int position) {
        if (position < getItemCount() && (position < items.size())) {
            int location = customHeaderView != null ? position - 1 : position;
            final SearchPollsItemData searchPollsItemData = items.get(location);
            holder.nameTextView.setText(searchPollsItemData.name);
            //TODO: no closed date in server api yet
            String publicatedDaysAgoText = dateStringFormatter.createClosedDaysAgoFormatFromDate(searchPollsItemData.publicationDate);
            holder.startedTextView.setText(publicatedDaysAgoText);
            holder.votesTextView.setText(searchPollsItemData.votesText);
            holder.answeredQuestionsBar.setProgress(searchPollsItemData.answeredQuestionsRatioFor100);
            if (!searchPollsItemData.isPublic) {
                int locketImageId = Hawk.contains(searchPollsItemData.id) ? OPENED_LOCKET : CLOSED_LOCKET;
                holder.itemRightImage.setImageResource(locketImageId);
            } else {
                holder.itemRightImage.setImageResource(RIGHT_ARROW);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pollItemClickListener.pollItemClicked(searchPollsItemData);
                }
            });
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
        SearchPollsItemData itemData = getItem(position);
        HeaderViewHolder holder = (HeaderViewHolder)viewHolder;
        holder.headerTextView.setText(itemData.state.toString());

    }
    class HeaderViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTextView = (TextView) itemView.findViewById(R.id.header_text);
        }
    }
}
