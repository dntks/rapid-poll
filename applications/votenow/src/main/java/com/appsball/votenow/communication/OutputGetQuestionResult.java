package com.appsball.votenow.communication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class OutputGetQuestionResult extends OutputAncestor {

	private String question;
	private String numberOfRates;
	private String avarage;
	private String median;
	private String modus;
	private String sdeviation;

	private final List<String> rateStrings = new ArrayList<String>();
	private final List<List<String>> messages = new ArrayList<List<String>>();

	public OutputGetQuestionResult(Context context, InputStream stream) {
		super(context, stream);
	}

}
