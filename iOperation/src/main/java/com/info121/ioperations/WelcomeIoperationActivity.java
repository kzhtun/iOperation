package com.info121.ioperations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperations.adapter.IoperationPagerAdapter;
import com.info121.ioperations.util.Constant;
import com.info121.ioperations.util.Util;
import com.info121.model.iOP_Model;
import com.viewpagerindicator.TitlePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class WelcomeIoperationActivity extends RootWithIdleCheckActivity {
    public static WelcomeIoperationActivity thisFragment;
    public ViewPager pager;
    IoperationPagerAdapter adapter;
    SharedPreferences sp;
    private Context context;
    private int jobPosition;
    private ArrayList<HashMap<String, String>> jobList;
    private ArrayList<iOP_Model> item;
    private String result;
    private String filter;
    private String jonNOIntent;
    private int tempPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ioperation_pager_activity);
        context = getApplicationContext();
        thisFragment = this;
        sp = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        result = sp.getString("result", "");
        filter = sp.getString("filter", "");

        jobList = new ArrayList<HashMap<String, String>>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobPosition = extras.getInt("position");
            item = (ArrayList<iOP_Model>) extras.getSerializable("searchResult");
            jonNOIntent = extras.getString("jobNOIntent", "");
            Log.d("array", "" + item.size() + "     " + item.get(0).getClaimStatus());
            Log.e("IoperationLIST", "" + Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_JOBLIST));
            String result = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_JOBLIST);
            Log.d("msg", "welcomeioperation   " + result);


            String searchvalue = sp.getString("search", "");
            if (searchvalue.equalsIgnoreCase("")) {

            } else {

            }

            JSONObject rootObj = null;
            JSONArray txtObj = null;
            try {
                rootObj = new JSONObject(result);
                if (rootObj.has("assignedjoblist")) {
                    txtObj = rootObj.getJSONArray("assignedjoblist");
                }
                if (rootObj.has("showandservedjoblist")) {
                    txtObj = rootObj.getJSONArray("showandservedjoblist");
                }
                if (rootObj.has("noshowandservedjoblist")) {
                    txtObj = rootObj.getJSONArray("noshowandservedjoblist");
                }
                if (rootObj.has("rejectedjoblist")) {
                    txtObj = rootObj.getJSONArray("rejectedjoblist");
                }
                Log.e("OBJECT", "" + txtObj.toString());

                for (int i = 0; i < txtObj.length(); i++) {


                    HashMap<String, String> hashmap = new HashMap<String, String>();
                    hashmap.put("jobno", txtObj.getJSONObject(i).getString("jobno"));
//					Log.e("jobno", "" + txtObj.getJSONObject(i).getString("jobno"));
                    hashmap.put("assigndate", txtObj.getJSONObject(i).getString("assigndate"));
                    hashmap.put("bookno", txtObj.getJSONObject(i).getString("bookno"));
                    hashmap.put("type", txtObj.getJSONObject(i).getString("type"));
                    hashmap.put("client", txtObj.getJSONObject(i).getString("client"));
                    hashmap.put("noofadult", txtObj.getJSONObject(i).getString("noofadult"));
                    hashmap.put("noofchild", txtObj.getJSONObject(i).getString("noofchild"));
                    hashmap.put("noofinfant", txtObj.getJSONObject(i).getString("noofinfant"));
                    hashmap.put("flightno", txtObj.getJSONObject(i).getString("flightno"));
//                    hashmap.put("flighttime", txtObj.getJSONObject(i).getString("flighttime"));
                    hashmap.put("pickuptime", txtObj.getJSONObject(i).getString("pickuptime"));
                    hashmap.put("hotel", txtObj.getJSONObject(i).getString("hotel"));
                    hashmap.put("TPTremarks", txtObj.getJSONObject(i).getString("TPTremarks"));
                    hashmap.put("BookingRemarks", txtObj.getJSONObject(i).getString("BookingRemarks"));
                    hashmap.put("agent", txtObj.getJSONObject(i).getString("agent"));
                    hashmap.put("arrdepdate", txtObj.getJSONObject(i).getString("arrdepdate"));
                    hashmap.put("status", txtObj.getJSONObject(i).getString("status"));
                    hashmap.put("driver", txtObj.getJSONObject(i).getString("driver"));

                    hashmap.put("pickup", txtObj.getJSONObject(i).getString("pickup"));
                    hashmap.put("alight", txtObj.getJSONObject(i).getString("alight"));
                    hashmap.put("price", txtObj.getJSONObject(i).getString("price"));
                    hashmap.put("description", txtObj.getJSONObject(i).getString("description"));
                    hashmap.put("phone", txtObj.getJSONObject(i).getString("phone"));
                    hashmap.put("ClaimAmount", txtObj.getJSONObject(i).getString("ClaimAmount"));
                    hashmap.put("ClaimRemarks", txtObj.getJSONObject(i).getString("ClaimRemarks"));
                    hashmap.put("ClaimStatus", txtObj.getJSONObject(i).getString("ClaimStatus"));

//                    hashmap.put("BookingRemarks",txtObj.getJSONObject(i).getString("BookingRemarks"));
                    jobList.add(hashmap);
                }
            } catch (JSONException e) {
                Log.d("RujulError", e.toString());
                Toast.makeText(WelcomeIoperationActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

        Log.e("JOB list Size", "" + jobList.size());
        //Set the pager with an adapter
        pager = (ViewPager) findViewById(R.id.pagerIoperation);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.d("msg", "onpagescrolled " + position);

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("msg", "onpageselected " + position);
                Log.d("msg", "page selected listener : " + jobPosition + " position:  " + position);

                /*if (jobPosition == position) {
                    HashMap<String, String> map = jobList.get(jobPosition);
                    Log.d("msg", "page selected listener  " + jobPosition + "   jobno   " + map.get("jobno"));
                  //  new DriverClaimsGetJobClaimJob(map.get("jobno")).execute();

                }*/


            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.d("msg", "onpagescrollstatechange " + state);
            }
        });


