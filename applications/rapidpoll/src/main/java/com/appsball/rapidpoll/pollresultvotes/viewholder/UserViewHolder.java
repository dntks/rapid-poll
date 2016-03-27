package com.appsball.rapidpoll.pollresultvotes.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresultvotes.model.UserListItem;

public class UserViewHolder extends ResultVotesViewHolderParent<UserListItem>{
    private TextView emailTextView;

    public UserViewHolder(View parent) {
        super(parent);
        emailTextView = (TextView) itemView.findViewById(R.id.email_textview);
    }

    @Override
    public void bindView(UserListItem userListItem) {
        emailTextView.setText(userListItem.email);
    }
}
