package com.info121.ioperations.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperations.R;
import com.info121.ioperations.adapter.UnclaimJobListAdapter;
import com.info121.ioperations.util.Util;
import com.info121.model.UnclaimJobListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeSet;

/**
 * Created by archirayan1 on 3/30/2016.
 */
public class BillingFragment extends Fragment implements View.OnClickListener {


    public static ArrayList<UnclaimJobListModel> unclaimJobListModelsSelectedArrayz;
    ListView lsvBillingJObList;
    EditText edtFromDate, edtToDate, edtPageNo;
    Button btnUnclaimJOb;
    // Spinner spnrList;
    TextView tvTotalJobValue;
    ArrayList<UnclaimJobListModel> unclaimJobListModels;
    ImageView imgFromDate, imgToDate;
    int showCount = 0;
    int noShowCount = 0;
    int unclaimCount = 0;
    TextView tvShowJob, tvNoShow, tvUnhandled;
    HashMap<Integer, ArrayList<Boolean>> listHashMap;
    private ArrayList<Float> jobTotalValue;
    private float totalJobListValue;
    private int month;
    private int day;
    private int year;
    private Button btnSearch, btnNextPage;
    private Button btnHome, btnSubmitClaim, btnGo, btnPrevious;
    private UnclaimJobListAdapter unclaimJobListAdapter;
    private int whichPage = 1;
    private String maxPages;
    private String pageNO;
    private String fromDateStr = "";
    private String toDateStr = "";
    private ArrayList<String> unclaimdata;
    private ArrayAdapter<String> dataAdapter;
    private SharedPreferences sp;
    private String userName;
    private String selectedJobNoArray;
    private SharedPreferences spp;
    private ArrayList<UnclaimJobListModel> unclaimJobListModelsSelectedArray;
    private FragmentTransaction fragmentTransaction;
    private  Button btnListing;