/*
        String searchvalue = sp.getString("search", "");
        if (searchvalue.equalsIgnoreCase("")) {
*/


        for (int i = 0; i < jobList.size(); i++) {

            HashMap<String, String> hashMaps = jobList.get(i);
            String jobno = hashMaps.get("jobno");


            if (jonNOIntent.equalsIgnoreCase(jobno)) {

                tempPosition = i;

            }


        }


        adapter = new IoperationPagerAdapter(WelcomeIoperationActivity.this, jobList, jobPosition);
        pager.setAdapter(adapter);




/*

       } else {
            adapter = new IoperationPagerAdapter(WelcomeIoperationActivity.this, item, jobPosition,"s");
            pager.setAdapter(adapter);

        }
*/


        try {
            pager.setCurrentItem(tempPosition);
        } catch (NumberFormatException e) {
            Log.e("", "" + e);
        }

//Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.titlesIoperation);
        titleIndicator.setViewPager(pager);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getBoolean(Util.EXIT_NOSHOW_KEY, false)) {
            SharedPreferences preferences = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Util.EXIT_NOSHOW_KEY, false);
            editor.commit();
            finish();
//			iOperationFragment.thisFragment.CallTheJob();
        }

        if (getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getBoolean(Util.EXIT_SHOW_KEY, false)) {
            SharedPreferences preferences = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Util.EXIT_SHOW_KEY, false);
            editor.commit();
            finish();
//			iOperationFragment.thisFragment.CallTheJob();
        }
    }


// check status of claims

    class DriverClaimsGetJobClaimJob extends AsyncTask<Void, Void, String> {

        String jobNo;


        public DriverClaimsGetJobClaimJob(String jobNo) {
            this.jobNo = jobNo;
            Log.d("msg", "job no get " + jobNo);
        }


        @Override
        protected String doInBackground(Void... params) {


            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("jobNo", jobNo);

            return Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetJobClaimJob, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetJobClaimJob, Constants.APIDotNetMethodNameDriverClaimsGetJobClaimJob, parameter);

        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Log.d("msg", "res ioperationpageradapter  " + aVoid);

            //  {"jobclaimjobdisplay": [{"JobNo":"","ClaimAmount":"","ClaimRemarks":"","ClaimStatus":""}]}

            try {

                JSONObject jsonObject = new JSONObject(aVoid);
                JSONArray jsonArray = jsonObject.getJSONArray("jobclaimjobdisplay");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                String jobNO = jsonObject1.getString("JobNo");
                String jobAmount = jsonObject1.getString("ClaimAmount");
                String jobRemarks = jsonObject1.getString("ClaimRemarks");
                String jobStatus = jsonObject1.getString("ClaimStatus");


                Log.d("msg", " 1 " + jobNO + "   " + jobAmount + "   " + jobRemarks + "   " + jobStatus);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("msg", "error " + e.getMessage());
            }


        }
    }


