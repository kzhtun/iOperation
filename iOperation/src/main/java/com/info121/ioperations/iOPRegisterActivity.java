package com.info121.ioperations;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;
import com.info121.model.City_Model;
import com.info121.model.Country_Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class iOPRegisterActivity extends RootActivity implements OnClickListener {

	private TextView tvTNC;
	private Context context;
	private Button btnSubmit;
	
	private EditText etParam1, etParam2, etParam3, etParam4, etParam5,
			etParam6, etParam7, etParam8, etParam9, etParam10, etParam11,
			etParam12, etParam13, etParam14, etParam15, etParam16, etParam17,
			etParam18, etParam19;
	
	private CheckBox checkTickedRegister;
	
	private Spinner spinCountry,spinCity;
	
	private ProgressDialog progress;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		context = this;
		tvTNC = (TextView) findViewById(R.id.textViewRegisterTNC);
		tvTNC.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvTNC.setOnClickListener(this);
		
		btnSubmit = (Button) findViewById(R.id.buttonRegisterSubmit);
		btnSubmit.setOnClickListener(this);
		
		checkTickedRegister = (CheckBox) findViewById(R.id.checkBoxRegisterTNC);
		
		etParam1 = (EditText) findViewById(R.id.EditTextRegisterSupplierName);
		etParam2 = (EditText) findViewById(R.id.EditTextRegisterAddress);
		etParam3 = (EditText) findViewById(R.id.EditTextRegisterPostalCode);
		etParam4 = (EditText) findViewById(R.id.EditTextCountryCode);
		etParam5 = (EditText) findViewById(R.id.EditTextRegisterCityCode);
		etParam6 = (EditText) findViewById(R.id.EditTextRegisterState);
		etParam7 = (EditText) findViewById(R.id.EditTextRegisterContactPerson1);
		etParam8 = (EditText) findViewById(R.id.EditTextRegisterConPerTel1);
		etParam9 = (EditText) findViewById(R.id.EditTextRegisterConPerEmail1);
		etParam10 = (EditText) findViewById(R.id.EditTextRegisterContactPerson2);
		etParam11 = (EditText) findViewById(R.id.EditTextRegisterConPerTel2);
		etParam12 = (EditText) findViewById(R.id.EditTextRegisterConPerEmail2);
		etParam13 = (EditText) findViewById(R.id.EditTextRegisterContactPerson3);
		etParam14 = (EditText) findViewById(R.id.EditTextRegisterConPerTel3);
		etParam15 = (EditText) findViewById(R.id.EditTextRegisterConPerEmail3);
		etParam16 = (EditText) findViewById(R.id.editTextRegisterContactPerson4);
		etParam17 = (EditText) findViewById(R.id.editTextRegisterConPerTel4);
		etParam18 = (EditText) findViewById(R.id.editTextRegisterConPerEmail4);
		etParam19 = (EditText) findViewById(R.id.EditTextRegisterFaxNo);

		spinCountry = (Spinner) findViewById(R.id.spinnerRegisterCountry);
		spinCountry.setOnItemSelectedListener(new CountryOnItemSelectedListener());
		spinCity = (Spinner) findViewById(R.id.spinnerRegisterCity);
		spinCity.setOnItemSelectedListener(new CityOnItemSelectedListener());
		
		new GetCountryAsyncTask().execute();
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.textViewRegisterTNC:
               Util.showCustomDialog(context, context.getResources().getString(R.string.Register_tnc_dummy_content));
			break;

		case R.id.buttonRegisterSubmit:

			if(checkTickedRegister.isChecked())
			new CheckExistSupplierAsyncTask().execute();
			else
			Util.showAlertCommonDialogWithOKBtn(context, "Error","Please read & tick the check box for terms of conditions");
			
			break;
			
		case R.id.ButtoniOpNext:
            
			break;
//		case R.id.ButtoniOpLast:
//
//			break;
		case R.id.ButtoniOpGo:

			break;

		}
		
	}
	
	
	public class CountryOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

			
			etParam4.setText(tempCountry.get(pos).getCountrycode());
			
			new GetCityAsyncTask().execute(tempCountry.get(pos).getCountrycode());
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
		  }
		 
		}
	
	public class CityOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

			etParam5.setText(tempCity.get(pos).getCitycode());
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
		  }
		 
		}
	
	
	 private class SaveSupplierAsyncTask extends AsyncTask<Void, Void, String> {
	        @Override
	        protected void onPreExecute() {
	        	progress = ProgressDialog.show(context, "Process",
	        		    "Loading . . .", true);
	        }

			@Override
			protected String doInBackground(Void... v) {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
//				parameter.put("suppliername", etParam1.getText().toString());
//				parameter.put("address", etParam2.getText().toString());
//				parameter.put("postalcode", etParam3.getText().toString());
//				parameter.put("countrycode", etParam4.getText().toString());
//				parameter.put("citycode", etParam5.getText().toString());
//				parameter.put("state", etParam6.getText().toString());
//
//				parameter.put("contactperson1", etParam7.getText().toString());
//				parameter.put("telno1", etParam8.getText().toString());
//				parameter.put("email1", etParam9.getText().toString());
//
//				parameter.put("contactperson2", etParam10.getText().toString());
//				parameter.put("telno2", etParam11.getText().toString());
//				parameter.put("email2", etParam12.getText().toString());
//
//				parameter.put("contactperson3", etParam13.getText().toString());
//				parameter.put("telno3", etParam14.getText().toString());
//				parameter.put("email3", etParam15.getText().toString());
//
//				parameter.put("contactperson4", etParam16.getText().toString());
//				parameter.put("telno4", etParam17.getText().toString());
//				parameter.put("email4", etParam18.getText().toString());
//
//				parameter.put("faxno", etParam19.getText().toString());
				
				return Util.connectSOAP(Constants.uRLiOPsRegisterSaveSupplier,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionSaveSupplier, Constants.APIDotNetMethodNameSaveSupplier, parameter);
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	progress.dismiss();
	        	if(result!=null){
	        		
	        		try {
						if (Parser.getJSONSaveSupplierResult(result).compareToIgnoreCase("updated")==0){
							//Util.showAlertCommonDialogWithOKBtn(context, "Updated","Updated");
							new ValidateSupplierAsyncTask().execute();
						}
					} catch (Exception e) {
						
						Util.showAlertCommonDialogWithOKBtn(context, "Error","Error");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Error");
	        		
	        	}
	        	
	        }
	  }
	 
	 private class CheckExistSupplierAsyncTask extends AsyncTask<Void, Void, String> {
	        @Override
	        protected void onPreExecute() {
	        	progress = ProgressDialog.show(context, "Checking. . .",
	        		    "Loading . . .", true);
	        }

			@Override
			protected String doInBackground(Void... v) {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
//				parameter.put("suppliername", etParam1.getText().toString());
				return Util.connectSOAP(Constants.uRLiOPsRegisterValidateSupplier,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionValidateSupplier, Constants.APIDotNetMethodNameValidateSupplier, parameter);
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	progress.dismiss();
	        	if(result!=null){
	        		
	        		try {
						if (Parser.getJSONValidateSupplierResult(result).compareToIgnoreCase("yes")==0){
							Util.showAlertCommonDialogWithOKBtn(context, "Error","Account already exist");
						}else{
							new SaveSupplierAsyncTask().execute();
						}
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error","Unknown Error");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error","Please check your connection and try again.");
	        		
	        	}
	        	
	        }
	  }
	 
	 private class ValidateSupplierAsyncTask extends AsyncTask<Void, Void, String> {
	        @Override
	        protected void onPreExecute() {
	        	progress = ProgressDialog.show(context, "Validating",
	        		    "Loading . . .", true);
	        }

			@Override
			protected String doInBackground(Void... v) {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
//				parameter.put("suppliername", etParam1.getText().toString());
				return Util.connectSOAP(Constants.uRLiOPsRegisterValidateSupplier,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionValidateSupplier, Constants.APIDotNetMethodNameValidateSupplier, parameter);
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	progress.dismiss();
	        	if(result!=null){
	        		
	        		try {
						if (Parser.getJSONValidateSupplierResult(result).compareToIgnoreCase("yes")==0){
							Util.showAlertCommonDialogWithOKBtn(iOPLoginActivity.context, "Account Creation Success","Account Creation Success");
							finish();
						}
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error validate","Error validate");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error validate","Error validate");
	        		
	        	}
	        	
	        }
	  }
	 
	 ArrayList<City_Model> tempCity;
	 private class GetCityAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected void onPreExecute() {}

			@Override
			protected String doInBackground(String... v) {
				Hashtable<String, String> parameter = new Hashtable<String, String>();
				parameter.put("countrycode", v[0]);
				return Util.connectSOAP(Constants.uRLiOPsRegisterGetCity,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionGetCity, Constants.APIDotNetMethodNameGetCity, parameter);
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	
	        	if(result!=null){
	        		
	        		try {
                         tempCity = Parser.getJSONCityResult(result);
	        			
						if (tempCity!=null){
							
							List<String> list = new ArrayList<String>();
							for(int i=0;i<tempCity.size();i++)list.add(tempCity.get(i).getCity());
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
									R.layout.spinner_item,R.id.spinner_item, list);
								dataAdapter.setDropDownViewResource(R.layout.spinner_item);
								spinCity.setAdapter(dataAdapter);
							//Util.showAlertCommonDialogWithOKBtn(context, "City Success","City Success");
						}
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error Get City","Error Get City");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error Get City","Error Get City");
	        		
	        	}
	        	
	        }
	  }
	 
	 ArrayList<Country_Model> tempCountry;
	 private class GetCountryAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected void onPreExecute() {}

			@Override
			protected String doInBackground(String... v) {
				return Util.connectSOAP(Constants.uRLiOPsRegisterGetCountry,Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionGetCountry, Constants.APIDotNetMethodNameGetCountry, null);
			}
	        @Override
	        protected void onPostExecute(String result) {  
	        	
	        	if(result!=null){
	        		
	        		try {
	        			
	        			tempCountry = Parser.getJSONCountryResult(result);
	        			
						if (tempCountry!=null){
							
							List<String> list = new ArrayList<String>();
							for(int i=0;i<tempCountry.size();i++)list.add(tempCountry.get(i).getCountry());
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
									R.layout.spinner_item,R.id.spinner_item,list);
								dataAdapter.setDropDownViewResource(R.layout.spinner_item);
								spinCountry.setAdapter(dataAdapter);
							
							
							//Util.showAlertCommonDialogWithOKBtn(context, "Country Success","Country Success");
						}
					} catch (Exception e) {
						Util.showAlertCommonDialogWithOKBtn(context, "Error Get Country","Error Get Country");
					}
	        		
	        		
	        	}else{
	        		
	        		Util.showAlertCommonDialogWithOKBtn(context, "Error Get Country","Error Get Country");
	        		
	        	}
	        	
	        }
	  }

}
