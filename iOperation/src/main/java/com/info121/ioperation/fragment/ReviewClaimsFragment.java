package com.info121.ioperation.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.adapter.ReviewClaimAdapter;
import com.info121.ioperation.util.Util;
import com.info121.model.UnclaimJobListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewClaimsFragment extends Fragment implements View.OnClickListener {

    public TextView tvDate;
    RecyclerView rv;
    ArrayList<UnclaimJobListModel> selectedArraylist;
    TextView tvJobAmt, tvClaimAmt;
    ArrayList<Float> jobamtArray;
    ArrayList<Float> claimJobamtArray;
    Button btnSubmit, btnbackUnclaimed;
    ArrayList<String> jobNo;
    String ab, submitted, co;
    TextView tvClaimNo;
    TextView tvSubmmited, tvCo;
    private String claimNO;
    private SharedPreferences spp;
    private String selectedJobNoArray;
    private SharedPreferences sp;
    private String userName;
    private FragmentTransaction fragmentTransaction;
    private ReviewClaimAdapter mAdapter;
    private ArrayList<String> claimEditAmtArray;
    private ArrayList<String> claimEditRemarkArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_review_claims, container, false);
        sp = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE);
        userName = sp.getString(Util.LOGIN_KEY, "");
        rv = (RecyclerView) view.findViewById(R.id.rv_reviews);
        tvClaimAmt = (TextView) view.findViewById(R.id.tvclaimtotalamt);
        tvDate = (TextView) view.findViewById(R.id.tv_date_review_claims);

        tvDate.setText(getCurrentDate());
        tvJobAmt = (TextView) view.findViewById(R.id.tv_jobtotalamt);
        tvClaimNo = (TextView) view.findViewById(R.id.tv_claim_no_fragment_review_claims);
        tvSubmmited = (TextView) view.findViewById(R.id.tv_submmited_username);
        tvSubmmited.setText(userName);
        btnSubmit = (Button) view.findViewById(R.id.btnsubmit);
        btnbackUnclaimed = (Button) view.findViewById(R.id.btnbackunclaimed);
        tvCo = (TextView) view.findViewById(R.id.tv_co_reviewclaimsfragment);

        btnbackUnclaimed.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        claimJobamtArray = new ArrayList<>();
        jobamtArray = new ArrayList<>();
        spp = getActivity().getSharedPreferences("Jobarray", MODE_PRIVATE);
        sp = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE);
        jobNo = new ArrayList<>();
        selectedJobNoArray = spp.getString("data", "");
        selectedJobNoArray = selectedJobNoArray.replace(',', '|');
        userName = sp.getString(Util.LOGIN_KEY, "");


        selectedArraylist = BillingFragment.unclaimJobListModelsSelectedArrayz;
        mAdapter = new ReviewClaimAdapter(getActivity(), selectedArraylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        Float totalJobamt = 0f;
        Float totalClaimAmt = 0f;

        for (int i = 0; i < selectedArraylist.size(); i++) {

            UnclaimJobListModel unclaimJobListModel = selectedArraylist.get(i);
            unclaimJobListModel.getJobAmt();
            String jobamtstr = unclaimJobListModel.getJobAmt();
            String claimamtstr = unclaimJobListModel.getClaimAmount();
            co = unclaimJobListModel.getCo();
            submitted = unclaimJobListModel.getSubmittedBy();
            jobamtArray.add(Float.parseFloat(jobamtstr));

            claimJobamtArray.add(Float.parseFloat(claimamtstr));
            jobNo.add(unclaimJobListModel.getJobNo());

        }

        for (int j = 0; j < selectedArraylist.size(); j++) {

            Float temp = claimJobamtArray.get(j);
            totalClaimAmt = totalClaimAmt + temp;
            Float temp2 = jobamtArray.get(j);
            totalJobamt = totalJobamt + temp2;

        }

        StringBuffer stringBuffer = new StringBuffer();
        for (int p = 0; p < jobNo.size(); p++) {

            stringBuffer.append(jobNo.get(p));
            stringBuffer.append("|");
        }

        ab = stringBuffer.toString();

        tvJobAmt.setText(String.valueOf(totalJobamt));
        tvClaimAmt.setText(String.valueOf(totalClaimAmt));
        tvCo.setText(co);

        return view;
    }

    public String getCurrentDate() {

       /* Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int day = c.get(Calendar.DAY_OF_WEEK);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        String currentDate = day + "/" + month + "/" + year;*/

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        return currentDateTimeString;

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnsubmit:

                // TODO: 10-Feb-17 change api call
                btnSubmit.setEnabled(false);
                claimEditAmtArray = mAdapter.getClaimEditAmtArrayMethod();
                claimEditRemarkArray = mAdapter.getClaimEditRemarkArrayMethod();
                new SubmitUnclaimedJobListAsy().execute();

//                new GetGeneratedClaimNoAsy(0).execute();
//
//                claimEditAmtArray = mAdapter.getClaimEditAmtArrayMethod();
//                Log.d("msg", "ClaimEditAmtArray  " + claimEditAmtArray.get(0));


                // new SubmitUnclaimedJobListAsy().execute();
                //     BillingFragment.unclaimJobListModelsSelectedArrayz.clear();

                break;

            case R.id.btnbackunclaimed:

                BillingFragment billingFragment = new BillingFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, billingFragment);
                fragmentTransaction.commit();

                break;

        }
    }

    class SubmitUnclaimedJobListAsy extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
