package com.appsball.rapidpoll.allpolls;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

public class AllPollsItemViewHolder extends UltimateRecyclerviewViewHolder {

    TextView nameView;

    public AllPollsItemViewHolder(View itemView, boolean isItem) {
        super(itemView);
        if (isItem) {
            nameView = (TextView) itemView.findViewById(R.id.pollitem_text);
        }
    }
}
