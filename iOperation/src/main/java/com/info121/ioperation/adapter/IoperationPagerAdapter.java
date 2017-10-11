package com.info121.ioperation.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.SignaneActivity;
import com.info121.constant.Constants;
import com.info121.ioperation.MainActivity;
import com.info121.ioperation.NoShowClientActivity;
import com.info121.ioperation.R;
import com.info121.ioperation.ShowClientActivity;
import com.info121.ioperation.dialog.ReAssignDialog;
import com.info121.ioperation.util.Constant;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by archirayan1 on 4/22/2016.
 */
public class IoperationPagerAdapter extends PagerAdapter {

    public Button btnWelcome;
    public Button btnNoSHow;
    public Button btReassing;
    public Button btReject;
    public Button btListing;
    public Button btHome;
    public LinearLayout LLLoading;
    public TextView tvDateTime, tvCustomer, tvAdult, tvChild, tvPicup, tvAlight, tvHotel, tvFlight, tvType, tvAmount, tvContact, tvPhone, tvDesc, tvRemark, tvTPTnote;
    public Button btSubmitClaims_WelcomFragment, btsignageClaims_WelcomFragment;
    public TextView tvPageNo;
    public String jobNo;
    public Context context;
    public ArrayList<Float> extraChargeArray;
    public ArrayList<String> remarkArray;
    EditText etClaimAmt_extraCharge;
    EditText etRemark_extraCharge;
    ArrayList<HashMap<String, String>> jobList;
    LayoutInflater inflater;
    int jobPositon;

