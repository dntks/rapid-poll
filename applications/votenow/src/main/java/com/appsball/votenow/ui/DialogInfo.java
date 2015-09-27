package com.appsball.votenow.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogInfo extends DialogFragment {

	public static final String TAG = "INFO_DIALOG_TAG";

	public DialogInfo() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.infopage, container);
		getDialog().setTitle(getResources().getString(R.string.info_title));

		TextView tv = (TextView) view.findViewById(R.id.help_text);
		Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES);
		tv.setLinkTextColor(Color.BLACK);

		return view;
	}

	public static void showInfoDialog(FragmentManager supportFragmentManager) {
		DialogInfo editNameDialog = new DialogInfo();
		editNameDialog.show(supportFragmentManager, DialogInfo.TAG);
	}

	public static void showInfoDialogOnFirstRun(FragmentManager supportFragmentManager, Context context) {
		SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		if (!sp.getBoolean(TAG, false)) {
			showInfoDialog(supportFragmentManager);
			sp.edit().putBoolean(TAG, true).commit();
		}
	}
}
