package com.info121.ioperations.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.ioperations.R;
import com.info121.ioperations.fragment.DetailsClaimListing;
import com.info121.model.ListingModel;

import java.util.ArrayList;

/**
 * Created by archirayan on 21/10/16.
 */
public class ListingAdapter extends BaseAdapter {

    Context context;
    ArrayList<ListingModel> listingModelsArray;
    LayoutInflater inflater;
    FragmentManager supportFragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    public ListingAdapter(Context context, ArrayList<ListingModel> listingModelsArray, FragmentManager supportFragmentManager) {
        this.context = context;
        this.supportFragmentManager = supportFragmentManager;
        this.listingModelsArray = listingModelsArray;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listingModelsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return listingModelsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {

            view = inflater.inflate(R.layout.listing_adapter, null);
        }


        final ListingModel listingModel = listingModelsArray.get(position);

        TextView tvClaimNo = (TextView) view.findViewById(R.id.tv_claimno_listing_adapter);
        TextView tvClaimDate = (TextView) view.findViewById(R.id.tv_claimdate_listing_adapter);
        TextView tvClaimStatus = (TextView) view.findViewById(R.id.tv_claimstatus__listing_adapter);
        TextView tvClaimAmt = (TextView) view.findViewById(R.id.tvClaimAmt_listing_adapter);
        TextView tvClaimTotal = (TextView) view.findViewById(R.id.tvClaimTotal_listing_adapter);
        TextView tvJobCount = (TextView) view.findViewById(R.id.tvJobCount_listing_adapter);
        LinearLayout layoutroot = (LinearLayout) view.findViewById(R.id.rootlayout);
        GradientDrawable gradientDrawable = (GradientDrawable) layoutroot.getBackground();
        gradientDrawable.setColor(context.getResources().getColor(R.color.COLOR_GREEN_ITEM));

        layoutroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailsClaimListing detailsClaimListing = new DetailsClaimListing();
                Bundle bundle = new Bundle();
                bundle.putString("claimno", listingModel.getClaimNo());
                detailsClaimListing.setArguments(bundle);
                fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, detailsClaimListing);
                fragmentTransaction.commit();


            }
        });


        tvClaimNo.setText(listingModel.getClaimNo() + " ");
        tvClaimAmt.setText(listingModel.getTotalAddClaimsAmt());
        tvClaimDate.setText(listingModel.getClaimDate());
        tvClaimStatus.setText(listingModel.getStatus());
        tvClaimTotal.setText(listingModel.getClaimTotal());
        tvJobCount.setText(listingModel.getTotalJob());


        return view;
    }


}
