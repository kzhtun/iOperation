package com.info121.ioperation.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.SurveyActivity;
import com.info121.ioperation.iOPLoginActivity;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.iBid_Model;
import com.info121.model.iMessage;
import com.info121.model.iOP_Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by archirayan1 on 3/29/2016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    Button btIncoming, btCalender, btViewJobs, btToDO, btBilling, btExit;
    public static Context context;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
//    private BadgeView badge1;
//    private BadgeView badge2;
    private int no_jobs = 0;
    private int no_bids = 0;
    private List<String> listJobNo;
    private List<String> listBidNo;
    private ProgressDialog progress;
    private Button tvJob, tvBid;

    private ImageView mExit;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btCalender = (Button) v.findViewById(R.id.btCalender_chooseTask);
        btViewJobs = (Button) v.findViewById(R.id.btViewJobs_chooseTask);
        btBilling = (Button) v.findViewById(R.id.btBilling_chooseTask);
        btExit = (Button) v.findViewById(R.id.btExit_chooseTask);
        tvBid = (Button) v.findViewById(R.id.tvBidCount_HomeFragment);
        tvJob = (Button) v.findViewById(R.id.tvJobCount_HomeFragment);

        mExit = (ImageView) v.findViewById(R.id.exit);

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOutDialog(getActivity(), "Exit", "logout  from the app?  ");
            }
        });


        btCalender.setOnClickListener(this);
        btViewJobs.setOnClickListener(this);
        btBilling.setOnClickListener(this);
        btExit.setOnClickListener(this);
        tvJob.setOnClickListener(this);
        tvBid.setOnClickListener(this);

        context = getActivity();

        mProgressDialog = new ProgressDialog(context);

        if (Util.isNetworkAvailable(getActivity())) {
            new getAllJobNoListAsyncTask().execute();
            new getAllMessageAsyncTask().execute();
        } else {
            Toast.makeText(getActivity(), "No internet connection found,please check your internet connectivity.", Toast.LENGTH_SHORT).show();
        }





        return v;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

//            case R.id.btIncomming_chooseTask:
//                iBiddingFragment bidingFragment = new iBiddingFragment();
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, bidingFragment);
//                fragmentTransaction.commit();
//
//                break;

            case R.id.btCalender_chooseTask:
//                CalenderFragment calenderFragment = new CalenderFragment();
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, calenderFragment);
//                fragmentTransaction.commit();
                showDialog(getActivity(),"iOperation","This services will be available in time to come");
                break;

            case R.id.btViewJobs_chooseTask:

                // KZHTUN on 10102017 add survey screen
                //showDialog(getActivity(),"iOperation","This services will be available in time to come");

                startActivity(new Intent(getActivity(), SurveyActivity.class));



                break;

