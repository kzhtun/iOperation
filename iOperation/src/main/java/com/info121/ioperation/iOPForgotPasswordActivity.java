package com.info121.ioperation;

import java.util.Hashtable;
import com.info121.constant.Constants;
import com.info121.ioperation.util.Util;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class iOPForgotPasswordActivity extends RootWithIdleCheckActivity implements OnClickListener {

	private Context context;
	private Button btnSubmit;
	private String oldPwd,newPwd;
	private EditText etParam1, etParam3, etParam4;


	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgot_password_layout);
		context = this;


		btnSubmit = (Button) findViewById(R.id.buttonForgotPasswordSubmit);
		btnSubmit.setOnClickListener(this);

		etParam1 = (EditText) findViewById(R.id.EditTextForgotPasswordReType);
		etParam3 = (EditText) findViewById(R.id.EditTextForgotPasswordOldPassword);
		etParam4 = (EditText) findViewById(R.id.EditTextForgotPasswordNewPassword);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {


		case R.id.buttonForgotPasswordSubmit:

			String pass = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.PASSWORD_KEY, "");

			if(etParam3.getText().toString().compareToIgnoreCase(pass)==0){

				if(etParam4.getText().toString().compareToIgnoreCase(etParam1.getText().toString())==0) {
                    oldPwd = etParam3.getText().toString();
                    newPwd = etParam4.getText().toString();
                    new ForgotPasswordAsyncTask().execute();
                }else
				Util.showAlertCommonDialogWithOKBtn(context, "Error","New & Re Type Password is not matched");

			}else{
				Util.showAlertCommonDialogWithOKBtn(context, "Error","Old Password is wrong");
			}

			break;

		}

	}


	 private class ForgotPasswordAsyncTask extends AsyncTask<Void, Void, String> {
	        @Override
	        protected void onPreExecute() {
	        	progress = ProgressDialog.show(context, "Process",
	        		    "Loading . . .", true);
	        }

			@Override
			protected String doInBackground(Void... v) {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
				parameter.put("iopsuser", getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
				parameter.put("TelNo", getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.HANDPHONE_KEY, ""));
                parameter.put("OldPassword",oldPwd );
				parameter.put("NewPassword", newPwd);

				return Util.connectSOAP(Constants.uRLiOPsUserChangePassword,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionUserChangePassword, Constants.APIDotNetMethodNameUserChangePassword, parameter);
			}
	        @Override
	        protected void onPostExecute(String result) {
	        	progress.dismiss();
	        	if(result!=null){

	        		SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString(Util.PASSWORD_KEY, etParam4.getText().toString());
					editor.commit();
	        		Util.showAlertCommonDialogWithOKBtn(ChooseTaskActivity.context, "Success","Password succesfully changed");
	        		finish();

	        	}else{

	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Error");
	        	}
	        }
	  }
}
