package com.info121.ioperation.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperation.R;
import com.info121.ioperation.SurveyActivity;
import com.info121.ioperation.util.Util;
import com.info121.model.Job;
import com.info121.model.iMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by KZHTUN on 9/26/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    Context mContext;
    List<Job> jobList;


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
    public void onBindViewHolder(ViewHolder holder, int i) {

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
                mContext.startActivity(new Intent(mContext, SurveyActivity.class));
            }
        });

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

