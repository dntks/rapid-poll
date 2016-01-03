package com.appsball.rapidpoll.commons.view;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.Fragment;

import com.appsball.rapidpoll.RapidPollActivity;
import com.appsball.rapidpoll.commons.utils.Utils;

public class RapidPollFragment extends Fragment {

    protected boolean isNetDialogShownForGetPolls = false;

    public RapidPollActivity getRapidPollActivity() {
        return (RapidPollActivity) getActivity();
    }

    public boolean checkIsOnlineAndShowSimpleDialog() {
        if (!Utils.isOnline(getContext())) {
            DialogsBuilder.showNoNetConnectionDialog(getActivity(), new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return false;
        }
        return true;
    }

    public boolean checkIsOnlineAndShowSimpleDialog(OnClickListener onClickListener) {
        if (!Utils.isOnline(getContext())) {
            DialogsBuilder.showNoNetConnectionDialog(getActivity(), onClickListener);
            isNetDialogShownForGetPolls = true;
            return false;
        }
        isNetDialogShownForGetPolls = false;
        return true;
    }
}