    public IoperationPagerAdapter(Context mContext, ArrayList<HashMap<String, String>> list, int positon) {
        this.context = mContext;
        this.jobList = list;
        jobPositon = positon;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        extraChargeArray = new ArrayList<>();
        remarkArray = new ArrayList<>();

        for (int i = 0; i < jobList.size(); i++) {
            extraChargeArray.add(Float.valueOf(jobList.get(i).get("ClaimAmount")));


        }

        for (int i = 0; i < jobList.size(); i++) {
            remarkArray.add(jobList.get(i).get("ClaimRemarks"));
        }


    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public Object instantiateItem(final View container, final int position) {


        jobList.get(position);

        View view = inflater.inflate(R.layout.welcome_layout, null);

        Log.d("msg", "positoin  getv " + position);


        tvPageNo = (TextView) view.findViewById(R.id.tvPageNo_WelcomeActivity);
        tvDateTime = (TextView) view.findViewById(R.id.tvDateTime_WelcomeActivity);
        tvCustomer = (TextView) view.findViewById(R.id.tvCustomer_WelcomeActivity);
        tvAdult = (TextView) view.findViewById(R.id.tvAdult_WelcomeActivity);
        tvChild = (TextView) view.findViewById(R.id.tvChild_WelcomeActivity);

        tvPicup = (TextView) view.findViewById(R.id.tvPickup_WelcomeActivity);
        tvAlight = (TextView) view.findViewById(R.id.tvAlight_WelcomeActivity);
        tvHotel = (TextView) view.findViewById(R.id.tvHotel_WelcomeActivity);
        tvFlight = (TextView) view.findViewById(R.id.tvFlight_WelcomeActivity);

        tvType = (TextView) view.findViewById(R.id.tvType_WelcomeActivity);
        tvAmount = (TextView) view.findViewById(R.id.tvAmount_WelcomeActivity);
        tvContact = (TextView) view.findViewById(R.id.tvContact_WelcomeActivity);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone_WelcomeActivity);

        tvDesc = (TextView) view.findViewById(R.id.tvDesc_WelcomeActivity);
        tvRemark = (TextView) view.findViewById(R.id.tvRemarks_WelcomeActivity);
        tvTPTnote = (TextView) view.findViewById(R.id.tvtptNote_WelcomeActivity);

        etClaimAmt_extraCharge = (EditText) view.findViewById(R.id.etClaimAmt_extraChargee);


        etRemark_extraCharge = (EditText) view.findViewById(R.id.etRemark_extraChargee);

        btSubmitClaims_WelcomFragment = (Button) view.findViewById(R.id.btSubmitClaims_WelcomFragment);
        btsignageClaims_WelcomFragment = (Button) view.findViewById(R.id.btsignageClaims_WelcomFragment);


        int totalSize = jobList.size();
        final int pos = position + 1;
        tvPageNo.setText("" + pos + "/" + totalSize);
        String date = jobList.get(position).get("assigndate");
        String time = jobList.get(position).get("pickuptime");
        String dateTime = date + "  " + time;
        Log.e("dateTime", "" + dateTime);
        tvDateTime.setText(dateTime);
        tvCustomer.setText(jobList.get(position).get("agent"));
        tvAdult.setText(jobList.get(position).get("noofadult"));
        tvChild.setText(jobList.get(position).get("noofchild"));
        tvPicup.setText(jobList.get(position).get("pickup"));
        tvAlight.setText(jobList.get(position).get("alight"));
        tvHotel.setText(jobList.get(position).get("hotel"));
        tvFlight.setText(jobList.get(position).get("flightno"));
        tvType.setText(jobList.get(position).get("type"));
        tvAmount.setText(jobList.get(position).get("price"));
        tvContact.setText(jobList.get(position).get("client"));
        tvPhone.setText(jobList.get(position).get("phone"));
        tvDesc.setText(jobList.get(position).get("description"));
        tvRemark.setText(jobList.get(position).get("BookingRemarks"));
        tvTPTnote.setText(jobList.get(position).get("TPTremarks"));




        String jobStatus = jobList.get(position).get("ClaimStatus");

        Log.d("msg", " jobstatus  " + jobStatus);
        if (jobStatus.equalsIgnoreCase("NEW") || jobStatus.equalsIgnoreCase("PENDING")) {

            etClaimAmt_extraCharge.setEnabled(true);
            etRemark_extraCharge.setEnabled(true);
            btSubmitClaims_WelcomFragment.setEnabled(true);
            Float amtf = Float.parseFloat(jobList.get(position).get("ClaimAmount"));
            Log.d("msg", "float  " + amtf);

            //   etClaimAmt_extraCharge.setText(amtf.toString());

            etClaimAmt_extraCharge.setText(new DecimalFormat("#0.00").format(amtf));
            etRemark_extraCharge.setText(jobList.get(position).get("ClaimRemarks"));


            Log.d("msg", "if");


        } else {

            Log.d("msg", "else");

            etClaimAmt_extraCharge.setText(jobList.get(position).get("ClaimAmount"));


            String remark = jobList.get(position).get("ClaimRemarks");

            if (!remark.equalsIgnoreCase("")) {

                etRemark_extraCharge.setText(remark);

            }


            etClaimAmt_extraCharge.setEnabled(false);
            etRemark_extraCharge.setEnabled(false);
            btSubmitClaims_WelcomFragment.setEnabled(false);


        }

        Log.d("msg", "value remark  " + jobList.get(position).get("ClaimRemarks") + "   amount   " + jobList.get(position).get("ClaimAmount"));

        etClaimAmt_extraCharge.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                extraChargeArray.set(position, 0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    int length = editable.toString().length();
                    if (length != 0) {
                        extraChargeArray.set(position, Float.valueOf(editable.toString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        etRemark_extraCharge.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                Log.d("edt", "before text");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edt", "ontextchanged ");

                remarkArray.set(position, "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    Log.d("edt", "aftertext text");

                    int length = editable.toString().length();
                    if (length != 0) {
                        remarkArray.set(position, String.valueOf(editable.toString()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


//        tvRemarkExtraCharge.setText(jobList.get(position).get("BookingRemarks"));


        btListing = (Button) view.findViewById(R.id.btListing_WelcomeActivity);
        btHome = (Button) view.findViewById(R.id.btHome_WelcomeActivity);

        btReassing = (Button) view.findViewById(R.id.btReassign_WelcomeActivity);
        btReject = (Button) view.findViewById(R.id.btReject_WelcomeActivity);

        btnWelcome = (Button) view.findViewById(R.id.buttonWelcome);
        btnNoSHow = (Button) view.findViewById(R.id.buttonNoSHow);
        LLLoading = (LinearLayout) view.findViewById(R.id.LLRootLoading_WelcomeActivity);

        btSubmitClaims_WelcomFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Util.isNetworkAvailable(context)) {


                    jobNo = jobList.get(position).get("jobno");
                    Log.d("msg", "jobno submit " + jobNo);

                  /*  String str = etClaimAmt_extraCharge.getText().toString();
                    Log.d("msg", " str  " + str + " dyt  " + etRemark_extraCharge.getText().toString());
                    Log.d("msg", " array  " + extraChargeArray.toString() + " pos" + position);
                    Log.d("msg", " remark  " + remarkArray.toString() + " pos" + position);*/
                    Float claimAmt = extraChargeArray.get(position);

                    if (claimAmt != 0) {


                        if (claimAmt != 0) {


                            String remarkStr = remarkArray.get(position);
                            Log.d("msg", " revalue  " + remarkStr);

                            if (!remarkStr.equalsIgnoreCase("")) {

                                new DriverClaimsSaveJobClaimJobAsy(claimAmt, remarkStr, etClaimAmt_extraCharge, etRemark_extraCharge).execute();


                                etClaimAmt_extraCharge.setText("");
                                etRemark_extraCharge.setText("");

                            } else {

                                Toast.makeText(context, "Remarks should not be blank. ", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(context, "Extra Charge Amount should not be Zero.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Extra Charge Amount should not be blank.", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(context, "No Internet connection found,Please check your internet copnnectivity.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btsignageClaims_WelcomFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String isCLick = Util.ReadSharePrefrence(context, "isclick");
                if (isCLick.equalsIgnoreCase("1"))
                {

                }else {
                    Util.WriteSharePrefrence(context, "name", jobList.get(position).get("client"));
                }


                Intent intentSignane = new Intent(context, SignaneActivity.class);
                context.startActivity(intentSignane);


            }
        });

        btListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "ioperation");
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        btReassing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openReAssignDialog();
                Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "ioperation");
                ReAssignDialog.show(context, jobList.get(position).get("jobno").toString());
            }
        });
        btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.isNetworkAvailable(context)) {
                    jobNo = jobList.get(position).get("jobno");
                    Log.d("msg", "jobno " + jobNo);

                    Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "ioperation");
                    new updateJobRejectedAsyncTask().execute();

                } else {
                    Toast.makeText(context, "No internet connection found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowClientActivity.class);
                intent.putExtra(Util.JOBNO_KEY, jobList.get(position).get("jobno"));
                intent.putExtra(Util.PASSENGER_KEY, jobList.get(position).get("client").trim());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        btnNoSHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoShowClientActivity.class);
                intent.putExtra(Util.JOBNO_KEY, jobList.get(position).get("jobno"));
                intent.putExtra(Util.PASSENGER_KEY, jobList.get(position).get("client").trim());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        ((ViewPager) container).addView(view);

        if (jobPositon == position) {

            HashMap<String, String> hashtableJobData = jobList.get(jobPositon);

            String jobNo = hashtableJobData.get("jobno");


            // new DriverClaimsGetJobClaimJob(jobNo).execute();
        }

        return view;
    }


    protected void showLoading() {
        LLLoading.setVisibility(View.VISIBLE);
    }

    protected void dismissLoading() {
        LLLoading.setVisibility(View.GONE);
    }

    public void setClaimJob(int positon) {


    }

    private class updateJobRejectedAsyncTask extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("JobNo", jobNo);
                parameter.put("IOPSUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateJobRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobRejectedStatus, Constants.APIDotNetMethodNameUpdateJobRejectedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {

            dismissLoading();
            if (result != null) {

                try {


                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + MultipleJobNo + " Rejected Succesfully");
//					items = new ArrayList<iOP_Model>();
//					search = new ArrayList<iOP_Model>();
                    Toast.makeText(context, "Job #" + jobNo + " Rejected Succesfully", Toast.LENGTH_SHORT).show();

                    try {

                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Job #" + jobNo + " Rejected Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "ioperation");
                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.putExtra(Constants.intentKey_iOp, 1);
//                                        alertDialog.dismiss();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });

                        alertDialog.show();

                    } catch (Exception e) {
                        Util.modifiedLogTrace(e.getStackTrace().toString());
                    }


                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }

        }
    }


// check status of claims

    public class DriverClaimsGetJobClaimJob extends AsyncTask<Void, Void, String> {

        String jobNo;


        public DriverClaimsGetJobClaimJob(String jobNo) {
            this.jobNo = jobNo;


            Log.d("msg", "job no get " + jobNo);
        }


        @Override
        protected String doInBackground(Void... params) {


            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("jobno", jobNo);

            return Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetJobClaimJob, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetJobClaimJob, Constants.APIDotNetMethodNameDriverClaimsGetJobClaimJob, parameter);

        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Log.d("msg", "res ioperationpageradapter  " + aVoid);

            try {

                JSONObject jsonObject = new JSONObject(aVoid);
                JSONArray jsonArray = jsonObject.getJSONArray("jobclaimjobdisplay");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                String jobNO = jsonObject1.getString("JobNo");
                String jobAmount = jsonObject1.getString("ClaimAmount");
                String jobRemarks = jsonObject1.getString("ClaimRemarks");
                String jobStatus = jsonObject1.getString("ClaimStatus");


//                if (jobStatus.equalsIgnoreCase("APPROVED")) {
//
//                    etClaimAmt_extraCharge.setText(jobAmount);
////                    etRemark_extraCharge.setText(jobRemarks);
//
////
////                    etClaimAmt_extraCharge.setEnabled(false);
////                    etRemark_extraCharge.setEnabled(false);
////                    btSubmitClaims_WelcomFragment.setEnabled(false);
//
//
//                } else if (jobStatus.equalsIgnoreCase("PENDING") || jobStatus.equalsIgnoreCase("NEW")) {
//
//                    etClaimAmt_extraCharge.setEnabled(true);
////                    etRemark_extraCharge.setEnabled(true);
////
//
//                }
//
//
//                Log.d("msg", " 1 " + jobNO + "   " + jobAmount + "   " + jobRemarks + "   " + jobStatus);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("msg", "error " + e.getMessage());
            }

        }
    }


    private class DriverClaimsSaveJobClaimJobAsy extends AsyncTask<Void, Void, String> {


        Float claimAmt;
        String claimRemarks;
        EditText etClaimAmt_extraCharg, etRemark_extraCharge;

        public DriverClaimsSaveJobClaimJobAsy(Float claimAmt, String claimRemarks, EditText etClaimAmt_extraCharg, EditText etRemark_extraCharge) {
            this.claimAmt = claimAmt;
            this.claimRemarks = claimRemarks;
            this.etClaimAmt_extraCharg = etClaimAmt_extraCharg;
            this.etRemark_extraCharge = etRemark_extraCharge;

            Log.d("test", "jobno  " + jobNo + "   amt  " + claimAmt + "  remark  " + claimRemarks);
        }


        @Override
        protected String doInBackground(Void... voids) {

            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("jobno", jobNo);
            parameter.put("ClaimAmt", claimAmt.toString());
            parameter.put("ClaimRemarks", claimRemarks);
            parameter.put("iopsuser", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));


            return Util.connectSOAP(Constants.uRLiOpsDriverClaimsSaveJobClaimJob, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsSaveJobClaimJob, Constants.APIDotNetMethodNameDriverClaimsSaveJobClaimJob, parameter);


        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Log.d("msg", "res " + aVoid);
            try {
                JSONObject jsonObject = new JSONObject(aVoid);
                JSONArray array = jsonObject.getJSONArray("jobclaimjobsave");
                JSONObject jsonObject1 = array.getJSONObject(0);
                String status = jsonObject1.getString("msg");

                if (status.equalsIgnoreCase("success")) {
                    etClaimAmt_extraCharg.setText("");
                    etRemark_extraCharge.setText("");
                }

                Toast.makeText(context, "" + status, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private class DriverClaimsGetJob extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("JobNo", jobNo);
                parameter.put("IOPSUserName", context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
//            return Util.connectSOAP(Constants.uRLiOpsUpdateJobRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobRejectedStatus, Constants.APIDotNetMethodNameUpdateJobRejectedStatus, parameter);
            return Util.connectSOAP(Constants.uRLiOpsGetSupplierAssignedJobsList, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetSupplierAssignedJobsList, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + MultipleJobNo + " Rejected Succesfully");
//					items = new ArrayList<iOP_Model>();
//					search = new ArrayList<iOP_Model>();
                    Toast.makeText(context, "Job #" + jobNo + " Rejected Succesfully", Toast.LENGTH_SHORT).show();

                    try {

                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Job #" + jobNo + " Rejected Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION, "ioperation");
                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.putExtra(Constants.intentKey_iOp, 1);
//                                        alertDialog.dismiss();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                    }
                                });

                        alertDialog.show();

                    } catch (Exception e) {
                        Util.modifiedLogTrace(e.getStackTrace().toString());
                    }


                } catch (Exception e) {
                    Util.showAlertCommonDialogWithOKBtn(context, "Error", "Operation Failed");
                }


            } else {

                Util.showAlertCommonDialogWithOKBtn(context, "Error ", "Operation Failed");

            }

        }
    }


}