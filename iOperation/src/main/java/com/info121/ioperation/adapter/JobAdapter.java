package com.info121.ioperation.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperation.MainActivity;
import com.info121.ioperation.R;
import com.info121.ioperation.SurveyActivity;
import com.info121.ioperation.fragment.HomeFragment;
import com.info121.ioperation.util.Parser;
import com.info121.ioperation.util.Util;
import com.info121.model.Job;
import com.info121.model.iMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by KZHTUN on 9/26/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    Context mContext;
    List<Job> jobList;


    String emailText;
    String jobNoText;


    public JobAdapter(Context mContext, List<Job> jobList) {
        this.mContext = mContext;
        this.jobList = jobList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.child_listview_job, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {


        holder.mJobNo.setText("Job No : " + jobList.get(i).getJobNo());
        holder.mClientName.setText("Guest : " + jobList.get(i).getClientname());
        holder.mPax.setText("Pax : " + jobList.get(i).getPax());
        holder.mJobType.setText("Job Type : " + jobList.get(i).getJobtype());
        holder.mJobDate.setText("Job Date : " + jobList.get(i).getJobDate());
        holder.mPickupTime.setText("Pickup Time : " + jobList.get(i).getPickupTime());


        switch (jobList.get(i).getJobtype().toUpperCase()) {
            case "ARR":
                holder.mRowLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_itemview_arr));
                break;
            case "CTR":
                holder.mRowLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_itemview_ctr));
                break;
            case "DEP":
                holder.mRowLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_itemview_dep));
                break;
        }


        holder.mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str[] = holder.mJobNo.getText().toString().split(":");

                jobNoText = str[1].trim();

                // custom dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_rating, null);
                dialogBuilder.setView(dialogView);

                final EditText mEmail = (EditText) dialogView.findViewById(R.id.email);
                final Button mSubmit = (Button) dialogView.findViewById(R.id.submit);

                final AlertDialog alertDialog = dialogBuilder.create();

                mSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        emailText = mEmail.getText().toString();

                        if (emailText.length() == 0) {
                            mEmail.setError("Email should not be left blank.");
                            mEmail.requestFocus();
                        } else {
                            if (isValidEmaillId(emailText)) {
                                alertDialog.dismiss();
                                new setIopsSurveyInfo().execute();
                            } else {
                                mEmail.setError("Please enter valid email address.");
                                mEmail.requestFocus();
                            }
                        }
                    }
                });

                alertDialog.show();

            }
        });

    }


    private class setIopsSurveyInfo extends AsyncTask<Void, Void, String> {

        boolean isTimeout = false;
        boolean isMainPerson = false;

        @Override
        protected String doInBackground(Void... voids) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("Jobno", jobNoText);
                parameter.put("Email", emailText);

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsSetSurveyInfo, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsSetSurveyInfo, Constants.APIDotNetMethodNameSetSurveyInfo, parameter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject rootObj = new JSONObject(result);
                    Boolean success = Boolean.valueOf(rootObj.getJSONArray("result").getJSONObject(0).getString("isSuccess"));
                    //mJobList  = Parser.getJSONDriverAllJobs(result);

                    if (success) {
                        HomeFragment bidingFragment = new HomeFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, bidingFragment);
                        fragmentTransaction.commit();

                        mContext.startActivity(new Intent(mContext, SurveyActivity.class));
                    } else {
                        Toast.makeText(mContext, "Oops data saving error. Please try again.", Toast.LENGTH_SHORT);
                    }

                } catch (Exception e) {
                    Util.modifiedLogTrace(e.getStackTrace().toString());
                }
            }
        }
    }


    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mJobNo;
        TextView mClientName;
        TextView mPax;
        TextView mJobType;
        TextView mJobDate;
        TextView mPickupTime;
        Button mStart;
        LinearLayout mRowLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            mJobNo = (TextView) itemView.findViewById(R.id.job_no);
            mClientName = (TextView) itemView.findViewById(R.id.client_name);
            mPax = (TextView) itemView.findViewById(R.id.pax);
            mJobType = (TextView) itemView.findViewById(R.id.job_type);
            mJobDate = (TextView) itemView.findViewById(R.id.job_date);
            mPickupTime = (TextView) itemView.findViewById(R.id.pickup_time);
            mStart = (Button) itemView.findViewById(R.id.survey);
            mRowLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);

        }
    }


}

