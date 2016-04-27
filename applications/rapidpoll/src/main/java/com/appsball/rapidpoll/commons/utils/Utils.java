/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appsball.rapidpoll.commons.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.orhanobut.hawk.Hawk;

import java.io.File;

/**
 * This class contains static utility methods.
 */
public class Utils {
    public static String ALPHABET="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getLetterOfAlphabet(int order){
        int alphabetSize= ALPHABET.length();
        int realOrder= order%alphabetSize;
        return ALPHABET.substring(realOrder, realOrder+1);
    }

    // Prevents instantiation.
    private Utils() {
    }

    public static final Joiner ON_SLASH_JOINER = Joiner.on("/");
    public static final Splitter ON_SLASH_SPLITTER = Splitter.on("/");

    public static boolean isOnline(final Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getMimeTypeOfFile(File file, Context context) {
        Uri uri = Uri.fromFile(file);
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver contentResolver = context.getContentResolver();
            mimeType = contentResolver.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static boolean isRegistered() {
        return !Constants.NO_ID.equals(Hawk.get(Constants.USER_ID_KEY, Constants.NO_ID));
    }

}
