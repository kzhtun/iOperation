package com.info121.ioperations.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperations.ChooseTaskActivity;
import com.info121.ioperations.R;
import com.info121.ioperations.fragment.iBiddingFragment;
import com.info121.ioperations.iOPLoginActivity;
import com.info121.ioperations.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

//Server IP : 203.125.95.176
//Web Service address : http://203.125.95.176/iops
//User Name : Jeffrey
//Supplier Code: SHM-OPT
//Tel No: 12345678
//Password: 12345

public class UpdateBidServerDialog extends Dialog {

	private static UpdateBidServerDialog error = null;

	private ProgressDialog progress;
	Context context;
	
	private Activity act;
	

	protected UpdateBidServerDialog(final Context context, final Activity act,final iBiddingFragment fragment, boolean isVisible) {
		
		super(context);
		this.context = context;
		this.act = act;
		this.fragment = fragment;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_server_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		
		((EditText)findViewById(R.id.editTextServerNo1)).setText(Constants.SERVER_URL);
		((EditText)findViewById(R.id.editTextServerNo2)).setText(Constants.BASE_PHOTO_URL);
	
		findViewById(R.id.ButtonServerUpdate).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(((EditText)findViewById(R.id.EditTextUpdateServerPassword)).getText().toString().compareTo("KJyN33EwZc")==0){
				
				SimpleDateFormat s = new SimpleDateFormat("dd MMM yyyy, hhmm aa");
				String update_time = s.format(new Date());
				
				SharedPreferences settings = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE);
			    settings.edit().clear().commit();
				
				SharedPreferences preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(Util.SERVER_LAST_UPDATED_KEY, update_time);
				editor.putString(Util.SERVER_ONE_KEY, ((EditText)findViewById(R.id.editTextServerNo1)).getText().toString());
				editor.putString(Util.SERVER_TWO_KEY, ((EditText)findViewById(R.id.editTextServerNo2)).getText().toString());
				editor.commit();
				
				
				Constants.SERVER_URL = ((EditText)findViewById(R.id.editTextServerNo1)).getText().toString();
				Constants.BASE_URL = "http://" + Constants.SERVER_URL;
				Constants.BASE_PHOTO_URL = ((EditText)findViewById(R.id.editTextServerNo2)).getText().toString();
				
				Constants.reload();
				
				dismiss();
				//fragment.CallTheJob();
				
				Intent intent = new Intent(act.getApplicationContext(), iOPLoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			    context.startActivity(intent);
			    act.finish();
			    ChooseTaskActivity.thisFragment.finish();
				}else{
					Util.showAlertCommonDialogWithOKBtn(context, "Error","Password is not match");
				}
			}
		});

		
		findViewById(R.id.ButtonServerClose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		((TextView)findViewById(R.id.TextViewServerLastUpdated)).setText("Last Updated : "+context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SERVER_LAST_UPDATED_KEY, "-"));

		
		
	}
	
	iBiddingFragment fragment;
	public static void show(Context context, Activity act, iBiddingFragment fragment, boolean isVisible) {
		error = new UpdateBidServerDialog(context, act,fragment,isVisible);
		error.show();
	}
	



}
