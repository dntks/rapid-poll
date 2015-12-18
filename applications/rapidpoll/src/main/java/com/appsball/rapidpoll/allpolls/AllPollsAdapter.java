package com.appsball.rapidpoll.allpolls;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsball.rapidpoll.R;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class AllPollsAdapter extends SimpleAdapter<AllPollsItemData, AllPollsItemViewHolder> {

    public static final int OPENED_LOCKET = R.drawable.nyitottlakat;
    public static final int CLOSED_LOCKET = R.drawable.lakat;
    public static final int RIGHT_ARROW = R.drawable.jobbranyil;

    public AllPollsAdapter(List<AllPollsItemData> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(final AllPollsItemViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= items.size() : position < items.size()) && (customHeaderView != null ? position > 0 : true)) {

            int location = customHeaderView != null ? position - 1 : position;
            AllPollsItemData allPollsItemData = items.get(location);
            holder.nameTextView.setText(allPollsItemData.name);
            holder.startedTextView.setText(allPollsItemData.publicatedDaysAgoText);
            holder.votesTextView.setText(allPollsItemData.votesText);
            holder.answeredQuestionsBar.setProgress(allPollsItemData.answeredQuestionsRatioFor100);
            if (!allPollsItemData.isPublic) {
                int locketImageId = Hawk.contains(allPollsItemData.id) ? OPENED_LOCKET : CLOSED_LOCKET;
                holder.itemRightImage.setImageResource(locketImageId);
            } else {
                holder.itemRightImage.setImageResource(RIGHT_ARROW);
            }
        }
    }

    @Override
    public AllPollsItemData getItem(int position) {
        return items.get(position);
    }


    @Override
    public AllPollsItemViewHolder getViewHolder(View view) {
        return new AllPollsItemViewHolder(view, false);
    }

    @Override
    public AllPollsItemViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allpolls_item, parent, false);
        AllPollsItemViewHolder vh = new AllPollsItemViewHolder(v, true);
        return vh;
    }

    @Override
    public long generateHeaderId(int position) {
        if (getItem(position).name.length() > 0) {
            return getItem(position).name.charAt(0);
        } else return -1;
    }
}
