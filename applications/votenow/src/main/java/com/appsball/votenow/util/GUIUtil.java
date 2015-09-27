package com.appsball.votenow.util;

import com.appsball.votenow.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager.BadTokenException;

public class GUIUtil {
	
	private static ProgressDialog dialog; 
	
	public static ProgressDialog showProgressDialog(Context mContext) 
	{
		if(dialog != null)
		{
			hideProgressDialog();
		}
		dialog = new ProgressDialog(mContext);
		try 
		{
			dialog.show();
		} 
		catch (BadTokenException e) {}
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.progressdialog);
		return dialog;
	}
	
	public static void hideProgressDialog()
	{
		dialog.hide();
		dialog = null;
	}

	public static void showAlert(Context context, CharSequence title) 
	{
		new AlertDialog.Builder(context).setMessage(title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() 
		{
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        }
	     })
	     .show();
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setBackgroundForView(View v, Drawable d)
	{

		int sdk = android.os.Build.VERSION.SDK_INT; 
		
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
		   v.setBackgroundDrawable(d);
		} else {
		   v.setBackground(d); 
		   
		}
		
	}
}
