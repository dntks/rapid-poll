package com.appsball.rapidpoll.commons.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.appsball.rapidpoll.R;


public class CircleLoadingView extends LinearLayout {

	public CircleLoadingView(Context context) {
		super(context);
		init();
	}

	public CircleLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.loadingview, this);
	}

}