//            case R.id.btToDo_chooseTask:
//                iOperationFragment toDoFragment = new iOperationFragment();
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, toDoFragment);
//                fragmentTransaction.commit();
//
//                break;

            case R.id.btBilling_chooseTask:
                BillingFragment billingFragment = new BillingFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, billingFragment);
                fragmentTransaction.commit();
               // showDialog(getActivity(),"iOperation","This services will be available in time to come");
                break;

            case R.id.btExit_chooseTask:
                //showLogOutDialog(getActivity(), "Exit", "logout  from the app?  ");
                MessageFragment messageFragment = new MessageFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, messageFragment);
                fragmentTransaction.commit();

                break;

            case R.id.tvJobCount_HomeFragment:

                iOperationFragment ToDoFragment = new iOperationFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, ToDoFragment);
                fragmentTransaction.commit();


                break;

            case R.id.tvBidCount_HomeFragment:
                iBiddingFragment bidFragment = new iBiddingFragment();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, bidFragment);
                fragmentTransaction.commit();
                break;

        }

    }


    public void showDialog(Context ctx, String title, String message) {
        try {

            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
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

                            SharedPreferences settings = getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE);
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

                            Intent intent = new Intent(getActivity(), iOPLoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();




                            return;

                        }
                    });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {

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
                progress = ProgressDialog.show(context, "",
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
                Log.e("msg"," res  json  "+AssignJobList);
                Log.e(">>>>>>>>>>",""+AssignJobList);
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
//                imgRefresh.setVisibility(View.VISIBLE);

                try {
                    tvJob.setText("My Jobs : "+String.valueOf(listJobNo.size()));
//                    badge1.setText(String.valueOf(listJobNo.size()));
                } catch (Exception e1) {
//                    badge1.setText("0");
                    tvJob.setText("My Jobs : 0");
                }


                try {
                    tvBid.setText("I-Bidding : "+String.valueOf(listBidNo.size()));
//                    badge2.setText(String.valueOf(listBidNo.size()));
                } catch (Exception e1) {
//                    badge2.setText("0");
                    tvBid.setText("I-Bidding : 0");
                }

            }else{
                try {
                    if(listJobNo.size()==0||listBidNo.size()==0){
                        //badge1.setVisibility(View.GONE);
                        //badge2.setVisibility(View.GONE);
                        boolean check = false;
//                        badge1.setVisibility(View.VISIBLE);
//                        badge2.setVisibility(View.VISIBLE);

                        if(listJobNo.size()==0){
//                            badge1.setText("0");
                            tvJob.setText("My Jobs : 0");
                            check=true;

//                            imgRefresh.setVisibility(View.VISIBLE);
                        }
                        else
//                            badge1.setText(String.valueOf(listJobNo.size()));
                            tvJob.setText("My Jobs : "+String.valueOf(listJobNo.size()));
                        if(listBidNo.size()==0)
                        {
//                            badge2.setText("0");
                            tvBid.setText("I-Bidding : 0");
                            check=true;

//                            imgRefresh.setVisibility(View.VISIBLE);
                        }
                        else
//                            badge2.setText(String.valueOf(listBidNo.size()));
                        tvBid.setText("I-Bidding : "+String.valueOf(listBidNo.size()));
                        //if(check)
                        //Util.showAlertCommonDialogWithOKBtn(context, "Error","Connection timeout error. Please check your internet connection");
                    }else{
//                        badge1.setVisibility(View.VISIBLE);
//                        badge2.setVisibility(View.VISIBLE);
//                        badge1.setText(String.valueOf(listJobNo.size()));
//                        badge2.setText(String.valueOf(listBidNo.size()));
                        tvJob.setText("My Jobs  : "+String.valueOf(listJobNo.size()));
                        tvBid.setText("I-Bidding : "+String.valueOf(listBidNo.size()));
                    }

//                    imgRefresh.setVisibility(View.GONE);

                } catch (Exception e) {
                    //Util.showAlertCommonDialogWithOKBtn(context, "Error","Connection timeout error. Please check your internet connection");
//                    imgRefresh.setVisibility(View.VISIBLE);

                    try {
//                        badge1.setText(String.valueOf(listJobNo.size()));
                        tvJob.setText("My Jobs : "+String.valueOf(listJobNo.size()));
                    } catch (Exception e1) {
//                        badge1.setText("0");
                        tvJob.setText("My Jobs : 0");
                    }


                    try {
//                        badge2.setText(String.valueOf(listBidNo.size()));
                        tvBid.setText("I-Bidding : "+String.valueOf(listBidNo.size()));
                    } catch (Exception e1) {
//                        badge2.setText("0");
                        tvBid.setText("I-Bidding : 0");
                    }
                }
            }
        }
    }

    private class getAllMessageAsyncTask extends AsyncTask<Void, Void, String> {
        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected String doInBackground(Void... voids) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("Dtfrom", "");
                parameter.put("Dtto", "");
                parameter.put("Username",  getActivity().getSharedPreferences(Util.SHARED_PREFERENCES_KEY, getActivity().MODE_PRIVATE).getString(Util.LOGIN_KEY, ""));
                parameter.put("PageSize", "0");
                parameter.put("PagesPerSet", "0");
                parameter.put("PageNo", "0");

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetDriverAllMessages, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetDriverAllMessages, Constants.APIDotNetMethodNameGetDriverAllMessages, parameter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mProgressDialog.hide();

            if (result != null) {
                try {
                    List<iMessage> listTemp  = Parser.getJSONDriverAllMessages(result);
                    int msgCount = 0;
                    if(listTemp.size() > 0) {
                        Constants.AllMessages = listTemp;
                        for(iMessage i : listTemp){
                            if(i.getIsRead().equals("0"))
                                msgCount++;
                        }

                        Constants.unreadCount = msgCount;

                        btExit.setText("INBOX : " + msgCount);
                    }else{
                        Constants.AllMessages.clear();
                        btExit.setText("INBOX : 0");
                    }

                } catch (Exception e) {
                    Util.modifiedLogTrace(e.getStackTrace().toString());
                }
            }
        }
    }

    private void showProgressDialog() {
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        mProgressDialog.show();
    }
}
