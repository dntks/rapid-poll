package com.appsball.rapidpoll.pollresult.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresult.model.CommentItem;
import com.appsball.rapidpoll.pollresult.model.PollResultListItem;

public class PollResultCommentHolder extends PollResultViewHolderParent {
    private TextView textView;

    public PollResultCommentHolder(View parent) {
        super(parent);
        textView = (TextView) itemView.findViewById(R.id.comment_text);
    }

    @Override
    public void bindView(PollResultListItem pollResultListItem) {

        final CommentItem pollResultQuestionItem = (CommentItem) pollResultListItem;
        String commentText = pollResultQuestionItem.text;
        if (pollResultQuestionItem.email.isPresent()) {
            commentText = pollResultQuestionItem.email.get() + ": " + pollResultQuestionItem.text;
        }
        textView.setText(commentText);
    }

}