//	@Override
//	public void onClick(View v) {
//
//		switch(v.getId()){
//
//		case R.id.buttonWelcome :
//
//			 Intent intent = new Intent(WelcomeIoperationActivity.this, ShowClientActivity.class);
//			 intent.putExtra(Util.JOBNO_KEY,getIntent().getStringExtra(Util.JOBNO_KEY));
//			 intent.putExtra(Util.PASSENGER_KEY, namefile.trim());
//             startActivity(intent);
//
//			break;
//
//         case R.id.buttonNoSHow :
//        	 intent = new Intent(WelcomeIoperationActivity.this, NoShowClientActivity.class);
//        	 intent.putExtra(Util.JOBNO_KEY, getIntent().getStringExtra(Util.JOBNO_KEY));
//			  intent.putExtra(Util.PASSENGER_KEY, namefile.trim());
//             startActivity(intent);
//			break;
//
//			case R.id.btReassign_WelcomeActivity:
//				ReAssignDialog.show(getApplicationContext(),jobNo);
//				break;
//			case R.id.btReject_WelcomeActivity:
//					if(Util.isNetworkAvailable(getApplicationContext())){
//						new updateJobRejectedAsyncTask().execute();
//					}else{
//						Toast.makeText(WelcomeIoperationActivity.this, "No internet connection found", Toast.LENGTH_SHORT).show();
//					}
//
//				break;
//		}
//
//	}
//	protected void showLoading(){
//		LLLoading.setVisibility(View.VISIBLE);
//	}
//
//	protected void dismissLoading(){
//		LLLoading.setVisibility(View.GONE);
//	}


//	private class updateJobRejectedAsyncTask extends AsyncTask<Void, Void, String> {
//
//
//		@Override
//		protected void onPreExecute() {
//			showLoading();
//
//		}
//
//		@Override
//		protected String doInBackground(Void... v) {
//			Hashtable<String, String> parameter = null;
//			try {
//				parameter = new Hashtable<String, String>();
//				parameter.put("JobNo", jobNo);
//				parameter.put("IOPSUserName", getApplicationContext().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getApplicationContext().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
//			} catch (Exception e) {
//				Util.modifiedLogTrace(Log.getStackTraceString(e));
//			}
//			return Util.connectSOAP(Constants.uRLiOpsUpdateJobRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobRejectedStatus, Constants.APIDotNetMethodNameUpdateJobRejectedStatus, parameter);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			dismissLoading();
//			if (result != null) {
//				try {
//
//					String temp = Parser.getJSONAcceptRejectResult(result);
//					//Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + MultipleJobNo + " Rejected Succesfully");
////					items = new ArrayList<iOP_Model>();
////					search = new ArrayList<iOP_Model>();
//					Toast.makeText(WelcomeIoperationActivity.this, "Job #" + jobNo + " Rejected Succesfully", Toast.LENGTH_SHORT).show();
//
//					try {
//
//						AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
//						alertDialog.setTitle("Success");
//						alertDialog.setMessage("Job #" + jobNo + " Rejected Succesfully");
//						alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog, int which) {
//
//										Intent intent = new Intent(context, HomeActivity.class);
//										context.startActivity(intent);
//
//									}
//								});
//
//						alertDialog.show();
//
//					} catch (Exception e) {
//						Util.modifiedLogTrace(e.getStackTrace().toString());
//					}
//
//
//				} catch (Exception e) {
//					Util.showAlertCommonDialogWithOKBtn(getApplicationContext(), "Error", "Operation Failed");
//				}
//
//
//			} else {
//
//				Util.showAlertCommonDialogWithOKBtn(getApplicationContext(), "Error ", "Operation Failed");
//
//			}
//
//		}
//	}

}
