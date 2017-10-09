package com.info121.ioperations;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

//import com.google.android.gcm.GCMRegistrar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.info121.constant.Constants;
import com.info121.ioperations.dialog.UpdateLoginServerDialog;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;

import java.util.Hashtable;

public class iOPLoginActivity extends RootActivity implements OnClickListener {

    private Button btnRegister;
    private Button btnLogin;
    private Button buttonLoginChangeServer;
    private EditText ETUsername;
    private EditText ETPassword;
    private EditText ETTelpNo;
    private static EditText ETRegId;

    private ProgressDialog progress;

    private String regId = "";

    AsyncTask<Void, Void, Void> mRegisterTask;

    public static Context context;

    public static iOPLoginActivity thisAct;

    private String uName, uPhone, uPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        thisAct = thisAct;
        context = this;


        Constants.SERVER_URL = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.SERVER_ONE_KEY, Constants.SERVER_URL);
        Log.e("SERVER_URL", "" + Constants.SERVER_URL);
        Constants.BASE_URL = "http://" + Constants.SERVER_URL;
        Log.e("BASE_URL", "" + Constants.BASE_URL);
        Constants.BASE_PHOTO_URL = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.SERVER_TWO_KEY, Constants.SERVER_URL);
        Log.e("BASE_PHOTO_URL", "" + Constants.BASE_PHOTO_URL);
        Constants.reload();

        //GCMIntentService.generateNotification(context, "testing notification");
        String checkLogin = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
        boolean isExit = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getBoolean(Util.EXIT_KEY, false);

        if (isExit) {

            SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Util.EXIT_KEY, false);
            editor.commit();

            finish();

        }


        if (!(checkLogin.compareToIgnoreCase("") == 0)) {
            finish();
            Intent intent = new Intent(iOPLoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        setContentView(R.layout.login_layout);

        btnRegister = (Button) findViewById(R.id.buttonLoginRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.buttonLoginLogin);
        btnLogin.setOnClickListener(this);
        buttonLoginChangeServer = (Button) findViewById(R.id.buttonLoginChangeServer);
        buttonLoginChangeServer.setOnClickListener(this);

        ETUsername = (EditText) findViewById(R.id.editTextUsername);
        ETPassword = (EditText) findViewById(R.id.editTextPassword);
        ETTelpNo = (EditText) findViewById(R.id.editTextTelNo);
        ETRegId = (EditText) findViewById(R.id.EditTextGCMRegId);

	/*	ETUsername.setText("BABA");
        ETTelpNo.setText("90254814");
		ETPassword.setText("121");*/


        //TODO: to delete when release
        ETUsername.setText("Henry Tan");
        ETTelpNo.setText("97568467");
        ETPassword.setText("121");


		
		
	/*	 // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver,
                new IntentFilter(Util.DISPLAY_MESSAGE_ACTION));
       
       
        
        regId = GCMRegistrar.getRegistrationId(context);
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(context, Util.SENDER_ID);
        } else {
        	
        	Util.gcm_register_id = regId;
        	ETRegId.setText(regId);
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(context)) {
                // Skips registration.
            	Log.v(Util.TAG,"already registered");
                //mDisplay.append(getString(R.string.already_registered) + "\n");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
               // final Context context = this;
//                mRegisterTask = new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        boolean registered =
//                                Util.register(context, regId);
//                        // At this point all attempts to register with the app
//                        // server failed, so we need to unregister the device
//                        // from GCM - the app will try to register again when
//                        // it is restarted. Note that GCM will send an
//                        // unregistered callback upon completion, but
//                        // GCMIntentService.onUnregistered() will ignore it.
//                        if (!registered) {
//                            GCMRegistrar.unregister(context);
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void result) {
//                        mRegisterTask = null;
//                    }
//
//                };
//                mRegisterTask.execute(null, null, null);
            }
        }
		*/
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.buttonLoginLogin:

                uName = ETUsername.getText().toString();
                uPhone = ETTelpNo.getText().toString();
                uPwd = ETPassword.getText().toString();





                if (checkLoginFields())
                    new LoginAsyncTask().execute();

//			intent = new Intent(iOPLoginActivity.this, NoShowClientActivity.class); 
//            startActivity(intent);

                break;

            case R.id.buttonLoginRegister:

                intent = new Intent(iOPLoginActivity.this, iOPRegisterActivity.class);
                startActivity(intent);


                break;


            case R.id.buttonLoginChangeServer:

                UpdateLoginServerDialog.show(context, this, true);


                break;


        }

    }


    private class LoginAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "Login",
                    "Loading . . .", true);
        }

        @Override
        protected String doInBackground(Void... v) {

            // KZHTUN remove GCM Related
            //regId = GCMRegistrar.getRegistrationId(context);

            regId = FirebaseInstanceId.getInstance().getToken();
            Util.gcm_register_id = regId;

            Log.e("FIREBASE TOKEN : " , regId);

            Hashtable<String, String> parameter = new Hashtable<String, String>();
            parameter.put("iopsuser", uName);
            parameter.put("TelNo", uPhone);
            parameter.put("Password", uPwd);
            parameter.put("RegID", Util.gcm_register_id);
            Log.v("MAU AMBIL REG ID", "MAU AMBIL REG ID");
            Log.v("REG ID IOP", Util.gcm_register_id);
            //parameter.put("RegID", "Test");
            return Util.connectSOAP(Constants.uRLValidateIOPSUser, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionValidateIOPSUser, Constants.APIDotNetMethodNameValidateIOPSUser, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();


            Log.d("msg", "ioperatonlogin  res " + result);
            if (result != null) {

                try {

                    if (Parser.getJSONValidateUserResult(result).compareToIgnoreCase("yes") == 0) {

                        SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Util.LOGIN_KEY, ETUsername.getText().toString());
                        editor.putString(Util.SUPPLIER_KEY, Parser.getJSONValidateUserSupplierResult(result));
                        editor.putString(Util.HANDPHONE_KEY, ETTelpNo.getText().toString());
                        editor.putString(Util.PASSWORD_KEY, ETPassword.getText().toString());
                        editor.commit();

//						Intent intent = new Intent(iOPLoginActivity.this, ChooseTaskActivity.class);

                        Intent intent = new Intent(iOPLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Util.showAlertCommonDialogWithOKBtn(context, "Error Login", "Error Login");
                    }
                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error Login", "Error Login");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error Login", "Error Login");

            }

        }
    }


    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
           /* unregisterReceiver(mHandleMessageReceiver);
	        try {
				GCMRegistrar.onDestroy(this);
			} catch (Exception e) {

			}*/
        super.onDestroy();
    }

    private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String newMessage = intent.getExtras().getString(Util.EXTRA_MESSAGE);

                }
            };

    public boolean checkLoginFields() {


        if (ETUsername.getText().toString().trim().compareTo("") == 0 ||
                ETPassword.getText().toString().trim().compareTo("") == 0 ||
                ETTelpNo.getText().toString().trim().compareTo("") == 0) {
            Util.showAlertCommonDialogWithOKBtn(context, "Error Login", "Please fills all fields");
            return false;
        }
        return true;
    }

    public static void updateGCMText() {

        if (ETRegId != null && thisAct != null)
            thisAct.runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    ETRegId.setText(Util.gcm_register_id);
                }
            }));


    }

    @Override
    public void onBackPressed() {
        System.exit(1);
    }


}
