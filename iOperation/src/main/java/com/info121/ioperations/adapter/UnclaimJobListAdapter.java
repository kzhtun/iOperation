package com.info121.ioperations.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.ioperations.R;
import com.info121.ioperations.fragment.BillingFragment;
import com.info121.model.UnclaimJobListModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by archirayan on 7/9/16.
 */
public class UnclaimJobListAdapter extends BaseAdapter {


    public TreeSet<String> jobNoArray;
    public ArrayList<String> jobNoArrayShare;
    Context context;
    ArrayList<UnclaimJobListModel> unclaimJobListModels;
    ArrayList<Boolean> isCheckedArray;
    SharedPreferences sp;


    public UnclaimJobListAdapter(Context context, ArrayList<UnclaimJobListModel> unclaimJobListModels) {

        this.context = context;
        this.unclaimJobListModels = unclaimJobListModels;


        sp = context.getSharedPreferences("Jobarray", Context.MODE_PRIVATE);
        isCheckedArray = new ArrayList<>();
        jobNoArray = new TreeSet<>();
        jobNoArrayShare = new ArrayList<>();


        for (int i = 0; i < unclaimJobListModels.size(); i++) {
            isCheckedArray.add(false);
            //  jobNoArray.add("");
        }


    }


    @Override
    public int getCount() {
        return unclaimJobListModels.size();
    }

    @Override
    public Object getItem(int position) {
        return unclaimJobListModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.unclaims_joblist_adapter, null);


        TextView tvDate = (TextView) view.findViewById(R.id.tv_date_unclaims_joblist_adapter);
        TextView tvPax = (TextView) view.findViewById(R.id.tv_pax_unclaims_joblist_adapter);
        TextView tvAmt = (TextView) view.findViewById(R.id.tv_amt_unclaims_joblist_adapter);
        TextView tvUnclaimJobs = (TextView) view.findViewById(R.id.tvunclaims_job_unclaims_joblist_adapter);
        TextView tvopsType = (TextView) view.findViewById(R.id.tv_opstype_unclaims_joblist_adapter);
        TextView tvStatusShow = (TextView) view.findViewById(R.id.tv_status_fragment_billing);

        LinearLayout linearUnclaimJob = (LinearLayout) view.findViewById(R.id.linear_unclaims_joblist);

        TextView tvShowRe = (TextView) view.findViewById(R.id.tvshowte_unclaims_joblist_adapter);

        final UnclaimJobListModel unclaimJobListModel = unclaimJobListModels.get(position);
        String claimAmount = unclaimJobListModel.getClaimAmount();
        String jobAmt = unclaimJobListModel.getJobAmt();
        String jobStatus = unclaimJobListModel.getJobstatus();

        tvStatusShow.setText(unclaimJobListModel.getJobstatus());

        final CheckBox chkJobNO = (CheckBox) view.findViewById(R.id.ck_job_unclaims_joblist_adapter);
        chkJobNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedArray.set(position, isChecked);


                if (isChecked) {

                    if (jobNoArrayShare.contains(unclaimJobListModel.getJobNo())) {

                    } else {

                        Log.d("msg", "unclaimJobListModelsSelectedArray adapter " + BillingFragment.unclaimJobListModelsSelectedArrayz.size());

                        jobNoArrayShare.add(unclaimJobListModel.getJobNo());
                        BillingFragment.unclaimJobListModelsSelectedArrayz.add(unclaimJobListModels.get(position));

                    }


                } else {

                    if (jobNoArrayShare.contains(unclaimJobListModel.getJobNo())) {
                        jobNoArrayShare.remove(unclaimJobListModel.getJobNo());

                        Log.d("msg", "unclaimJobListModelsSelectedArray adapter " + BillingFragment.unclaimJobListModelsSelectedArrayz.size());


                    }

                    if (BillingFragment.unclaimJobListModelsSelectedArrayz.contains(unclaimJobListModels.get(position))) {
                        BillingFragment.unclaimJobListModelsSelectedArrayz.remove(unclaimJobListModels.get(position));

                    }

                }


                try {


                    String data = sp.getString("data", "");
                    Log.d("test", "data  " + data);


                    if (!data.equalsIgnoreCase("")) {

                        List<String> myList = new ArrayList<String>(Arrays.asList(data.split(",")));

                        Log.d("test", "mylist  " + myList.get(1));

                        if (myList.contains(unclaimJobListModel.getJobNo())) {
                            chkJobNO.setChecked(true);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

/*
                try {



                    if (isChecked) {

                        jobNoArray.add(unclaimJobListModel.getJobNo());

                    } else {

                        jobNoArray.remove(position);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context," "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
                */

            }
        });

        try {

            chkJobNO.setChecked(isCheckedArray.get(position));

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg", "error  " + e.getMessage());
        }
        linearUnclaimJob.setBackgroundResource(R.drawable.rounded_itemview);

        GradientDrawable drawable = (GradientDrawable) linearUnclaimJob.getBackground();

        switch (jobStatus) {
            case "Show":

                drawable.setColor(context.getResources().getColor(R.color.COLOR_GREEN));


                break;
            case "No Show":
                //  linearUnclaimJob.setBackgroundColor(context.getResources().getColor(R.color.ORANGE));
                drawable.setColor(context.getResources().getColor(R.color.COLOR_DARK_BLUE));


                break;

            case "Unhandled":
                // linearUnclaimJob.setBackgroundColor(context.getResources().getColor(R.color.COLOR_DARK_BLUE));
                drawable.setColor(context.getResources().getColor(R.color.ORANGE));


                break;

        }


        // Log.d("msg", "joba d" + claimAmount + "   dd " + jobAmt);

        float claimAmountInt = Float.parseFloat(claimAmount);
        float jobAmtInt = Float.parseFloat(jobAmt);
        Float total = claimAmountInt + jobAmtInt;

        //    Log.d("msg", "jobtotal adap in" + jobTotalValue.size());

        //   Log.d("msg", "joba" + claimAmountInt + "   d " + jobAmtInt + " too " + total);

        tvDate.setText(unclaimJobListModel.getOpsdate());
        tvPax.setText(unclaimJobListModel.getPax());
        tvShowRe.setText("Re :" + unclaimJobListModel.getRemarks());
        tvopsType.setText(unclaimJobListModel.getOpstype());
        tvUnclaimJobs.setText(unclaimJobListModel.getJobNo());
        tvAmt.setText(String.format("$" + new DecimalFormat("#0.00").format(claimAmountInt) + " +  $" + new DecimalFormat("#0.00").format(jobAmtInt) + " = $" + new DecimalFormat("#0.00").format(total)));


        return view;

    }

    public TreeSet<String> getJobNoArray() {

        return jobNoArray;

    }


    public void storeSharePre() {

        Log.d("msg", "string buffer  aray " + jobNoArrayShare);

        String jobPre = sp.getString("data", "");

        StringBuffer stringBuffer = new StringBuffer(jobPre);


        for (int i = 0; i < jobNoArrayShare.size(); i++) {

            String jobNo = jobNoArrayShare.get(i);
            stringBuffer.append(jobNo + ",");


        }

        sp.edit().putString("data", stringBuffer.toString()).commit();

        Log.d("msg", "string buffer str " + stringBuffer.toString());

    }


    public ArrayList<UnclaimJobListModel> getUnclaimSelectedJValue() {
        return BillingFragment.unclaimJobListModelsSelectedArrayz;
    }




}