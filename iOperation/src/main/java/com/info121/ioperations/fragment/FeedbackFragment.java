package com.info121.ioperations.fragment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.info121.constant.Constants;
import com.info121.ioperations.ChooseTaskActivity;
import com.info121.ioperations.R;
import com.info121.ioperations.iOpMainViewPagerActivity;
import com.info121.ioperations.dialog.UpdateFeedbackServerDialog;
import com.info121.ioperations.infointerface.callJobAssignList;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;
import com.info121.model.iBid_Model;
import com.info121.model.iOP_Model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public final class FeedbackFragment extends RootFragment implements OnClickListener,callJobAssignList {
	
    private static final String KEY_CONTENT = "TestFragment:Content";
    private Context context;
    private String SupplierCode;
    private Button btnSend;
    private Button btnClear;
    private Spinner spinListJobNo;
    private EditText etMessage;
    
    private List<String> listJobDetails;
    private List<String> listUserName;
    
    private ArrayAdapter<String> dataAdapter;
    
    public static FeedbackFragment thisFragment;
    
    //buttons navigation
    private Button btnUpdateServer;
    private Button btniOperation;
    private Button btniBidding;
    private Button btnFeedback;
    
    public static FeedbackFragment newInstance(String content) {
        FeedbackFragment fragment = new FeedbackFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }
    
    public static boolean isActive = false;
    @Override
	public void onResume() {
		super.onResume();
		isActive = true;
	}



	@Override
	public void onStop() {
		super.onStop();
		isActive = false;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
    	addToParentLayout(R.layout.fragment_feedback);
        context = getActivity();
        thisFragment = this;
        SupplierCode = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");
        btnSend = (Button) getViewParent().findViewById(R.id.buttonFeedBackSend);
        btnSend.setOnClickListener(this);
        btnClear = (Button) getViewParent().findViewById(R.id.buttonFeedbackClear);
        btnClear.setOnClickListener(this);
        etMessage = (EditText) getViewParent().findViewById(R.id.editTextFeedback);
       
        
        spinListJobNo = (Spinner) getViewParent().findViewById(R.id.spinnerFeedbackReferenceJobNo);
        listJobDetails = new ArrayList<String>();
        listUserName = new ArrayList<String>();

    	dataAdapter = new ArrayAdapter<String>(context,
    		android.R.layout.simple_spinner_item, listJobDetails);
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinListJobNo.setAdapter(dataAdapter);
        
    	new getAllJobNoListAsyncTask().execute();
    	
    	btnUpdateServer = (Button) getViewParent().findViewById(R.id.ButtonShortcutiOpServerInfo);
		btnUpdateServer.setText("xxx.xxx."+Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));
		btnUpdateServer.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			      
				   UpdateFeedbackServerDialog.show(context, getActivity(), thisFragment, true);
				   
			   }
		});
		
		btniOperation = (Button) getViewParent().findViewById(R.id.ButtonShortcutiOpJobAssignments);
		btniOperation.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			      
				   iOpMainViewPagerActivity.pager.setCurrentItem(0);
				   
			   }
		});
		
		btniBidding = (Button) getViewParent().findViewById(R.id.ButtonShortcutiOpJobBidding);
		btniBidding.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			      
				   iOpMainViewPagerActivity.pager.setCurrentItem(1);
				   
			   }
		});
		
		btnFeedback = (Button) getViewParent().findViewById(R.id.ButtonShortcutiOpFeedback);
		btnFeedback.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			      
				   iOpMainViewPagerActivity.pager.setCurrentItem(2);
				   
			   }
		});
    	
        return getViewParent();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
    
    private class getAllJobNoListAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {	
        	showLoading();
        	
        }

		@Override
		protected String doInBackground(Void... v) {
			Hashtable<String, String> parameter = new Hashtable<String, String>();
			parameter.put("SupplierCode", SupplierCode);
			parameter.put("IOPSUser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
			parameter.put("PageSize", "999999999");
			parameter.put("PagesPerSet", "1");
			parameter.put("PageNo", "1");
			
			String AssignJobList = Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
			
			try { 
				ArrayList<iOP_Model> listTemp = Parser.getJSONSupplierJobAssignListResult(AssignJobList,"Job Assigned");
				for(int i=0;i<listTemp.size();i++){
					if(!(listTemp.get(i).getJobNo().compareToIgnoreCase("")==0)){
						listJobDetails.add(listTemp.get(i).getJobNo()+"-Job-"+listTemp.get(i).getBookNo());
						listUserName.add(listTemp.get(i).getName());
					}
				}
			} catch (Exception e) {

			}
			
			parameter = new Hashtable<String, String>();
			parameter.put("SupplierCode", SupplierCode);
			//parameter.put("Status", "Bid New");
			parameter.put("PageSize", "999999999");
			parameter.put("PagesPerSet", "1");
			parameter.put("PageNo", "1");
			
			String BidJobList = Util.connectSOAP(Constants.uRLiOpsGetSupplierBidNewList,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierBidNewList, Constants.APIDotNetMethodNameGetSupplierBidNewList, parameter);
			
			try {
				ArrayList<iBid_Model> listTemp = Parser.getJSONSupplierBidNewListResult(BidJobList);
				for(int i=0;i<listTemp.size();i++){
					if(!(listTemp.get(i).getJobNo().compareToIgnoreCase("")==0)){
						listJobDetails.add(listTemp.get(i).getBidNo()+"-Bid-"+listTemp.get(i).getBookNo());
						listUserName.add(listTemp.get(i).getName());
					}
				}
			} catch (Exception e) {

			}
			
			return null;
		}
        @Override
        protected void onPostExecute(String result) {  
        	dismissLoading();
        	
        	dataAdapter.notifyDataSetChanged();
        	spinListJobNo.invalidate();

        	if(listJobDetails.size()==0){
        		//Util.showAlertCommonDialogWithOKBtn(context, "Error","Error Getting Reference Job No");	
        	}
        	
        }
    }
    
    
    private class sendFeedBackAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {	
        	showLoading();
        	
        }

		@Override
		protected String doInBackground(Void... v) {
			
			 String Driver = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
	         String Company = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.SUPPLIER_KEY, "");
	         //String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
	         
	         android.text.format.DateFormat df = new android.text.format.DateFormat();
	         String mydate = df.format("yyyy-MM-ddThh:mm:ss", new java.util.Date()).toString();
			
			Hashtable<String, String> parameter = new Hashtable<String, String>();
			parameter.put("SupplierCode", Company.trim());
			parameter.put("IOpsUserName", Driver);
			parameter.put("MessageType", "feedback");
			parameter.put("Message", etMessage.getText().toString());
			parameter.put("MessageDate", mydate);
			parameter.put("InBound", "0");
			parameter.put("Status", "new");
			parameter.put("UserRole", "Operations");
			//parameter.put("UserName", listUserName.get(spinListJobNo.getSelectedItemPosition()));
			//parameter.put("UserName", "");
			
			String temp = listJobDetails.get(spinListJobNo.getSelectedItemPosition());
			temp = temp.substring(0,temp.indexOf('-'));
			
			parameter.put("JobNo", temp);
			
			String result = Util.connectSOAP(Constants.uRLiOpsSaveFeedBack,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsSaveFeedback, Constants.APIDotNetMethodNameSaveFeedBack, parameter);
			
		
			return result;
		}
        @Override
        protected void onPostExecute(String result) {  
        	dismissLoading();
        	
        	
        	if(result!=null){
        		
        		try { 
    				if(Parser.getJSONSaveFeedbackResult(result).compareToIgnoreCase("updated")==0)
    					Util.showAlertCommonDialogWithOKBtn(ChooseTaskActivity.context, "Succes","Feedback sent");	
    				else
    					Util.showAlertCommonDialogWithOKBtn(ChooseTaskActivity.context, "Error","Error Sending Feedback");	
    			} catch (Exception e) {

    			}
        		getActivity().finish();
    			
        	}else{
        		
        		Util.showAlertCommonDialogWithOKBtn(ChooseTaskActivity.context, "Error","Error Sending Feedback");	
        		getActivity().finish();
        	}
        	
   
        }
    }


	@Override
	public void onClick(View v) {
		
		
		switch(v.getId()){
			
		case R.id.buttonFeedBackSend:
			new sendFeedBackAsyncTask().execute();
			break;

		case R.id.buttonFeedbackClear:
			//etMessage.setText(context.getString(R.string.ioperation_feedback_prompt));
			etMessage.setText("");
			break;
			
		}
		
	}



	@Override
	public void CallTheJob() {
		new getAllJobNoListAsyncTask().execute();
		btnUpdateServer.setText("xxx.xxx."+Constants.SERVER_URL.substring(8, Constants.SERVER_URL.length()));
	}
    
    
    
}