//            new GetGeneratedClaimNoAsy(0).execute();
        }

        @Override
        protected String doInBackground(Void... params) {

            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("Username", userName);
            return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsGetGeneratedClaimNo, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsGetGeneratedClaimNo, Constants.APIDotNetMethodNameClaimsGetGeneratedClaimNo, parameter);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (dialog != null) {
                dialog.dismiss();
            }

            try {
                Log.d("Response", s);
                JSONObject jsonMain = new JSONObject(s);
                JSONArray jsonArrayResult = jsonMain.getJSONArray("result");
//                JSONObject JSONObjectSingle = jsonArrayResult.getJSONObject(0);
//                Boolean isSuccess = JSONObjectSingle.getBoolean("isSuccess");

                if (jsonArrayResult.getJSONObject(0).getString("isSuccess").equalsIgnoreCase("true")) {

                    claimNO = jsonArrayResult.getJSONObject(0).getString("claimNo");
                    tvClaimNo.setText(claimNO);
                    new GetGeneratedClaimNoAsy(0).execute();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public class GetGeneratedClaimNoAsy extends AsyncTask<Void, Void, String> {

            ProgressDialog dialog;
            private int position;

            public GetGeneratedClaimNoAsy(int i) {
                this.position = i;
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {

                Hashtable<String, String> parameter = new Hashtable<>();
//            parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("JobNo", selectedArraylist.get(position).getJobNo());
                parameter.put("ClaimAmount", claimEditAmtArray.get(position));
                parameter.put("Remarks", claimEditRemarkArray.get(position));
                parameter.put("ClaimNo", claimNO);
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                for (Map.Entry<String, String> entry : parameter.entrySet()) {   //print keys and values
                    Log.d("HashMapValues", entry.getKey() + " : " + entry.getValue());
                }
                return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsSaveJob, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsSaveJob, Constants.APIDotNetMethodNameDriverClaimsSaveJob, parameter);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("msg", "reviewclaimsfragment res claimno :" + s);
/*
            {
                "result":[{
                "isSuccess":true, "claimNo":"CLM000033"
            }]}

*/
                if (dialog != null) {
                    dialog.dismiss();
                }


                if (s != null) {
                    if (selectedArraylist.size() > position + 1) {


                        Log.d("AndroidPosition", "" + selectedArraylist.size() + " -- " + position);
                        new GetGeneratedClaimNoAsy(position + 1).execute();

                    }

                    try {
                        if (selectedArraylist.size() == position + 1) {
                            JSONObject jsonMain = new JSONObject(s);
                            if (jsonMain.getJSONArray("jobclaimjobsave").getJSONObject(0).getString("msg").equalsIgnoreCase("success")) {
//                                new SubmitUnclaimedJobListAsy().execute();
//                                btnbackUnclaimed.performClick();
                                Toast.makeText(getActivity(), "Claim no is updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
// JSONObject jsonMain = new JSONObject(s);
////                    JSONArray jsonArrayResult = jsonMain.getJSONArray("result");
////                    JSONObject JSONObjectSingle = jsonArrayResult.getJSONObject(0);
////                    Boolean isSuccess = JSONObjectSingle.getBoolean("isSuccess");
////                    Log.d("msg", "issuccess  " + isSuccess);
////
////                    if (isSuccess.equals(true)) {
////                        claimNO = JSONObjectSingle.getString("claimNo");
////
////                        Log.d("msg", "claimNO  " + claimNO);
////
////                        tvClaimNo.setText(claimNO);
////
////
////                        for (int i = 0; i < selectedArraylist.size(); i++) {
////
////
////                            UnclaimJobListModel unclaimJobListModel = selectedArraylist.get(i);
////                            String jobNo = unclaimJobListModel.getJobNo();
////                            //  String claimAmt = unclaimJobListModel.getClaimAmount();
////                            String remark = unclaimJobListModel.getRemarks();
////                            String claimAmt = claimEditAmtArray.get(i);
////
////                            new DriverClaimsSaveJobAsy(jobNo, claimAmt, remark).execute();
////
////                        }
////
////
////                    } else {
////
////                    }
//
//
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        }


        class DriverClaimsSaveJobAsy extends AsyncTask<Void, Void, String> {
            String jobNo, claimAmt, remark;


            ProgressDialog dialog;


            public DriverClaimsSaveJobAsy(String jobNo, String claimAmt, java.lang.String remark) {
                this.jobNo = jobNo;
                this.claimAmt = claimAmt;
                this.remark = remark;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();


            }

            @Override
            protected String doInBackground(Void... params) {


                Hashtable<String, String> parameter = new Hashtable<>();
                parameter.put("JobNo", jobNo);
                parameter.put("ClaimNo", claimNO);
                parameter.put("ClaimAmt", claimAmt);
                parameter.put("Remarks", remark);
                parameter.put("Username", "bala");


                return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsSaveJob, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsSaveJob, Constants.APIDotNetMethodNameDriverClaimsSaveJob, parameter);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                Log.d("msg", "DriverClaimsSaveJobAsy  res " + s);

                if (dialog != null) {
                    dialog.dismiss();
                }

           /* {
                "result":[{
                "isSuccess":true, "msg":"Successfully saved."
            }]}*/

                try {

                    if (s != null) {

                        JSONObject jsonObjectMain = new JSONObject(s);
                        JSONArray jsonArrayResult = jsonObjectMain.getJSONArray("result");
                        JSONObject jsonObjectSingle = jsonArrayResult.getJSONObject(0);
                        Boolean isSuccess = jsonObjectSingle.getBoolean("isSuccess");

                        if (isSuccess.equals(true)) {
                            String msg = jsonObjectSingle.getString("msg");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        } else {

                            btnSubmit.setEnabled(true);
                        }


                    } else {
                        Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                        btnSubmit.setEnabled(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}


   /* @Override
    public void onStop() {
        super.onStop();

        BillingFragment.unclaimJobListModelsSelectedArrayz.clear();



    }*/





