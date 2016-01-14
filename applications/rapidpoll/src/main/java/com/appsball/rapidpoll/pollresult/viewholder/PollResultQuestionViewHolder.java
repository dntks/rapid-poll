package com.appsball.rapidpoll.pollresult.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.newpoll.listviewholder.TextChangeAwareEditText;
import com.appsball.rapidpoll.pollresult.model.PollResultListItem;

public class PollResultQuestionViewHolder extends PollResultViewHolderParent {

    private TextView textView;

    public PollResultQuestionViewHolder(View parent) {
        super(parent);
        textView = (TextChangeAwareEditText) itemView.findViewById(R.id.comment_edittext);
    }

    @Override
    public void bindView(PollResultListItem pollResultListItem) {

    }

}