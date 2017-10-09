package com.info121.ioperations.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperations.MainActivity;
import com.info121.ioperations.R;
import com.info121.ioperations.util.Constant;
import com.info121.ioperations.util.Parser;
import com.info121.ioperations.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by archirayan1 on 4/22/2016.
 */
public class IbiddingPagerAdapter extends PagerAdapter{

    private TextView tvDateTime, tvCustomer, tvAdult, tvChild, tvPicup, tvAlight, tvHotel, tvFlight, tvType, tvAmount, tvContact, tvPhone, tvDesc, tvRemark, tvTPTnote;
    private Button btAccept;
    private Button btReject;
    private Button btListing;
    private Button btHome;
    private TextView tvPageNo;

    private LinearLayout LLLoading;

    private Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> recordList;
    String bidNo;
    public IbiddingPagerAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
        this.context = mContext;
        this.recordList = list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public Object instantiateItem(View container, final int position){
        recordList.get(position);

        View view = inflater.inflate(R.layout.ibidding_details_pager_item, null);
        tvPageNo = (TextView) view.findViewById(R.id.tvPageNo_WelcomeIbidding);
        tvDateTime = (TextView) view.findViewById(R.id.tvDateTime_WelcomeIbidding);
        tvCustomer = (TextView) view.findViewById(R.id.tvCustomer_WelcomeIbidding);
        tvAdult = (TextView) view.findViewById(R.id.tvAdult_WelcomeIbidding);
        tvChild = (TextView) view.findViewById(R.id.tvChild_WelcomeIbidding);

        tvPicup = (TextView) view.findViewById(R.id.tvPickup_WelcomeIbidding);
        tvAlight = (TextView) view.findViewById(R.id.tvAlight_WelcomeIbidding);
        tvHotel = (TextView) view.findViewById(R.id.tvHotel_WelcomeIbidding);
        tvFlight = (TextView) view.findViewById(R.id.tvFlight_WelcomeIbidding);

        tvType = (TextView) view.findViewById(R.id.tvType_WelcomeIbidding);
        tvAmount = (TextView) view.findViewById(R.id.tvAmount_WelcomeIbidding);
        tvContact = (TextView) view.findViewById(R.id.tvContact_WelcomeIbidding);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone_WelcomeIbidding);

        tvDesc = (TextView) view.findViewById(R.id.tvDesc_WelcomeIbidding);
        tvRemark = (TextView) view.findViewById(R.id.tvRemarks_WelcomeIbidding);
        tvTPTnote = (TextView) view.findViewById(R.id.tvtptNote_WelcomeIbidding);

        int totalSize = recordList.size();
        int pos = position+1;
        tvPageNo.setText(""+pos+"/"+totalSize);

        String date = recordList.get(position).get("assigndate");
        String time = recordList.get(position).get("pickuptime");
        String dateTime = date+"  "+time ;
        tvDateTime.setText(dateTime);
        tvCustomer.setText(recordList.get(position).get("agent"));
        tvAdult.setText(recordList.get(position).get("noofadult"));
        tvChild.setText(recordList.get(position).get("noofchild"));
        tvPicup.setText(recordList.get(position).get("pickuptime"));
        tvAlight.setText(recordList.get(position).get("alight"));
        Log.e("alight", "" + recordList.get(position).get("alight"));
        tvHotel.setText(recordList.get(position).get("hotel"));
        tvFlight.setText(recordList.get(position).get("flightno"));
        tvType.setText(recordList.get(position).get("type"));
        tvAmount.setText(recordList.get(position).get("price"));
        Log.e("price", "" + recordList.get(position).get("price"));
        tvContact.setText(recordList.get(position).get("client"));
        tvPhone.setText(recordList.get(position).get("phone"));
        Log.e("phone", "" + recordList.get(position).get("phone"));
        tvDesc.setText(recordList.get(position).get("description"));
        Log.e("description", "" + recordList.get(position).get("description"));
        tvRemark.setText(recordList.get(position).get("BookingRemarks"));
        tvTPTnote.setText(recordList.get(position).get("TPTremarks"));

