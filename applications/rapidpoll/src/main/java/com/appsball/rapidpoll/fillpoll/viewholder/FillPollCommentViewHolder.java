package com.appsball.rapidpoll.fillpoll.viewholder;

import android.view.View;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.fillpoll.model.FillPollComment;
import com.appsball.rapidpoll.fillpoll.model.FillPollListItem;
import com.appsball.rapidpoll.newpoll.listviewholder.TextChangeAwareEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillPollCommentViewHolder extends FillPollViewHolderParent {

    @BindView(R.id.comment_edittext) TextChangeAwareEditText editText;

    public FillPollCommentViewHolder(View parent) {
        super(parent);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(FillPollListItem fillPollListItem) {
        FillPollComment fillPollComment = (FillPollComment)fillPollListItem;

        editText.setTextChangedListener(new CommentTextChangedListener(fillPollComment));
        editText.setText(fillPollComment.getComment().or(""));

    }
}
