package com.info121.ioperation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.infointerface.callJobAssignList;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.iBid_Model;
import com.info121.model.iOP_Model;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class ChooseTaskActivity extends RootWithIdleCheckActivity implements OnClickListener, callJobAssignList {

	private TextView txtUsername;
	private TextView txtDate;
	private Button btnChangePassword;
	private Button btniOperation;
	private Button btniBidding;
	private Button btnFeedback;
	private Button btnLogOut;
	private ImageView imgRefresh;
	
	private List<String> listJobNo;
	private List<String> listBidNo;
	
	public static Context context;
	
	private ProgressDialog progress;
	
	private int no_jobs=0;
	private int no_bids=0;
	private BadgeView badge1;
	private BadgeView badge2;
	
	public static boolean isActive = false;
	public static ChooseTaskActivity thisFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_task_layout);
		context = this;
		thisFragment=this;
		//GCMIntentService.generateNotification(context, "testing notification");
		txtUsername = (TextView) findViewById(R.id.textViewUsername);
		txtDate = (TextView) findViewById(R.id.textViewDate);
		btnChangePassword= (Button) findViewById(R.id.ButtonChangePassword);
		btnChangePassword.setOnClickListener(this);
		btniOperation = (Button) findViewById(R.id.ButtonTaskiOperation);
		btniOperation.setOnClickListener(this);
		btniBidding = (Button) findViewById(R.id.ButtonTaskiBidding);
		btniBidding.setOnClickListener(this);
		btnFeedback = (Button) findViewById(R.id.buttonTaskFeedback);
		btnFeedback.setOnClickListener(this);
		btnLogOut= (Button) findViewById(R.id.ButtonLogOut);
		btnLogOut.setOnClickListener(this);
		imgRefresh = (ImageView) findViewById(R.id.imageViewRefresh);
		imgRefresh.setOnClickListener(this);
		imgRefresh.setVisibility(View.GONE);

		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		txtDate.setText(mydate);
		
		txtUsername.setText("Welcome : "+getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

	    badge1 = new BadgeView(this, btniOperation);
        badge1.setText(String.valueOf(no_jobs));
        badge1.setTextColor(Color.RED);
        badge1.setBadgeMargin(15, 10);
        badge1.setBadgeBackgroundColor(Color.parseColor("#FFFFFF"));
        badge1.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
        badge1.show();
        
        badge2 = new BadgeView(this, btniBidding);
        badge2.setText(String.valueOf(no_jobs));
        badge2.setTextColor(Color.RED);
        badge2.setBadgeMargin(15, 10);
        badge2.setBadgeBackgroundColor(Color.parseColor("#FFFFFF"));
        badge2.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
        badge2.show();
	
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		isActive = true;
		 new getAllJobNoListAsyncTask().execute();
	}



	@Override
	protected void onStop() {
		super.onStop();
		isActive = false;
	}



	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.ButtonChangePassword:
			
			Intent  intent = new Intent(ChooseTaskActivity.this, iOPForgotPasswordActivity.class); 
	        startActivity(intent);
			
			break;
		
		case R.id.ButtonTaskiOperation:
		    intent = new Intent(ChooseTaskActivity.this, iOpMainViewPagerActivity.class); 
			intent.putExtra(Constants.intentKey_iOp, 1);
            startActivity(intent);
			break;

		case R.id.ButtonTaskiBidding:

			 intent = new Intent(ChooseTaskActivity.this, iOpMainViewPagerActivity.class); 
			 intent.putExtra(Constants.intentKey_iOp, 2);
             startActivity(intent);
			
			
			break;
			
		case R.id.buttonTaskFeedback:

			 intent = new Intent(ChooseTaskActivity.this, iOpMainViewPagerActivity.class); 
			 intent.putExtra(Constants.intentKey_iOp, 3);
             startActivity(intent);
			
			
			break;
			
	    case R.id.ButtonLogOut:
	    	
	    	showLogOutDialog(ChooseTaskActivity.this, "Exit","logout from the app?");

			break;
			
	    case R.id.imageViewRefresh:
	    	
	    	new getAllJobNoListAsyncTask().execute();

			break;

		}
		
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		
		SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(Util.EXIT_KEY, true);
		editor.commit();
		
		//finish();
		System.exit(1);
	}
	
	
	public void showLogOutDialog(Context ctx, String title, String message) {

		try {

			AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							
							SharedPreferences preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
							
							String updated_time = preferences.getString(Util.SERVER_LAST_UPDATED_KEY, "-");
							String server_one = preferences.getString(Util.SERVER_ONE_KEY, Constants.SERVER_URL);
							String server_two = preferences.getString(Util.SERVER_TWO_KEY, Constants.SERVER_URL);
							
							SharedPreferences settings = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
					    	settings.edit().clear().commit();
					    	
					        preferences = context.getSharedPreferences(Util.SERVER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = preferences.edit();
							editor.putString(Util.SERVER_LAST_UPDATED_KEY, updated_time);
							editor.putString(Util.SERVER_ONE_KEY, server_one);
							editor.putString(Util.SERVER_TWO_KEY, server_two);
							editor.commit();
							
							Constants.SERVER_URL = server_one;
							Constants.BASE_URL = "http://" + Constants.SERVER_URL;
							Constants.BASE_PHOTO_URL = server_two;
							
							Constants.reload();

							
					    	finish();
					    	Intent intent = new Intent(ChooseTaskActivity.this, iOPLoginActivity.class); 
					        startActivity(intent);

							return;
						}
					});
			 alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
			 new DialogInterface.OnClickListener(){
			
			 public void onClick(DialogInterface dialog, int which) {

			 return;
			 }
			
			 });
			alertDialog.show();

		} catch (Exception e) {
			Util.modifiedLogTrace(e.getStackTrace().toString());
		}
		
		
	}
	
	
	 private class getAllJobNoListAsyncTask extends AsyncTask<Void, Void, String> {
		 
		 private boolean isTimeout=false;
		 
	        @Override
	        protected void onPreExecute() {	
	        	try {
					progress = ProgressDialog.show(context, "Login",
						    "Loading . . .", true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }

			@Override
			protected String doInBackground(Void... v) {
				
				try {
				
				Hashtable<String, String> parameter = new Hashtable<String, String>();
				parameter.put("SupplierCode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
				parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
				parameter.put("PageSize", "1000");
				parameter.put("PagesPerSet", "10");
				parameter.put("PageNo", "1");
				
				String AssignJobList = Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);

					Log.e("AssignJobList",""+AssignJobList);
				listJobNo = new ArrayList<String>();
				
					ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierJobAssignListResult(AssignJobList,"Job Assigned");
					for(int i=0;i<listTemp.size();i++){
						if(!(listTemp.get(i).getJobNo().compareToIgnoreCase("")==0)){
							listJobNo.add(listTemp.get(i).getJobNo());

					
						}
					}

					Log.e("LIST SIZE",""+listJobNo.size());
				parameter = new Hashtable<String, String>();
				parameter.put("SupplierCode", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, ""));
				parameter.put("PageSize", "1000");
				parameter.put("PagesPerSet", "10");
				parameter.put("PageNo", "1");
				//parameter.put("Status", "Bid New");
				listBidNo = new ArrayList<String>();
				String BidJobList = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidNewList,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidNewList, Constants.APIDotNetMethodNameGetSupplierBidNewList, parameter);
				
				
					ArrayList<iBid_Model> listTempBid = Parser.getJSONSupplierBidNewListResult(BidJobList);
					for(int i=0;i<listTempBid.size();i++){
						if(!(listTempBid.get(i).getJobNo().compareToIgnoreCase("")==0)){
							listBidNo.add(listTempBid.get(i).getJobNo());
						
						}
					}
					
				} catch (Exception e) {
					isTimeout = true;
				}
				
				
				
				return null;
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	try {
					progress.dismiss();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

	        	if(isTimeout){
	        		
	        		//Util.showAlertCommonDialogWithOKBtn(context, "Error","Connection timeout error. Please check your internet connection");
	        		imgRefresh.setVisibility(View.VISIBLE);
	        		
	        		try {
						badge1.setText(String.valueOf(listJobNo.size()));
					} catch (Exception e1) {
						badge1.setText("0");
					}
	        		
	        		
	        		try {
						badge2.setText(String.valueOf(listBidNo.size()));
					} catch (Exception e1) {
						badge2.setText("0");
					}
	        		
	        	}else{
		        	try {
						if(listJobNo.size()==0||listBidNo.size()==0){
							//badge1.setVisibility(View.GONE);
							//badge2.setVisibility(View.GONE);
							boolean check = false;
							badge1.setVisibility(View.VISIBLE);
							badge2.setVisibility(View.VISIBLE);
							if(listJobNo.size()==0){
							badge1.setText("0");
							check=true;
							
			        		imgRefresh.setVisibility(View.VISIBLE);
							}
							else
							badge1.setText(String.valueOf(listJobNo.size()));	
								
							if(listBidNo.size()==0)
							{
							badge2.setText("0");
							check=true;
							
			        		imgRefresh.setVisibility(View.VISIBLE);
							}
							else
							badge2.setText(String.valueOf(listBidNo.size()));
	   
							//if(check)
								//Util.showAlertCommonDialogWithOKBtn(context, "Error","Connection timeout error. Please check your internet connection");
						}else{
							badge1.setVisibility(View.VISIBLE);
							badge2.setVisibility(View.VISIBLE);
							badge1.setText(String.valueOf(listJobNo.size()));
							badge2.setText(String.valueOf(listBidNo.size()));
						}
						
						imgRefresh.setVisibility(View.GONE);
						
					} catch (Exception e) {
						//Util.showAlertCommonDialogWithOKBtn(context, "Error","Connection timeout error. Please check your internet connection");
		        		imgRefresh.setVisibility(View.VISIBLE);
		        		
		        		try {
							badge1.setText(String.valueOf(listJobNo.size()));
						} catch (Exception e1) {
							badge1.setText("0");
						}
		        		
		        		
		        		try {
							badge2.setText(String.valueOf(listBidNo.size()));
						} catch (Exception e1) {
							badge2.setText("0");
						}
		        		
		        		
					}
	        	}
	        }
	    }


	@Override
	public void CallTheJob() {

		 new getAllJobNoListAsyncTask().execute();
	}
	

	
	
}
