package com.appsball.rapidpoll.commons.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.appsball.rapidpoll.PollIdentifierData;
import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.ScreenFragment;

import java.io.File;

import static com.appsball.rapidpoll.commons.utils.Utils.ON_SLASH_JOINER;

public class PollSharer {

    private final Context context;

    public PollSharer(Context context) {
        this.context = context;
    }

    public void shareFileInIntent(File file, PollIdentifierData pollIdentifierData) {
        Uri contentUri = FileProvider.getUriForFile(context, "com.appsball.rapidpoll.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType(Utils.getMimeTypeOfFile(file, context));
        String shareString = String.format(context.getString(R.string.shareStatisticsText), pollIdentifierData.pollTitle);
        String pollResultLink = createLinkForScreen(ScreenFragment.POLL_RESULT, pollIdentifierData);
        String shareText = shareString + pollResultLink;
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, "Share poll result"));
    }

    public void inviteFriendsForPoll(PollIdentifierData pollIdentifierData, String shareString) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String pollResultLink = createLinkForScreen(ScreenFragment.FILL_POLL, pollIdentifierData);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString + " " + pollResultLink);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Share poll"));
    }


    private String createLinkForScreen(ScreenFragment screenFragment, PollIdentifierData pollIdentifierData) {
        return ON_SLASH_JOINER.join(" rapidpoll.appsball.com",
                screenFragment.apiName,
                pollIdentifierData.pollId,
                pollIdentifierData.pollCode);
    }
}
