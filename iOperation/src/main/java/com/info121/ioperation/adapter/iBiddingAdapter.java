package com.info121.ioperation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.info121.ioperation.R;
import com.info121.ioperation.WelcomeIbidingActivity;
import com.info121.ioperation.util.Constant;
import com.info121.ioperation.util.Util;
import com.info121.model.RowIsSelected;
import com.info121.model.iBid_Model;

import java.util.ArrayList;

public class iBiddingAdapter extends BaseAdapter {
    public static int selectedItem = -1;
    private final Context context;
    public ArrayList<iBid_Model> items;
    public ArrayList<RowIsSelected> ris;
    TextView tvDate, tvTime, tvHotel, tvPicupTime, tvPax, tvFlight, tvType;
    private FragmentManager fm;
    private CheckBox cbSelect;

    public iBiddingAdapter(Context context, ArrayList<iBid_Model> values, FragmentManager supportFragmentManager) {
        this.fm = supportFragmentManager;
        this.context = context;
        this.items = values;

        resetCheck();
    }

    public void resetCheck() {
        ris = new ArrayList<RowIsSelected>();
        for (int i = 0; i < items.size(); i++) {
            RowIsSelected temp = new RowIsSelected();
            temp.setSelected(false);
            ris.add(temp);
        }
    }

    public ArrayList<RowIsSelected> getRis() {
        return ris;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.child_listview_incomming_fragment, parent, false);

        try {


            tvDate = (TextView) rowView.findViewById(R.id.tvDate_iBiddingFragment);
            tvTime = (TextView) rowView.findViewById(R.id.tvTime_iBiddingFragment);
            tvHotel = (TextView) rowView.findViewById(R.id.tvHotel_iBiddingFragment);
            tvPicupTime = (TextView) rowView.findViewById(R.id.tvPicupTime_iBiddingFragment);
            tvPax = (TextView) rowView.findViewById(R.id.tvPax_iBiddingFragment);
            tvFlight = (TextView) rowView.findViewById(R.id.tvFlight_iBiddingFragment);
            tvType = (TextView) rowView.findViewById(R.id.tvType_iBiddingFragment);

            tvDate.setText(items.get(position).getDate());
            tvTime.setText(items.get(position).getFlightTime());
            tvHotel.setText(items.get(position).getHotel());
            tvPicupTime.setText(items.get(position).getPickupTime());
            String a = items.get(position).getA();
            String c = items.get(position).getC();
            String i = items.get(position).getI();

            Integer total = Integer.parseInt(a) + Integer.parseInt(c) + Integer.parseInt(i);
            tvPax.setText(total + " Pax");
            tvType.setText(items.get(position).getType());
            tvFlight.setText(items.get(position).getFlight());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.WriteSharePrefrence(context, Constant.SHRED_PR.KEY_BIDNO, items.get(position).getBidNo());
                    Intent intent = new Intent(context, WelcomeIbidingActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.d("msg", e.getMessage());
        }


        return rowView;


    }


    private boolean checkModItemNo(int x, int y) {
        int result = x % y;
        return result > 0 ? true : false;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * Use the array index as a unique id.
     */
    public long getItemId(int position) {
        return position;
    }

}