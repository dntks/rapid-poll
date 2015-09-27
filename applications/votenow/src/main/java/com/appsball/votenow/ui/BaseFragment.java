package com.appsball.votenow.ui;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	public boolean doBackButtonAction() {
		return false;
	}

	public String getTitle() {
		return "Vote now!";
	}
}