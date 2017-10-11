package com.info121.ioperation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.info121.constant.Constants;
import com.info121.ioperation.ChooseTaskActivity;
import com.info121.ioperation.R;
import com.info121.ioperation.util.Util;

import java.util.Hashtable;

/**
 * Created by archirayan1 on 3/30/2016.
 */
public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private Button btnSubmit;
    private String oldPwd,newPwd;
    private EditText etParam1, etParam3, etParam4;


    private ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forgot_password_layout,container,false);
        context = getActivity();
        btnSubmit = (Button) v.findViewById(R.id.buttonForgotPasswordSubmit);
        btnSubmit.setOnClickListener(this);

        etParam1 = (EditText) v.findViewById(R.id.EditTextForgotPasswordReType);
        etParam3 = (EditText) v.findViewById(R.id.EditTextForgotPasswordOldPassword);
        etParam4 = (EditText) v.findViewById(R.id.EditTextForgotPasswordNewPassword);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.buttonForgotPasswordSubmit:

                String pass = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.PASSWORD_KEY, "");

                if(etParam3.getText().toString().compareToIgnoreCase(pass)==0){

                    if(etParam4.getText().toString().compareToIgnoreCase(etParam1.getText().toString())==0)
                        new ForgotPasswordAsyncTask().execute();
                    else
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
            parameter.put("iopsuser", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            parameter.put("TelNo", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.HANDPHONE_KEY, ""));
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
            }else{
                Util.showAlertCommonDialogWithOKBtn(context, "Error","Error");
            }
        }
    }
}