    private TextView mNotiCount;
    private ImageView mNotiImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_billing, container, false);

        listHashMap = new HashMap<>();
        unclaimJobListModelsSelectedArray = new ArrayList<>();
        unclaimJobListModelsSelectedArrayz = new ArrayList<>();
        sp = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE);
        sp.getString(Util.LOGIN_KEY, "");
        spp = getActivity().getSharedPreferences("Jobarray", Context.MODE_PRIVATE);
        userName = sp.getString(Util.LOGIN_KEY, "");
        Log.d("msg", "username billingfragment " + userName);
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        //     spnrList = (Spinner) v.findViewById(R.id.spinneritem);
        unclaimdata = new ArrayList<String>();
        unclaimdata.add("Unclaimed Jobs");
        unclaimdata.add("Claim Listing");

      /*  dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, R.id.spinner_item, unclaimdata);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spnrList.setAdapter(dataAdapter);*/

       /* spnrList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                    new GetuncliamJoblist("billing").execute();

                } else if (position == 1) {

                    Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        lsvBillingJObList = (ListView) v.findViewById(R.id.lsv_claim_unclaim_billingFragment);

        lsvBillingJObList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = lsvBillingJObList.getItemAtPosition(position);
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });


        btnUnclaimJOb = (Button) v.findViewById(R.id.btn_unclaims_billingFragment);
        btnSubmitClaim = (Button) v.findViewById(R.id.btn_submit_claims_billing);
        btnPrevious = (Button) v.findViewById(R.id.ButtoniOpPrevious);
        btnPrevious.setOnClickListener(this);
        btnNextPage = (Button) v.findViewById(R.id.ButtoniOpNext);
        btnGo = (Button) v.findViewById(R.id.ButtoniOpGo);
        btnGo.setOnClickListener(this);

        btnNextPage.setOnClickListener(this);

        btnSubmitClaim.setOnClickListener(this);
        btnUnclaimJOb.setOnClickListener(this);
        edtFromDate = (EditText) v.findViewById(R.id.edtfromdate_fragment_billing);
        edtToDate = (EditText) v.findViewById(R.id.edttodate_billing_fragment);
        edtPageNo = (EditText) v.findViewById(R.id.editTextiOpPage);
        imgFromDate = (ImageView) v.findViewById(R.id.ivCalender_from_iOperation);
        imgFromDate.setOnClickListener(this);
        imgToDate = (ImageView) v.findViewById(R.id.ivCalender_to_iOperation);
        imgToDate.setOnClickListener(this);
        tvShowJob = (TextView) v.findViewById(R.id.tv_showjob_billingFragment);
        tvNoShow = (TextView) v.findViewById(R.id.tv_no_show_billingFragment);
        tvUnhandled = (TextView) v.findViewById(R.id.tv_unhandled_billingFragment);
        btnSearch = (Button) v.findViewById(R.id.btnsearch_billingFragment);
        btnSearch.setOnClickListener(this);
        btnHome = (Button) v.findViewById(R.id.btnhome_billingFragment);
        btnListing= (Button) v.findViewById(R.id.btlisting_billingFragment);
        btnListing.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        unclaimJobListModels = new ArrayList<>();
        jobTotalValue = new ArrayList<>();
        tvTotalJobValue = (TextView) v.findViewById(R.id.tv_total_billingFragment);

        edtFromDate.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(edtFromDate) {
            @Override
            public boolean onDrawableClick() {

                edtFromDate.setText("");
                if (edtToDate.getText().toString().equalsIgnoreCase("") && edtFromDate.getText().toString().equalsIgnoreCase("")) {

                    toDateStr = "";
                    fromDateStr = "";

                    new GetuncliamJoblist("billing").execute();

                }

                return true;
            }
        });

        edtToDate.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(edtToDate) {
            @Override
            public boolean onDrawableClick() {

                edtToDate.setText("");

                if (edtToDate.getText().toString().equalsIgnoreCase("") && edtFromDate.getText().toString().equalsIgnoreCase("")) {

                    toDateStr = "";
                    fromDateStr = "";

                    new GetuncliamJoblist("billing").execute();
                }

                return true;
            }
        });


        if (Util.isNetworkAvailable(getActivity())) {


            new GetuncliamJoblist("billing").execute();


        } else {

            Toast.makeText(getActivity(), "No internet connection found,please check your internet connectivity.", Toast.LENGTH_SHORT).show();
        }

        // KZHTUN on 20170925
        mNotiCount = (TextView) v.findViewById(R.id.notification_count);
        mNotiImage = (ImageView) v.findViewById(R.id.notification_icon);

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

        return v;

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {


         /*   case R.id.btn_unclaims_billingFragment:
                Log.d("msg", "username   " + getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));


                break;*/


            case  R.id.btlisting_billingFragment:

                Listing listing=new Listing();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, listing);
                fragmentTransaction.commit();



                break;

            case R.id.ivCalender_from_iOperation:

                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                DatePickerDialog datePickerDialogFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        if (view.isShown()) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String stringFormate = "dd/MM/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringFormate);


                            edtFromDate.setText("" + simpleDateFormat.format(calendar.getTime()));


                        }

                    }
                }, year, month, day);
                datePickerDialogFrom.show();




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

                            edtToDate.setText("" + simpleDateFormat.format(calendar.getTime()));

                        }

                    }
                }, year, month, day);

                datePickerDialogto.show();


                break;

            case R.id.btnsearch_billingFragment:

                fromDateStr = edtFromDate.getText().toString();
                toDateStr = edtToDate.getText().toString();


                if (fromDateStr.equalsIgnoreCase("") && toDateStr.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please Select Date", Toast.LENGTH_SHORT).show();
                } else {

                    if (whichPage <= 0) {


                    } else {
                        new GetuncliamJoblistSearch(fromDateStr, toDateStr).execute();
                    }

                }


                break;

            case R.id.btnhome_billingFragment:

                HomeFragment bidingFragment = new HomeFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidingFragment);
                fragmentTransaction.commit();

                break;


            case R.id.btn_submit_claims_billing:

                if (unclaimJobListAdapter != null) {

                    unclaimJobListAdapter.storeSharePre();
                    unclaimJobListModelsSelectedArray = unclaimJobListAdapter.getUnclaimSelectedJValue();

                }

                Log.d("msg", "unclaimJobListModelsSelectedArray  " + unclaimJobListModelsSelectedArray.size());


                selectedJobNoArray = spp.getString("data", "");
                selectedJobNoArray = selectedJobNoArray.replace(',', '|');
                Log.d("msg", "submit final value  " + selectedJobNoArray);
                TreeSet<String> jobNoArray = unclaimJobListAdapter.getJobNoArray();

                StringBuffer stringBuffer = new StringBuffer();
               /* for(int i=0;i<jobNoArray.size();i++)
                {
                    stringBuffer.append()
                }*/


                if (jobNoArray != null) {
                    Log.d("msg", "array jobno  " + jobNoArray);
                    jobNoArray.clear();
                }

                unclaimJobListAdapter.notifyDataSetChanged();

                new SubmitUnclaimedJobListAsy().execute();


                ReviewClaimsFragment reviewClaimsFragment = new ReviewClaimsFragment();

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, reviewClaimsFragment);
                fragmentTransaction.commit();


                break;

            case R.id.ButtoniOpPrevious:


                whichPage = whichPage - 1;

                if (whichPage <= 0) {
                    btnPrevious.setVisibility(View.GONE);
                }


                if (Util.isNetworkAvailable(getActivity())) {

                    if (whichPage <= 0) {

                    } else {

                        if (fromDateStr.equalsIgnoreCase("") && toDateStr.equalsIgnoreCase("")) {
                            new GetuncliamJoblist("billing").execute();
                        } else {
                            new GetuncliamJoblistSearch(fromDateStr, toDateStr).execute();
                        }

                    }

                } else {

                    Toast.makeText(getActivity(), "No internet connection found,please check your internet connectivity.", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.ButtoniOpGo:


                btnPrevious.setVisibility(View.VISIBLE);

                String pageNoStr = edtPageNo.getText().toString();
                if (!pageNoStr.equalsIgnoreCase("")) {
                    whichPage = Integer.parseInt(pageNoStr);
                }


                if (edtPageNo.getText().toString().compareTo("") == 0) {
                    Util.showAlertCommonDialogWithOKBtn(getActivity(), "Error", "Please insert page number");
                } else {


                    int maxPagesInt = Integer.parseInt(maxPages);

                    int temp = Integer.parseInt(edtPageNo.getText().toString());
                    if ((temp < 1 || temp > maxPagesInt)) {
                        Util.showAlertCommonDialogWithOKBtn(getActivity(), "Error", "page number is out of range");
                    } else {

                        if (Util.isNetworkAvailable(getActivity())) {

                            if (whichPage <= 0) {

                            } else {

                                if (fromDateStr.equalsIgnoreCase("") && toDateStr.equalsIgnoreCase("")) {
                                    new GetuncliamJoblist("billing").execute();
                                } else {
                                    new GetuncliamJoblistSearch(fromDateStr, toDateStr).execute();
                                }

                            }

                        } else {

                            Toast.makeText(getActivity(), "No internet connection found,please check your internet connectivity.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    edtPageNo.setText("");


                }
                break;

            case R.id.ButtoniOpNext:

                if (unclaimJobListAdapter != null) {

                    unclaimJobListAdapter.storeSharePre();
                }


                whichPage = whichPage + 1;
                btnPrevious.setVisibility(View.VISIBLE);


                if (Util.isNetworkAvailable(getActivity())) {


                    if (whichPage <= 0) {

                    } else {
                        if (fromDateStr.equalsIgnoreCase("") && toDateStr.equalsIgnoreCase("")) {

                            new GetuncliamJoblist("billing").execute();

                        } else {
                            new GetuncliamJoblistSearch(fromDateStr, toDateStr).execute();
                        }


                    }

                } else {
                    Toast.makeText(getActivity(), "No internet connection found,please check your internet connectivity.", Toast.LENGTH_SHORT).show();
                }


                break;


        }


    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sp = getActivity().getSharedPreferences("Jobarray", Context.MODE_PRIVATE);
        sp.edit().clear().commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        SharedPreferences sp = getActivity().getSharedPreferences("Jobarray", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


    class SubmitUnclaimedJobListAsy extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            Hashtable<String, String> parameter = new Hashtable<>();
            parameter.put("JobnoString", selectedJobNoArray);
            parameter.put("Username", userName);

            return Util.connectSOAP(Constants.uRLIOPS_DriverClaimsListJobsForClaim, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActionIOPS_DriverClaimsListJobsForClaim, Constants.APIDotNetMethodNameDriverClaimsListJobsForClaim, parameter);
            //  return Util.connectSOAP(Constants.uRLiOpsUpdateJobRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateJobRejectedStatus, Constants.APIDotNetMethodNameUpdateJobRejectedStatus, parameter);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("msg", "billingfragment submit call api res  " + s);


            try {
                JSONObject jsonmain = new JSONObject(s);
                JSONArray jsonArraySelectedForClaims = jsonmain.getJSONArray("JobsSelectedForClaims");


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class GetuncliamJoblist extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        String fromDateStr;
        String toDateStr;
        String btnStatus;
        private String Data;


        public GetuncliamJoblist(String btnStatus) {

            this.btnStatus = btnStatus;
        }

        public GetuncliamJoblist(String fromDateStr, String toDateStr, String btnStatus) {
            this.fromDateStr = fromDateStr;
            this.toDateStr = toDateStr;
            this.btnStatus = btnStatus;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();


        }

        @Override
        protected String doInBackground(Void... params) {


            try {


                if (unclaimJobListModels != null) {
                    unclaimJobListModels.clear();
                }


                Hashtable<String, String> parameter = null;

                if (fromDateStr == null && toDateStr == null && btnStatus.equalsIgnoreCase("billing")) {

               /* parameter = new Hashtable<>();
                parameter.put("dtfrom", fromDateStr);
                parameter.put("dtto", toDateStr);
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                Log.d("msg", "with date parameter  " + fromDateStr + "  to  " + toDateStr);
*/

                    parameter = new Hashtable<>();
                    parameter.put("dtfrom", "");
                    parameter.put("dtto", "");
                    parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                    parameter.put("PageSize", "10");
                    parameter.put("PagesPerSet", "1000");
                    parameter.put("PageNo", String.valueOf(whichPage));
                } else if (fromDateStr.length() == 0 && toDateStr.length() == 0 && btnStatus.equalsIgnoreCase("search")) {

               /* parameter = new Hashtable<>();
                parameter.put("dtfrom", "");
                parameter.put("dtto", "");
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
*/
                    Log.d("msg", "no date parameter  ");
                } else if (fromDateStr.length() >= 1 && toDateStr.length() >= 1 && btnStatus.equalsIgnoreCase("search")) {


                    parameter = new Hashtable<>();
                    parameter.put("dtfrom", fromDateStr);
                    parameter.put("dtto", toDateStr);
                    parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                    parameter.put("PageSize", "10");
                    parameter.put("PagesPerSet", "1000");
                    parameter.put("PageNo", String.valueOf(whichPage));

                    Log.d("msg", "with date parameter  " + fromDateStr + "  to  " + toDateStr);
                    Log.d("msg", "no date parameter  ");


                }


                // Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);

                Data = Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetMethodNameDriverClaimsGetUnclaimedJobs, parameter);
//            Log.d("DataRuju",Data);
            } catch (Exception e) {

            }

            return Data;


        }


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);


            if (dialog != null) {

                dialog.dismiss();
            }

            //   Log.d("res", "getunclaims job list abc      " +    s.toString()                 + "end");
            //    Toast.makeText(getActivity(), "res " + s, Toast.LENGTH_LONG).show();


            try {

                if (s != null) {


                    JSONObject jsonObject = new JSONObject(s);
                    //     Log.d("msg", "res  static" + jsonObject);
                    Log.d("msg", "res unclaimed job  dy  " + s);

                    JSONArray jsonArrayJobclaimun = jsonObject.getJSONArray("jobclaimunclaimedjobdisplay");


                    btnUnclaimJOb.setText("unClaims " + "(" + jsonArrayJobclaimun.length() + ")");
                    for (int i = 0; i < jsonArrayJobclaimun.length(); i++) {

                        JSONObject jsonSingle = jsonArrayJobclaimun.getJSONObject(i);

                        String auth = jsonSingle.getString("auth");
                        String opsdate = jsonSingle.getString("opsdate");
                        String opstype = jsonSingle.getString("opstype");
                        String pax = jsonSingle.getString("pax");
                        String JobAmt = jsonSingle.getString("JobAmt");
                        String ClaimAmount = jsonSingle.getString("ClaimAmount");
                        String UnclaimedAmt = jsonSingle.getString("UnclaimedAmt");
                        String JobNo = jsonSingle.getString("JobNo");
                        String jobstatus = jsonSingle.getString("jobstatus");

                        String remarks = jsonSingle.getString("remarks");
                        String ClaimNo = jsonSingle.getString("ClaimNo");
                        String ClaimStatus = jsonSingle.getString("ClaimStatus");
                        pageNO = jsonSingle.getString("page");
                        String recordsCOunt = jsonSingle.getString("recordscount");
                        maxPages = jsonSingle.getString("maxpages ");
                        String submittedBy = jsonSingle.getString("submittedby");
                        String co = jsonSingle.getString("co");

                        float claimAmountInt = Float.parseFloat(ClaimAmount);
                        float jobAmtInt = Float.parseFloat(JobAmt);
                        Float total = claimAmountInt + jobAmtInt;
                        jobTotalValue.add(total);


                        //    Log.d("msg", "job statuss     " + jobstatus);
                        if (jobstatus.equalsIgnoreCase("Unhandled")) {
                            unclaimCount++;
                            //      Log.d("msg", "unclaimcount     " + unclaimCount);

                        } else if (jobstatus.equalsIgnoreCase("Show")) {
                            showCount++;
                            //Log.d("msg", "job show     " + showCount);


                        } else if (jobstatus.equalsIgnoreCase("No Show")) {

                            noShowCount++;
                            //     Log.d("msg", "no show count     " + noShowCount);


                        }


                        Log.d("res", "getunclaims job list auth   " + auth);
                        UnclaimJobListModel unclaimJobListModel = new UnclaimJobListModel();
                        unclaimJobListModel.setClaimAmount(ClaimAmount);
                        unclaimJobListModel.setAuth(auth);
                        unclaimJobListModel.setOpsdate(opsdate);
                        unclaimJobListModel.setOpstype(opstype);
                        unclaimJobListModel.setPax(pax);
                        unclaimJobListModel.setJobAmt(JobAmt);
                        unclaimJobListModel.setUnclaimedAmt(UnclaimedAmt);
                        unclaimJobListModel.setJobNo(JobNo);
                        unclaimJobListModel.setJobstatus(jobstatus);
                        unclaimJobListModel.setRemarks(remarks);
                        unclaimJobListModel.setClaimNo(ClaimNo);
                        unclaimJobListModel.setClaimStatus(ClaimStatus);
                        unclaimJobListModel.setPageNo(pageNO);
                        unclaimJobListModel.setRecordscount(recordsCOunt);
                        unclaimJobListModel.setMaxpages(maxPages);
                        unclaimJobListModel.setCo(co);
                        unclaimJobListModel.setSubmittedBy(submittedBy);


                        unclaimJobListModels.add(unclaimJobListModel);


                    }

                    edtPageNo.setHint("pages " + pageNO + " of " + maxPages);



                    /*HashMap<String, ArrayList<UnclaimJobListModel>> map = new HashMap<>();
                    int count=0;
                    ArrayList<UnclaimJobListModel> unclaimJobListModelsArray=null;

                    for (int i = 0; i < unclaimJobListModels.size(); i++) {


                        if(count==0)
                        {
                            unclaimJobListModelsArray  = new ArrayList<>();


                        }


                            if (count <= 9) {

                                UnclaimJobListModel unclaimJobListModel = unclaimJobListModels.get(i);
                                unclaimJobListModelsArray.add(unclaimJobListModel);
                                count++;

                                if(count==9)
                                {
                                    count=0;
                                }



                            }

                        if(count==10)
                        {
                            map.put("item"+i,unclaimJobListModelsArray);
                        }


                    }

                    Log.d("msg","hashmap size  "+map.size());
*/


                    tvNoShow.setText("Job (No Show) : " + noShowCount);
                    tvShowJob.setText("Job (show) : " + showCount);
                    tvUnhandled.setText("Unhandled : " + unclaimCount);


                    unclaimJobListAdapter = new UnclaimJobListAdapter(getActivity(), unclaimJobListModels);
                    lsvBillingJObList.setAdapter(unclaimJobListAdapter);


                    Log.d("msg", "jobtotal " + jobTotalValue.size());


                    for (int i = 0; i < jobTotalValue.size(); i++) {

                        float value = jobTotalValue.get(i);
                        totalJobListValue = totalJobListValue + value;

                    }

                    tvTotalJobValue.setText("Total : " + totalJobListValue);

                } else {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    class GetuncliamJoblistSearch extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        String fromDateStr;
        String toDateStr;

        private String Data;


        public GetuncliamJoblistSearch(String fromDateStr, String toDateStr) {
            this.fromDateStr = fromDateStr;
            this.toDateStr = toDateStr;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();


        }

        @Override
        protected String doInBackground(Void... params) {


            try {


                if (unclaimJobListModels != null) {
                    unclaimJobListModels.clear();
                }


                Hashtable<String, String> parameter = null;



               /* parameter = new Hashtable<>();
                parameter.put("dtfrom", fromDateStr);
                parameter.put("dtto", toDateStr);
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                Log.d("msg", "with date parameter  " + fromDateStr + "  to  " + toDateStr);
*/




               /* parameter = new Hashtable<>();
                parameter.put("dtfrom", "");
                parameter.put("dtto", "");
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
*/


                parameter = new Hashtable<>();
                parameter.put("dtfrom", fromDateStr);
                parameter.put("dtto", toDateStr);
                parameter.put("Username", getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "10");
                parameter.put("PagesPerSet", "1000");
                parameter.put("PageNo", String.valueOf(whichPage));

                Log.d("msg", "with date parameter  " + fromDateStr + "  to  " + toDateStr);
                Log.d("msg", "no date parameter  ");


                // Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetMethodNameGetSupplierAssignedJobsList, parameter);

                Data = Util.connectSOAP(Constants.uRLiOpsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetNameSpace, Constants.ApiDotNetSOAPActionopsDriverClaimsGetUnclaimedJobs, Constants.APIDotNetMethodNameDriverClaimsGetUnclaimedJobs, parameter);
//            Log.d("DataRuju",Data);
            } catch (Exception e) {

            }

            return Data;


        }


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);


            if (dialog != null) {

                dialog.dismiss();
            }

            //   Log.d("res", "getunclaims job list abc      " +    s.toString()                 + "end");
            //    Toast.makeText(getActivity(), "res " + s, Toast.LENGTH_LONG).show();


            try {

                if (s != null) {


                    JSONObject jsonObject = new JSONObject(s);
                    Log.d("msg", "res  static" + jsonObject);
                    Log.d("msg", "res unclaimed job  dy  " + s);

                    JSONArray jsonArrayJobclaimun = jsonObject.getJSONArray("jobclaimunclaimedjobdisplay");


                    //btnUnclaimJOb.setText("unClaims " + "(" + jsonArrayJobclaimun.length() + ")");
                    for (int i = 0; i < jsonArrayJobclaimun.length(); i++) {

                        JSONObject jsonSingle = jsonArrayJobclaimun.getJSONObject(i);

                        String auth = jsonSingle.getString("auth");
                        String opsdate = jsonSingle.getString("opsdate");
                        String opstype = jsonSingle.getString("opstype");
                        String pax = jsonSingle.getString("pax");
                        String JobAmt = jsonSingle.getString("JobAmt");
                        String ClaimAmount = jsonSingle.getString("ClaimAmount");
                        String UnclaimedAmt = jsonSingle.getString("UnclaimedAmt");
                        String JobNo = jsonSingle.getString("JobNo");
                        String jobstatus = jsonSingle.getString("jobstatus");

                        String remarks = jsonSingle.getString("remarks");
                        String ClaimNo = jsonSingle.getString("ClaimNo");
                        String ClaimStatus = jsonSingle.getString("ClaimStatus");
                        pageNO = jsonSingle.getString("page");
                        String recordsCOunt = jsonSingle.getString("recordscount");
                        maxPages = jsonSingle.getString("maxpages ");


                        float claimAmountInt = Float.parseFloat(ClaimAmount);
                        float jobAmtInt = Float.parseFloat(JobAmt);
                        Float total = claimAmountInt + jobAmtInt;
                        jobTotalValue.add(total);


                        Log.d("msg", "job statuss     " + jobstatus);
                        if (jobstatus.equalsIgnoreCase("Unhandled")) {
                            unclaimCount++;
                            Log.d("msg", "unclaimcount     " + unclaimCount);

                        } else if (jobstatus.equalsIgnoreCase("Show")) {
                            showCount++;
                            Log.d("msg", "job show     " + showCount);


                        } else if (jobstatus.equalsIgnoreCase("No Show")) {

                            noShowCount++;
                            Log.d("msg", "no show count     " + noShowCount);


                        }


                        //       Log.d("res", "getunclaims job list auth   " + auth);
                        UnclaimJobListModel unclaimJobListModel = new UnclaimJobListModel();
                        unclaimJobListModel.setClaimAmount(ClaimAmount);
                        unclaimJobListModel.setAuth(auth);
                        unclaimJobListModel.setOpsdate(opsdate);
                        unclaimJobListModel.setOpstype(opstype);
                        unclaimJobListModel.setPax(pax);
                        unclaimJobListModel.setJobAmt(JobAmt);
                        unclaimJobListModel.setUnclaimedAmt(UnclaimedAmt);
                        unclaimJobListModel.setJobNo(JobNo);
                        unclaimJobListModel.setJobstatus(jobstatus);
                        unclaimJobListModel.setRemarks(remarks);
                        unclaimJobListModel.setClaimNo(ClaimNo);
                        unclaimJobListModel.setClaimStatus(ClaimStatus);
                        unclaimJobListModel.setPageNo(pageNO);
                        unclaimJobListModel.setRecordscount(recordsCOunt);
                        unclaimJobListModel.setMaxpages(maxPages);

                        unclaimJobListModels.add(unclaimJobListModel);


                    }

                    edtPageNo.setHint("pages " + pageNO + " of " + maxPages);



                    /*HashMap<String, ArrayList<UnclaimJobListModel>> map = new HashMap<>();
                    int count=0;
                    ArrayList<UnclaimJobListModel> unclaimJobListModelsArray=null;

                    for (int i = 0; i < unclaimJobListModels.size(); i++) {


                        if(count==0)
                        {
                            unclaimJobListModelsArray  = new ArrayList<>();


                        }


                            if (count <= 9) {

                                UnclaimJobListModel unclaimJobListModel = unclaimJobListModels.get(i);
                                unclaimJobListModelsArray.add(unclaimJobListModel);
                                count++;

                                if(count==9)
                                {
                                    count=0;
                                }



                            }

                        if(count==10)
                        {
                            map.put("item"+i,unclaimJobListModelsArray);
                        }


                    }

                    Log.d("msg","hashmap size  "+map.size());
*/


                    tvNoShow.setText("Job (No Show) : " + noShowCount);
                    tvShowJob.setText("Job (show) : " + showCount);
                    tvUnhandled.setText("Unhandled : " + unclaimCount);


                    unclaimJobListAdapter = new UnclaimJobListAdapter(getActivity(), unclaimJobListModels);
                    lsvBillingJObList.setAdapter(unclaimJobListAdapter);

                    Log.d("msg", "jobtotal " + jobTotalValue.size());


                    for (int i = 0; i < jobTotalValue.size(); i++) {

                        float value = jobTotalValue.get(i);
                        totalJobListValue = totalJobListValue + value;

                    }

                    tvTotalJobValue.setText("Total : " + totalJobListValue);

                } else {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
