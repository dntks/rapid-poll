package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollComment;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.newpoll.listviewholder.TextChangeAwareEditText;

public class FillPollCommentViewHolder extends FillPollViewHolderParent {

    private TextChangeAwareEditText editText;

    public FillPollCommentViewHolder(View parent) {
        super(parent);
        editText = (TextChangeAwareEditText) itemView.findViewById(R.id.comment_edittext);
    }

    @Override
    public void bindView(FillPollListItem fillPollListItem) {
        FillPollComment fillPollComment = (FillPollComment)fillPollListItem;

        editText.setTextChangedListener(new CommentTextChangedListener(fillPollComment));
        editText.setText(fillPollComment.getComment().or(""));

    }
}
