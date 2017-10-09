package com.info121.ioperations.fragment;


import android.app.ProgressDialog;
import android.content.Context;
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
import com.info121.ioperations.R;
import com.info121.ioperations.adapter.DetailListingAdapter;
import com.info121.ioperations.util.Util;
import com.info121.model.DetailsListingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;


public class DetailsClaimListing extends Fragment implements View.OnClickListener {


    RecyclerView recyclerViewDetailsListing;
    private String claimNo;
    private ArrayList<DetailsListingModel> detailsListingsArrayModel;
    private TextView tvTotalJobAmt, tvTotalClaimAmt, tvDate, tvSupper, tvSubmitedBy, tvClaimNo;
    private Button btnbackunclaimed, btnsubmit;
    private FragmentTransaction fragmentTransaction;
    private DetailListingAdapter detailListingAdapter;
    private Float updatedTotalAmt=0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details_claim_listing, container, false);
        Bundle bundle = getArguments();
        claimNo = bundle.getString("claimno");
        init(view);


        new GetDetailsListing().execute();

        return view;

    }

    public void init(View view) {

        recyclerViewDetailsListing = (RecyclerView) view.findViewById(R.id.rv_detailsListing);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDetailsListing.setLayoutManager(layoutManager);
        recyclerViewDetailsListing.setItemAnimator(new DefaultItemAnimator());
        detailsListingsArrayModel = new ArrayList<>();
        tvTotalClaimAmt = (TextView) view.findViewById(R.id.tvclaimtotalamt);
        tvTotalJobAmt = (TextView) view.findViewById(R.id.tv_jobtotalamt);

        tvSubmitedBy = (TextView) view.findViewById(R.id.tv_submmited_username);
        tvSupper = (TextView) view.findViewById(R.id.tv_co_reviewclaimsfragment);
        tvDate = (TextView) view.findViewById(R.id.tv_date_review_claims);
        tvClaimNo = (TextView) view.findViewById(R.id.tv_claim_no_fragment_review_claims);
        btnbackunclaimed = (Button) view.findViewById(R.id.btnbackunclaimed);
        btnsubmit = (Button) view.findViewById(R.id.btnsubmit);

        btnbackunclaimed.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())

        {

            case R.id.btnbackunclaimed:


                Listing listing = new Listing();

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, listing);
                fragmentTransaction.commit();


                break;

            case R.id.btnsubmit:

                ArrayList<String> updteClaimAmtArray = detailListingAdapter.getClaimEditAmtArrayMethod();
                Log.d("msg", "update claim amt array " + updteClaimAmtArray.size());


                for (int i = 0; i < detailsListingsArrayModel.size(); i++) {


                    DetailsListingModel unclaimJobListModel = detailsListingsArrayModel.get(i);
                    String jobNo = unclaimJobListModel.getJobNo();
                    //  String claimAmt = unclaimJobListModel.getClaimAmount();
                    String claimAmt = updteClaimAmtArray.get(i);

                    Log.d("msg", "all details " + jobNo + " d " + claimNo + "  d  " + claimAmt);

                    new DriverClaimsUpdateJob(jobNo, claimNo, claimAmt).execute();

                    updatedTotalAmt=updatedTotalAmt+Float.parseFloat(claimAmt);

                }

                tvTotalClaimAmt.setText(String.valueOf(updatedTotalAmt));


                break;

        }
    }


    public class GetDetailsListing extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

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

            Log.d("msg", "userid  " + getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));

            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
            parameter.put("ClaimNo", claimNo);
            parameter.put("PageSize", "10");
            parameter.put("PagesPerSet", "1");
            parameter.put("PageNo", "1");

            return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsGetDetailedClaimListing, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsGetDetailedClaimListing, Constants.APIDotNetMethodNameUpdateClaimsGetDetailedClaimListing, parameter);

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog != null) {
                dialog.dismiss();
            }

            Log.d("msg", "detials listing res " + s);


            try {
                JSONObject jsonObjectMain = new JSONObject(s);
                JSONArray jsonArrayResult = jsonObjectMain.getJSONArray("result");
                JSONObject jsonObjectSingle = jsonArrayResult.getJSONObject(0);
                Boolean status = jsonObjectSingle.getBoolean("isSuccess");

                if (status == true) {


                    JSONObject jsonObjectData = jsonObjectSingle.getJSONObject("data");
                    JSONArray ListofJobs = jsonObjectData.getJSONArray("listOfJobs");

                    String totalJobAmount = jsonObjectData.getString("totalJobAmount");
                    String totalClaimAmount = jsonObjectData.getString("totalClaimAmount");
                    String supplierName = jsonObjectData.getString("supplierName");
                    String claimDate = jsonObjectData.getString("claimDate");


                    tvTotalClaimAmt.setText(totalClaimAmount);
                    tvTotalJobAmt.setText(totalJobAmount);
                    tvSubmitedBy.setText(getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                    tvDate.setText(claimDate);
                    tvClaimNo.setText(claimNo);

                    tvSupper.setText(supplierName);

                    for (int i = 0; i < ListofJobs.length(); i++) {


                        JSONObject jsonObjectListofjobs = ListofJobs.getJSONObject(i);
                        String row = jsonObjectListofjobs.getString("row");
                        String jobNo = jsonObjectListofjobs.getString("jobNo");
                        String opsType = jsonObjectListofjobs.getString("opsType");
                        String pax = jsonObjectListofjobs.getString("pax");
                        String addClaimAmount = jsonObjectListofjobs.getString("addClaimAmount");
                        String jobAmount = jsonObjectListofjobs.getString("jobAmount");
                        String totalAmount = jsonObjectListofjobs.getString("totalAmount");
                        String jobDate = jsonObjectListofjobs.getString("jobDate");
                        String jobStatus = jsonObjectListofjobs.getString("jobStatus");


                        DetailsListingModel detailsListingModel = new DetailsListingModel();
                        detailsListingModel.setRow(row);
                        detailsListingModel.setJobNo(jobNo);
                        detailsListingModel.setOpsType(opsType);
                        detailsListingModel.setPax(pax);
                        detailsListingModel.setAddClaimAmount(addClaimAmount);
                        detailsListingModel.setJobAmount(jobAmount);
                        detailsListingModel.setTotalAmount(totalAmount);
                        detailsListingModel.setJobDate(jobDate);
                        detailsListingModel.setJobStatus(jobStatus);

                        detailsListingsArrayModel.add(detailsListingModel);


                    }


                    detailListingAdapter = new DetailListingAdapter(getActivity(), detailsListingsArrayModel);
                    recyclerViewDetailsListing.setAdapter(detailListingAdapter);


                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();


            }


        }


    }


    public class DriverClaimsUpdateJob extends AsyncTask<Void, Void, String> {


        ProgressDialog dialog;
        String jobNo, claimNo, claimAmt;


        public DriverClaimsUpdateJob(String jobNo, String claimNo, String claimAmt) {


            this.jobNo = jobNo;
            this.claimNo = claimNo;
            this.claimAmt = claimAmt;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("JobNo", jobNo);
            parameter.put("ClaimNo", claimNo);
            parameter.put("ClaimAmount", claimAmt);
            parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));


            return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsUpdateJob, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsUpdateJob, Constants.APIDotNetMethodNameUpdateClaimsUpdateJob, parameter);



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (dialog != null) {
                dialog.dismiss();
            }

            Log.d("msg", "details update joblist res " + s);

            try {


                JSONObject jsonObjectMain = new JSONObject(s);
                JSONArray jsonArrayResult = jsonObjectMain.getJSONArray("result");
                JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);
                Boolean status = jsonObjectResult.getBoolean("isSuccess");
                if (status == true) {
                    String msg = jsonObjectResult.getString("msg");
                    Toast.makeText(getActivity(),""+ msg, Toast.LENGTH_SHORT).show();

                    btnsubmit.setEnabled(false);
/*
                    ReviewClaimsFragment reviewClaimsFragment=new ReviewClaimsFragment();
                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, reviewClaimsFragment);
                    fragmentTransaction.commit();*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