        btListing = (Button) view.findViewById(R.id.btListing_WelcomeIbidding);
        btHome = (Button) view.findViewById(R.id.btHome_WelcomeIbidding);

//        btReassing = (Button) view.findViewById(R.id.btReassign_WelcomeIbidding);
        btAccept = (Button) view.findViewById(R.id.btAccept_WelcomeIbidding);
        btReject = (Button) view.findViewById(R.id.btReject_WelcomeIbidding);


//        btnWelcome = (Button) view.findViewById(R.id.buttonWelcome);
//        btnNoSHow = (Button) view.findViewById(R.id.buttonNoSHow);
        LLLoading = (LinearLayout) view.findViewById(R.id.LLRootLoading_WelcomeIbidding);


        btListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"ibiding");
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



        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.isNetworkAvailable(context)) {
                    Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"ibiding");
                    bidNo = recordList.get(position).get("bidno");
                    new updateBidAcceptedAsyncTask().execute();
                } else {
                    Toast.makeText(context, "No internet connection found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.isNetworkAvailable(context)) {
                    Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"ibiding");
                    bidNo = recordList.get(position).get("bidno");
                    new updateBidRejectedAsyncTask().execute();
                } else {
                    Toast.makeText(context, "No internet connection found", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        btnWelcome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ShowClientActivity.class);
//                intent.putExtra(Util.JOBNO_KEY, recordList.get(position).get("BookingRemarks"));
//                intent.putExtra(Util.PASSENGER_KEY, recordList.get(position).get("client").trim());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
//        btnNoSHow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, NoShowClientActivity.class);
//                intent.putExtra(Util.JOBNO_KEY, recordList.get(position).get("jobno"));
//                intent.putExtra(Util.PASSENGER_KEY,  recordList.get(position).get("client").trim());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        
        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    protected void showLoading() {
        LLLoading.setVisibility(View.VISIBLE);
    }

    protected void dismissLoading() {
        LLLoading.setVisibility(View.GONE);
    }


    private class updateBidAcceptedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("BidNo", bidNo);
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateBidAcceptedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidAcceptedStatus, Constants.APIDotNetMethodNameUpdateBidAcceptedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + iBiddingAdapter.items.get(iBiddingAdapter.selectedItem).getJobNo() + " Accepted Succesfully");
//                    items = new ArrayList<iBid_Model>();
//                    search = new ArrayList<iBid_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Bid #" + bidNo + " Accepted Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//        								    getActivity().finish();
                                        Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"ibiding");
                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.putExtra(Constants.intentKey_iOp, 2);
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

    private class updateBidRejectedAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            showLoading();

        }

        @Override
        protected String doInBackground(Void... v) {
            Hashtable<String, String> parameter = null;
            try {
                parameter = new Hashtable<String, String>();
                parameter.put("BidNo", bidNo);
            } catch (Exception e) {
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }
            return Util.connectSOAP(Constants.uRLiOpsUpdateBidRejectedStatus, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateBidRejectedStatus, Constants.APIDotNetMethodNameUpdateBidRejectedStatus, parameter);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoading();
            if (result != null) {
                try {

                    String temp = Parser.getJSONAcceptRejectResult(result);
                    //Util.showAlertCommonDialogWithOKBtn(context, "Success","Job #" + iBiddingAdapter.items.get(iBiddingAdapter.selectedItem).getJobNo() + " Rejected Succesfully");


//                    items = new ArrayList<iBid_Model>();
//                    search = new ArrayList<iBid_Model>();


                    try {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Bid #" + bidNo + " Rejected Succesfully");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//        								    getActivity().finish();
                                        Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_IS_IBIDING_OR_IOPERATION,"ibiding");
                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.putExtra(Constants.intentKey_iOp, 2);
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