package com.info121.ioperations.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperations.R;
import com.info121.ioperations.adapter.ListingAdapter;
import com.info121.ioperations.util.Util;
import com.info121.model.ListingModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class Listing extends Fragment implements View.OnClickListener {


    private ListView lvListing;
    private ArrayList<ListingModel> listingModelsArray;
    private Button btnHome, btnSearch;
    private ImageView ivFrom, ivTo;
    private TextView tvPendingClaim;
    private EditText edtFrom, edtTo;
    private TextView tvPartialClaim;
    private TextView tvApprovedClaim;
    private TextView tvPendingClaimAmt;
    private TextView tvPartialClaimAmt;
    private TextView tvApprovedClaiAmt;
    private Spinner spnStatus;
    private String selectedStatus = "all";
    private ArrayList<String> ListinstatusArray;
    private String userId;
    private FragmentTransaction fragmentTransaction;
    private int month;
    private int day;
    private int year;
    private Button btnUnclaims;
    private TextView tvEmpty;

    private TextView mNotiCount;
    private ImageView mNotiImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.billing_listing, container, false);
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        userId = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(Util.LOGIN_KEY, "");

        Log.d("msg", "listing screen username" + userId);
        init(view);

        ListinstatusArray.add("ALL");
        ListinstatusArray.add("PND");
        ListinstatusArray.add("PRT");
        ListinstatusArray.add("APR");


        // KZHTUN on 20170925
        mNotiCount = (TextView) view.findViewById(R.id.notification_count);
        mNotiImage = (ImageView) view.findViewById(R.id.notification_icon);

        mNotiCount.setText(String.valueOf(Constants.unreadCount));

        mNotiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageFragment messageFragment = new MessageFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, messageFragment);
                fragmentTransaction.commit();
            }
        });

        return view;


    }


    public void init(View view) {

        tvEmpty= (TextView) view.findViewById(R.id.tvemtyview);
        final String[] foo_array = getActivity().getResources().getStringArray(R.array.claimed_list);
        edtFrom = (EditText) view.findViewById(R.id.edtfromdate_fragment_billing);
        edtTo = (EditText) view.findViewById(R.id.edttodate_billing_fragment);
        lvListing = (ListView) view.findViewById(R.id.lstv_listing);
        tvPendingClaim = (TextView) view.findViewById(R.id.tv_pending_claims_listing);
        tvPartialClaim = (TextView) view.findViewById(R.id.tv_partial_claims_listing);
        tvApprovedClaim = (TextView) view.findViewById(R.id.tv_approved_listing);
        tvPendingClaimAmt = (TextView) view.findViewById(R.id.tv_pending_amt_listing);
        tvPartialClaimAmt = (TextView) view.findViewById(R.id.tv_partial_claims_amt_listing);
        tvApprovedClaiAmt = (TextView) view.findViewById(R.id.tv_approve_amt_listing);
        spnStatus = (Spinner) view.findViewById(R.id.spnStatusBillingListing);
        btnHome = (Button) view.findViewById(R.id.btnhome_billingFragment);
        btnHome.setOnClickListener(this);
        ivFrom = (ImageView) view.findViewById(R.id.ivCalender_from_iOperation);
        ivFrom.setOnClickListener(this);
        ivTo = (ImageView) view.findViewById(R.id.ivCalender_to_iOperation);
        ivTo.setOnClickListener(this);
        btnSearch = (Button) view.findViewById(R.id.btnsearch_billingFragment);
        btnSearch.setOnClickListener(this);
        btnUnclaims = (Button) view.findViewById(R.id.btn_unclaims_billingFragment);
        btnUnclaims.setOnClickListener(this);
        ListinstatusArray = new ArrayList<>();
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //      selectedStatus = foo_array[position];
                selectedStatus = ListinstatusArray.get(position);
                String fromStr = "";
                String toStr = "";
                new ListingAsy(fromStr, toStr).execute();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listingModelsArray = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnhome_billingFragment:
                HomeFragment bidingFragment = new HomeFragment();

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidingFragment);
                fragmentTransaction.commit();

                break;

            case R.id.btnsearch_billingFragment:

                String fromStr = edtFrom.getText().toString();
                String toStr = edtTo.getText().toString();
                new ListingAsy(fromStr, toStr).execute();
                break;

            case R.id.ivCalender_from_iOperation:

                try {

                    InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        if (view.isShown()) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            String dateFormate = "dd/MM/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormate);

                            edtFrom.setText("" + simpleDateFormat.format(calendar.getTime()));

                        }

                    }
                }, year, month, day);

                datePickerDialog.show();

                break;

            case R.id.ivCalender_to_iOperation:


                try {

                    InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                DatePickerDialog datePickerDialogto = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        if (view.isShown()) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            String dateFormate = "dd/MM/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormate);

                            edtTo.setText("" + simpleDateFormat.format(calendar.getTime()));

                        }

                    }
                }, year, month, day);

                datePickerDialogto.show();

                break;


            case R.id.btn_unclaims_billingFragment:

                BillingFragment billingFragmentt = new BillingFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, billingFragmentt);
                fragmentTransaction.commit();

                break;

        }

    }


    class ListingAsy extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;


        String fromDate, toDate;
        private ListingAdapter listingAdapter;

        public ListingAsy(String fromDate, String toDate) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        protected String doInBackground(Void... params) {

            Log.d("msg", "listing screen listingasy  selecteds status " + selectedStatus);

            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtable.put("Dtfrom", fromDate);
            hashtable.put("Dtto", toDate);
            hashtable.put("Username", userId);
            hashtable.put("Claimstatus", selectedStatus);
            hashtable.put("PageSize", "10");
            hashtable.put("PagesPerSet", "1");
            hashtable.put("PageNo", "1");


            return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsGetClaimListing, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsGetClaimListing, Constants.APIDotNetMethodNameClaimsGetClaimListing, hashtable);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            if (listingModelsArray != null) {
                listingModelsArray.clear();
            }


            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("msg", "listiing rs " + s);

            if (dialog != null) {
                dialog.dismiss();
            }



            try {

                JSONObject jsonObjectMain = new JSONObject(s);
                JSONArray jsonArrayResult = jsonObjectMain.getJSONArray("result");
                JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(0);
                Boolean status = jsonObjectResult.getBoolean("isSuccess");
                JSONObject jsonObjectData = jsonObjectResult.getJSONObject("data");

                String page = jsonObjectData.getString("page");
                String maxPage = jsonObjectData.getString("maxPages");
                String recordCount = jsonObjectData.getString("recordCount");
                String maxPages = jsonObjectData.getString("maxPages");
                String pendingCount = jsonObjectData.getString("pendingCount");
                String partialCount = jsonObjectData.getString("partialCount");
                String approvedCount = jsonObjectData.getString("approvedCount");
                String pendingAmt = jsonObjectData.getString("pendingAmt");
                String partialAmt = jsonObjectData.getString("partialAmt");
                String approvedAmt = jsonObjectData.getString("approvedAmt");


                tvPendingClaim.setText("Pending Claims:" + pendingCount);
                tvPartialClaim.setText("Partial Claims: " + partialCount);
                tvApprovedClaim.setText("Approved Claims: " + approvedCount);
                tvPendingClaimAmt.setText("Pending $: " + pendingAmt);
                tvPartialClaimAmt.setText("Partial $: " + partialAmt);
                tvApprovedClaiAmt.setText("Approved $: " + approvedAmt);

                JSONArray jsonArraylistofClaims = jsonObjectData.getJSONArray("listOfClaims");
                Log.d("msg", "array length listing " + jsonArraylistofClaims.length());

                if (status == true) {

                    for (int i = 0; i < jsonArraylistofClaims.length(); i++) {


                     /*   "row":"1",
                                "claimNo":"CLM000067",
                                "totalJobAmt":"120.00",
                                "totalAddClaimsAmt":"0.00",
                                "claimTotal":"120.00",
                                "status":"PND",
                                "claimDate":"10/20/2016 5:35:33 PM",
                                "totalJob":"2"
*/

                        JSONObject jsondata = jsonArraylistofClaims.getJSONObject(i);
                        String row = jsondata.getString("row");
                        String claimNo = jsondata.getString("claimNo");
                        String totalJobAmt = jsondata.getString("totalJobAmt");
                        String totalAddClaimsAmt = jsondata.getString("totalAddClaimsAmt");
                        String claimTotal = jsondata.getString("claimTotal");
                        String statusStr = jsondata.getString("status");
                        String claimDate = jsondata.getString("claimDate");
                        String totalJob = jsondata.getString("totalJob");


                        ListingModel listingModel = new ListingModel();
                        listingModel.setRow(row);
                        listingModel.setClaimNo(claimNo);
                        listingModel.setTotalJobAmt(totalJobAmt);
                        listingModel.setTotalAddClaimsAmt(totalAddClaimsAmt);
                        listingModel.setClaimTotal(claimTotal);
                        listingModel.setStatus(statusStr);
                        listingModel.setClaimDate(claimDate);
                        listingModel.setTotalJob(totalJob);


                        listingModelsArray.add(listingModel);


                    }

                    Log.d("msg", "adapter array size listing screen " + listingModelsArray.size());

                    listingAdapter = new ListingAdapter(getActivity(), listingModelsArray, getActivity().getSupportFragmentManager());
                    lvListing.setAdapter(listingAdapter);
                    listingAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();

                    lvListing.setEmptyView(tvEmpty);
                }

            } catch (Exception e) {
                e.printStackTrace();
                listingAdapter = new ListingAdapter(getActivity(), listingModelsArray, getActivity().getSupportFragmentManager());
                lvListing.setAdapter(listingAdapter);
            }

        }
    }
}
