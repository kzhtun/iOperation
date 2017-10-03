package com.info121.ioperation.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.MainActivity;
import com.info121.ioperation.R;
import com.info121.ioperation.fragment.iOperationFragment;
import com.info121.ioperation.util.Constant;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.ReAssign_Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class ReAssignDialog extends Dialog {

	private static ReAssignDialog error = null;

	private ProgressDialog progress;
	Context context;
	
	private Activity act;
	private Spinner spinReAssignList;
	private String jobno;
	private ArrayList<ReAssign_Model> rmlist;
	private TextView tvCompany;

	protected ReAssignDialog(final Context context, final Activity act, String jobno,iOperationFragment fragment) {
		
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.act = act;
		this.jobno = jobno;
		this.fragment = fragment;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reassign_dialog);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().getAttributes().dimAmount = 0.7f;
		
		spinReAssignList = (Spinner) findViewById(R.id.spinnerListOfContacts);
		tvCompany = (TextView) findViewById(R.id.textview);
		tvCompany.setText("Company : "+context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));

		findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new ReassignAsyncTask().execute();
			}
		});
		
		
		new GetReassignListAsyncTask().execute();
	
	}


	protected ReAssignDialog(Context context,String jobno) {

		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.jobno = jobno;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reassign_dialog);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().getAttributes().dimAmount = 0.7f;

		spinReAssignList = (Spinner) findViewById(R.id.spinnerListOfContacts);
		tvCompany = (TextView) findViewById(R.id.textview);
		tvCompany.setText("Company : "+context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));

		findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new ReassignAsyncTask().execute();
			}
		});


		new GetReassignListAsyncTask().execute();

	}



	
	iOperationFragment fragment;
	public static void show(Context context, Activity act, String jobno, iOperationFragment fragment) {
		error = new ReAssignDialog(context, act,jobno,fragment);
		error.show();
	}

	public static void show(Context context, String jobno) {
		error = new ReAssignDialog(context,jobno);
		Log.e("jobno",""+jobno);
		Log.e("ERROR",""+error);
		error.show();
	}
	
	
	public class GetReassignListAsyncTask extends AsyncTask<Void, Void, String> {
		boolean error = false;
		boolean timeout = false;

		Context ctx;

		public void setCtx(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		protected void onPreExecute() {
//			progress = ProgressDialog.show(context, "ReAssign",
//        		    "Loading . . .", true);
		}

		@Override
		protected String doInBackground(Void... params) {
			
			try {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
				parameter.put("SupplierCode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
				return Util.connectSOAP(Constants.uRLiOpsGetSupplierUserListToAssigned,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierUserListToAssigned, Constants.APIDotNetMethodNameGetSupplierUserListToAssigned, parameter);
			} catch (Exception e) {
				timeout = true;
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
//			progress.dismiss();
				
				if(result!=null){
	        		
	        		try {
	        			
	        			rmlist = Parser.getJSONReAssignUserListResult(result);
	        			
	        			List<String> list = new ArrayList<String>();
	        			for(int i=0;i<rmlist.size();i++)
	        	 		list.add(rmlist.get(i).getUsername());
	        			
						ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
								context, android.R.layout.simple_spinner_item, list);
						dataAdapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinReAssignList.setAdapter(dataAdapter);
	        			
						
						
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Getting ReAssign List");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Getting ReAssign List");
	        		
	        	}
		}

	}
	
	
	public class ReassignAsyncTask extends AsyncTask<Void, Void, String> {
		boolean error = false;
		boolean timeout = false;

		Context ctx;

		public void setCtx(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(context, "ReAssign",
        		    "Updating . . .", true);
		}

		@Override
		protected String doInBackground(Void... params) {
			
			try {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
				parameter.put("JobNo", jobno);
				parameter.put("SupplierCode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
				parameter.put("IOpsUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
				return Util.connectSOAP(Constants.uRLiOpsUpdateJobAssignedPerson,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobAssignedPerson, Constants.APIDotNetMethodNameUpdateJobAssignedPerson, parameter);
			} catch (Exception e) {
				timeout = true;
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
				
				if(result!=null){
	        		
	        		try {
	        			
	        		
	        			Util.showAlertCommonDialogWithOKBtn(context, "Success", "Succesfully Updating Assigned User");

						Intent intent = new Intent(context, MainActivity.class);
						context.startActivity(intent);
						
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Updating");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Updating");
	        		
	        	}
				
//				dismiss();
//				fragment.CallTheJob();
				
		}

	}

}
