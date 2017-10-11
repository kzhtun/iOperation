package com.info121.ioperation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.ioperation.R;
import com.info121.model.DetailsListingModel;

import java.util.ArrayList;

/**
 * Created by archirayan on 26/10/16.
 */
public class DetailListingAdapter extends RecyclerView.Adapter<DetailListingAdapter.MyviewHolder> {


    public Context context;
    public ArrayList<DetailsListingModel> detailsListingModels;
    public ArrayList<String> claimAmtUpdateArray;


    public DetailListingAdapter(Context context, ArrayList<DetailsListingModel> detailsListingModels) {
        this.context = context;
        this.detailsListingModels = detailsListingModels;
        claimAmtUpdateArray = new ArrayList<>();

        for (int i = 0; i < detailsListingModels.size(); i++) {
            claimAmtUpdateArray.add(detailsListingModels.get(i).getAddClaimAmount());
        }


    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_listing_adapter, parent, false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, final int position) {
        DetailsListingModel detailsListingModel = detailsListingModels.get(position);
        holder.tvDate.setText(detailsListingModel.getJobDate());
        holder.tvJobNo.setText(detailsListingModel.getJobNo());
        holder.tvJobAmt.setText(detailsListingModel.getJobAmount());
        holder.tvClaimAmt.setText(detailsListingModel.getAddClaimAmount());

        // KZHTUN 09102017 fix detail activity crash issue
        //  holder.tvStatus.setText("Status");// + detailsListingModel.getJobStatus());
        holder.tvOpsType.setText(detailsListingModel.getOpsType());
        holder.tvPax.setText(detailsListingModel.getPax() + " Pax");


        holder.tvClaimAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                claimAmtUpdateArray.set(position, s.toString());
            }
        });

        String status = detailsListingModel.getJobStatus();
        if (status.equalsIgnoreCase("A")) {

            GradientDrawable bgShape = (GradientDrawable) holder.linearMain.getBackground();
            bgShape.setColor(Color.GRAY);

            holder.tvClaimAmt.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return detailsListingModels.size();
    }

    public ArrayList<String> getClaimEditAmtArrayMethod() {
        return claimAmtUpdateArray;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate, tvJobNo, tvJobAmt, tvStatus, tvOpsType, tvPax;
        public EditText tvClaimAmt;
        public LinearLayout linearMain;


        public MyviewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date_listing_details);
            tvJobNo = (TextView) itemView.findViewById(R.id.tv_jobno_listin_reviewadapter);
            tvJobAmt = (TextView) itemView.findViewById(R.id.tv_jobamt_listing_adapter);
            tvClaimAmt = (EditText) itemView.findViewById(R.id.tv_claimsamt_listing_adapter);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_pax_listing_status_adapter);

            tvOpsType = (TextView) itemView.findViewById(R.id.tv_opstype_listing_adapter);
            tvPax = (TextView) itemView.findViewById(R.id.tv_pax_listing_adapter);
            linearMain = (LinearLayout) itemView.findViewById(R.id.lineardetails);
        }
    }

}
