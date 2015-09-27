package com.appsball.votenow.communication;

import java.io.InputStream;

import android.content.Context;
import android.util.Log;

public class OutputGetQuestion extends OutputAncestor {

	public OutputGetQuestion(Context context, InputStream stream) {
		super(context, stream);
		Log.e("answer: ", " " + answer);

	}

}
