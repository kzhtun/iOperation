package com.info121.ioperation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.info121.ioperation.R;
import com.info121.model.UnclaimJobListModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by archirayan on 10/10/16.
 */
public class ReviewClaimAdapter extends RecyclerView.Adapter<ReviewClaimAdapter.MyViewHolder> {

    Context context;
    ArrayList<UnclaimJobListModel> unclaimJobListModels;
    ArrayList<String> claimEditAmtArray;
    ArrayList<String> claimEditRemarkArray;

    public ReviewClaimAdapter(Context context, ArrayList<UnclaimJobListModel> unclaimJobListModels) {

        this.unclaimJobListModels = unclaimJobListModels;
        this.context = context;
        claimEditAmtArray = new ArrayList<>();
        claimEditRemarkArray = new ArrayList<>();

        for (int i = 0; i < unclaimJobListModels.size(); i++) {
            claimEditAmtArray.add(unclaimJobListModels.get(i).getClaimAmount());
            claimEditRemarkArray.add(unclaimJobListModels.get(i).getRemarks());
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_claims_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


      /*  if(position == 0)
        {
            holder.lvTitleDetails.setVisibility(View.VISIBLE);
        }
        else {
            holder.lvTitleDetails.setVisibility(View.GONE);
        }*/


        final UnclaimJobListModel unclaimJobListModel = unclaimJobListModels.get(position);
        String date = unclaimJobListModel.getOpsdate();

        holder.tvDate.setText(date.substring(0, 10));
        holder.tvJobNO.setText(unclaimJobListModel.getJobNo());
        holder.tvOpts.setText(unclaimJobListModel.getOpstype());

        holder.tvPax.setText(unclaimJobListModel.getPax() + " PAX");
        holder.tvJobAmt.setText(new DecimalFormat("#0.00").format(Float.parseFloat(unclaimJobListModel.getJobAmt())));

        holder.tvClaimsAmt.setText(new DecimalFormat("#0.00").format(Float.parseFloat(unclaimJobListModel.getClaimAmount())));

        holder.tvClaimsAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                unclaimJobListModel.setClaimAmount(s.toString());
                claimEditAmtArray.set(position, s.toString());

            }
        });

        holder.tvRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                unclaimJobListModel.setRemarks(s.toString());
                claimEditRemarkArray.set(position, s.toString());
            }
        });


    }

    @Override
    public int getItemCount() {

        return unclaimJobListModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate, tvJobNO, tvOpts, tvPax, tvJobAmt;
        public EditText tvClaimsAmt;
        public EditText tvRemark;
        //  public LinearLayout lvTitleDetails;

        public MyViewHolder(View view) {
            super(view);


            tvDate = (TextView) view.findViewById(R.id.tv_date_reviewadapter);
            tvJobNO = (TextView) view.findViewById(R.id.tv_jobno__reviewadapter);
            tvOpts = (TextView) view.findViewById(R.id.tv_opstype_review_adapter);
            tvPax = (TextView) view.findViewById(R.id.tv_pax_review_adapter);
            tvJobAmt = (TextView) view.findViewById(R.id.tv_jobamt_review_adapter);
            tvClaimsAmt = (EditText) view.findViewById(R.id.tv_claimsamt_review_adapter);
            tvRemark = (EditText) view.findViewById(R.id.tv_remarks);

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(tvClaimsAmt, InputMethodManager.SHOW_IMPLICIT);

            //    lvTitleDetails= (LinearLayout) view.findViewById(R.id.title_details_log);

        }
    }


    public ArrayList<String> getClaimEditAmtArrayMethod() {
        return claimEditAmtArray;
    }

    public ArrayList<String> getClaimEditRemarkArrayMethod() {
        return claimEditRemarkArray;
    }
}
