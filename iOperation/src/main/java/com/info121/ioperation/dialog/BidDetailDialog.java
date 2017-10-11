package com.info121.ioperation.dialog;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.fragment.iBiddingFragment;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.ReAssign_Model;
import com.info121.model.iBid_Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class BidDetailDialog extends Dialog {

	private static BidDetailDialog error = null;

	private ProgressDialog progress;
	Context context;
	
	private Activity act;
	private Spinner spinReAssignList;
	private iBid_Model jobdetail;
	private ArrayList<ReAssign_Model> rmlist;


	protected BidDetailDialog(final Context context, final Activity act, iBid_Model jobdetail,iBiddingFragment fragment, boolean isVisible) {
		
		super(context);
		this.context = context;
		this.act = act;
		this.jobdetail = jobdetail;
		this.fragment = fragment;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.biddetail_dialog);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		
		if(isVisible==true){
			findViewById(R.id.ButtonDialogOKToContinue).setVisibility(View.VISIBLE);
			findViewById(R.id.ButtonDialogJobReject).setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.ButtonDialogOKToContinue).setVisibility(View.GONE);
			findViewById(R.id.ButtonDialogJobReject).setVisibility(View.GONE);
		}
		
		findViewById(R.id.ButtonDialogOKToContinue).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new updateJobAcceptedAsyncTask().execute();
			}
		});

		findViewById(R.id.ButtonDialogJobReject).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new updateJobRejectedAsyncTask().execute();
			}
		});
		
		findViewById(R.id.ButtonDialogPhotoClose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		
		((TextView)findViewById(R.id.TextViewJobDialogJobNo)).setText(jobdetail.getBidNo());
		((TextView)findViewById(R.id.TextViewJobDialogBookingNo)).setText(jobdetail.getBookNo());
		((TextView)findViewById(R.id.TextViewJobDialogPassegerName)).setText(jobdetail.getName());
		((TextView)findViewById(R.id.TextViewJobDialogDate)).setText(jobdetail.getDate());
		((TextView)findViewById(R.id.TextViewJobDialogPickupTime)).setText(jobdetail.getPickupTime());
		((TextView)findViewById(R.id.TextViewJobDialogFlightNo)).setText(jobdetail.getFlight());
		((TextView)findViewById(R.id.TextViewJobDialogHotel)).setText(jobdetail.getHotel());
		((TextView)findViewById(R.id.TextViewJobDialogType)).setText(jobdetail.getType());
		((TextView)findViewById(R.id.TextViewJobDialogAdult)).setText(jobdetail.getA());
		((TextView)findViewById(R.id.TextViewJobDialogChild)).setText(jobdetail.getC());
		((TextView)findViewById(R.id.TextViewJobDialogRemarks)).setText(jobdetail.getBookingRemarks());
		
		
	
	}
	
	iBiddingFragment fragment;
	public static void show(Context context, Activity act, iBid_Model jobdetail, iBiddingFragment fragment, boolean isVisible) {
		error = new BidDetailDialog(context, act,jobdetail,fragment,isVisible);
		error.show();
	}
	
	
	
	private class updateJobAcceptedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        	progress = ProgressDialog.show(context, "Job Detail",
        		    "Loading . . .", true);
        	
        }

		@Override
		protected String doInBackground(Void... v) {
			Hashtable<String, String> parameter = null;
			try {
				parameter = new Hashtable<String, String>();
				parameter.put("BidNo", jobdetail.getBidNo());
			} catch (Exception e) {
				Util.modifiedLogTrace(Log.getStackTraceString(e));
			}

			return Util.connectSOAP(Constants.uRLiOpsUpdateBidAcceptedStatus,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidAcceptedStatus, Constants.APIDotNetMethodNameUpdateBidAcceptedStatus, parameter);
		}
        @Override
        protected void onPostExecute(String result) {  
        	progress.dismiss();
        	
        	if(result!=null){
        		try {
        			
        			String temp = Parser.getJSONAcceptRejectResult(result);
        		
        			try {

        				AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        				alertDialog.setTitle("Success");
        				alertDialog.setMessage("Bid #" + jobdetail.getBidNo() + " Accepted Succesfully");
        				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
        						new DialogInterface.OnClickListener() {
        							public void onClick(DialogInterface dialog, int which) {
        								
        								
        								 
        								return;
        							}
        						});
        				
        				alertDialog.show();

        			} catch (Exception e) {
        				Util.modifiedLogTrace(e.getStackTrace().toString());
        			}
        			
        			
        			    
				} catch (Exception e) {
					Util.showAlertCommonDialogWithOKBtn(context, "Error","Operation Failed");
				}
        		
        		
        	}else{
        		
        		Util.showAlertCommonDialogWithOKBtn(context, "Error ","Operation Failed");
        		
        	}
        	

        	dismiss();
        	fragment.CallTheJob();
        	
        }
  }
	
	
	private class updateJobRejectedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        	progress = ProgressDialog.show(context, "Job Detail",
        		    "Loading . . .", true);
        	
        }

		@Override
		protected String doInBackground(Void... v) {
			Hashtable<String, String> parameter = null;
			try {
				parameter = new Hashtable<String, String>();
				parameter.put("BidNo", jobdetail.getBidNo());
			} catch (Exception e) {
				Util.modifiedLogTrace(Log.getStackTraceString(e));
			}
			return Util.connectSOAP(Constants.uRLiOpsUpdateBidRejectedStatus,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidRejectedStatus, Constants.APIDotNetMethodNameUpdateBidRejectedStatus, parameter);
		}
        @Override
        protected void onPostExecute(String result) {  
        	progress.dismiss();
        	if(result!=null){
        		try {
        			
        			String temp = Parser.getJSONAcceptRejectResult(result);
        			
        			try {

        				AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        				alertDialog.setTitle("Success");
        				alertDialog.setMessage("Bid #" + jobdetail.getBidNo() + " Rejected Succesfully");
        				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
        						new DialogInterface.OnClickListener() {
        							public void onClick(DialogInterface dialog, int which) {
        								
        								return;
        							}
        						});
        				
        				alertDialog.show();

        			} catch (Exception e) {
        				Util.modifiedLogTrace(e.getStackTrace().toString());
        			}
        			    

				} catch (Exception e) {
					Util.showAlertCommonDialogWithOKBtn(context, "Error","Operation Failed");
				}
        		
        		
        	}else{
        		
        		Util.showAlertCommonDialogWithOKBtn(context, "Error ","Operation Failed");
        		
        	}
        	
        	dismiss();
        	fragment.CallTheJob();
        	
        }
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
			progress = ProgressDialog.show(context, "ReAssign",
        		    "Loading . . .", true);
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
			progress.dismiss();
				
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
			
//			try {
//				Hashtable<String, String> parameter = new Hashtable<String, String>();
//				parameter.put("JobNo", String.valueOf(jobno));
//				parameter.put("SupplierCode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
//				parameter.put("IOpsUserName", rmlist.get(spinReAssignList.getSelectedItemPosition()).getUsername());
//				return Util.connectSOAP(Constants.uRLiOpsUpdateJobAssignedPerson,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobAssignedPerson, Constants.APIDotNetMethodNameUpdateJobAssignedPerson, parameter);
//			} catch (Exception e) {
//				timeout = true;
//			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
				
				if(result!=null){
	        		
	        		try {
	        			
	        		
	        			Util.showAlertCommonDialogWithOKBtn(context, "Success","Succesfully Updating Assigned User");
						
						
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Updating");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Updating");
	        		
	        	}
				
				dismiss();
				fragment.CallTheJob();
				
		}

	}
	
	


}
