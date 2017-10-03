package com.info121.ioperation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.ioperation.R;
import com.info121.ioperation.WelcomeIoperationActivity;
import com.info121.ioperation.fragment.iOperationFragment;
import com.info121.ioperation.util.Util;
import com.info121.model.RowIsSelected;
import com.info121.model.iOP_Model;

import java.util.ArrayList;

public class iOperationAdapter extends BaseAdapter {
    private final Context context;
    public static ArrayList<iOP_Model> items;
    //	private LinearLayout LLBookNo,LLChild1,LLChild2,LLChild3,LLChild4,LLChild5,LLChild6,LLChild7,LLChild8,LLChild9,LLChild10;
//	private LinearLayout LLChild11,LLChild12,LLChild13,LLChild14,LLChild15,LLChild16,LLChild17,LLChild18,llHeader;
    TextView tvDate, tvTime, tvHotel, tvPicupTime, tvPax, tvFlight, tvType;
    private LinearLayout llHeader;
    public boolean isSelected = false;
    public int selectedIndex = 0;
    private Activity act;
    private CheckBox cbSelect;
    public static int selectedItem = -1;
    //private int pos;
    public ArrayList<RowIsSelected> ris;
    public boolean isAssigned;
    private Button btnReAssign;
    private Button btnServe;
    private iOperationFragment fragment;
    private boolean isMainContactPerson = true;
    private String MultipleJobNo;
    FragmentManager fm;

    public iOperationAdapter(Context context, ArrayList<iOP_Model> values, Activity act, boolean isAssigned, iOperationFragment fragment, FragmentManager fm) {

        this.context = context;
        this.items = values;
        this.act = act;
        this.isAssigned = isAssigned;
        this.fragment = fragment;
        this.fm = fm;

    }


    public iOperationAdapter(Context context, ArrayList<iOP_Model> values, Activity act, boolean isAssigned, iOperationFragment fragment, boolean isMainContactPerson, FragmentManager fm) {

        this.context = context;
        this.items = values;
        this.act = act;
        this.isAssigned = isAssigned;
        this.fragment = fragment;
        this.isMainContactPerson = isMainContactPerson;
        this.fm = fm;

    }


    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();

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
        //pos = position;
//		View rowView = inflater.inflate(R.layout.child_listview_ioperation, parent, false);
        View rowView = inflater.inflate(R.layout.child_listview_todo_fragment, parent, false);


        //Textview inti
        tvDate = (TextView) rowView.findViewById(R.id.tvDate_ToDoFragment);
        tvTime = (TextView) rowView.findViewById(R.id.tvTime_ToDoFragment);
        tvHotel = (TextView) rowView.findViewById(R.id.tvHotel_ToDoFragment);
        tvPicupTime = (TextView) rowView.findViewById(R.id.tvPicupTime_ToDoFragment);
        tvPax = (TextView) rowView.findViewById(R.id.tvPax_ToDoFragment);
        tvFlight = (TextView) rowView.findViewById(R.id.tvFlight_ToDoFragment);
        tvType = (TextView) rowView.findViewById(R.id.tvType_ToDoFragment);

        String date = items.get(position).getDate();

        tvDate.setText(date);
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

//		llHeader = (LinearLayout) rowView.findViewById(R.id.lliOperationHeader);
//		btnReAssign = (Button) rowView.findViewById(R.id.buttonReassign);
//		btnServe = (Button) rowView.findViewById(R.id.ButtonServe);
//		if(items.get(position).getStatus().compareToIgnoreCase("Job Assigned")!=0){
//			if(isMainContactPerson){
//				btnReAssign.setEnabled(true);
//				btnServe.setEnabled(false);
//			}else{
//				btnReAssign.setEnabled(false);
//				btnServe.setEnabled(false);
//			}
//		}
//		else
//		{
//			btnReAssign.setEnabled(false);
//			btnServe.setEnabled(true);
//		}

//		if(position != 0){
//			llHeader.setVisibility(View.GONE);
//		}

//		LLBookNo = (LinearLayout) rowView.findViewById(R.id.LLChildBookNo);
//		LLChild1 = (LinearLayout) rowView.findViewById(R.id.LLChild1);
//		LLChild2 = (LinearLayout) rowView.findViewById(R.id.LLChild2);
//		LLChild3 = (LinearLayout) rowView.findViewById(R.id.LLChild3);
//		LLChild4 = (LinearLayout) rowView.findViewById(R.id.LLChild4);
//		LLChild5 = (LinearLayout) rowView.findViewById(R.id.LLChild5);
//		LLChild6 = (LinearLayout) rowView.findViewById(R.id.LLChild6);
//		LLChild7 = (LinearLayout) rowView.findViewById(R.id.LLChild7);
//		LLChild8 = (LinearLayout) rowView.findViewById(R.id.LLChild8);
//		LLChild9 = (LinearLayout) rowView.findViewById(R.id.LLChild9);
//		LLChild10 = (LinearLayout) rowView.findViewById(R.id.LLChild10);
//		LLChild11 = (LinearLayout) rowView.findViewById(R.id.LLChild11);
//		LLChild12 = (LinearLayout) rowView.findViewById(R.id.LLChild12);
//		LLChild13 = (LinearLayout) rowView.findViewById(R.id.LLChild13);
//		LLChild14 = (LinearLayout) rowView.findViewById(R.id.LLChild14);
//		LLChild15 = (LinearLayout) rowView.findViewById(R.id.LLChild15);
//		LLChild16 = (LinearLayout) rowView.findViewById(R.id.LLChild16);
//		LLChild17 = (LinearLayout) rowView.findViewById(R.id.LLChild17);
//		LLChild18 = (LinearLayout) rowView.findViewById(R.id.LLChild18);
//
//		if(isSelected && selectedIndex==position){
//
//			LLBookNo.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild1.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild2.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild3.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild4.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild5.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild6.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild7.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild8.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild9.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild10.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild11.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild12.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild13.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild14.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild15.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild16.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild17.setBackgroundResource(R.drawable.bg_list_item_selected);
//			LLChild18.setBackgroundResource(R.drawable.bg_list_item_selected);
//
//		}else{
//
//
//					if(checkModItemNo(position,2)){
//
//						LLBookNo.setBackgroundResource(R.drawable.header_model7);
//						LLChild1.setBackgroundResource(R.drawable.header_model7);
//						LLChild2.setBackgroundResource(R.drawable.header_model7);
//						LLChild3.setBackgroundResource(R.drawable.header_model7);
//						LLChild4.setBackgroundResource(R.drawable.header_model7);
//						LLChild5.setBackgroundResource(R.drawable.header_model7);
//						LLChild6.setBackgroundResource(R.drawable.header_model7);
//						LLChild7.setBackgroundResource(R.drawable.header_model7);
//						LLChild8.setBackgroundResource(R.drawable.header_model7);
//						LLChild9.setBackgroundResource(R.drawable.header_model7);
//						LLChild10.setBackgroundResource(R.drawable.header_model7);
//						LLChild11.setBackgroundResource(R.drawable.header_model7);
//						LLChild12.setBackgroundResource(R.drawable.header_model7);
//						LLChild13.setBackgroundResource(R.drawable.header_model7);
//						LLChild14.setBackgroundResource(R.drawable.header_model7);
//						LLChild15.setBackgroundResource(R.drawable.header_model7);
//						LLChild16.setBackgroundResource(R.drawable.header_model7);
//						LLChild17.setBackgroundResource(R.drawable.header_model7);
//						LLChild18.setBackgroundResource(R.drawable.header_model7);
//
//
//
//					}else{
//
//						LLBookNo.setBackgroundResource(R.drawable.header_model8);
//						LLChild1.setBackgroundResource(R.drawable.header_model8);
//						LLChild2.setBackgroundResource(R.drawable.header_model8);
//						LLChild3.setBackgroundResource(R.drawable.header_model8);
//						LLChild4.setBackgroundResource(R.drawable.header_model8);
//						LLChild5.setBackgroundResource(R.drawable.header_model8);
//						LLChild6.setBackgroundResource(R.drawable.header_model8);
//						LLChild7.setBackgroundResource(R.drawable.header_model8);
//						LLChild8.setBackgroundResource(R.drawable.header_model8);
//						LLChild9.setBackgroundResource(R.drawable.header_model8);
//						LLChild10.setBackgroundResource(R.drawable.header_model8);
//						LLChild11.setBackgroundResource(R.drawable.header_model8);
//						LLChild12.setBackgroundResource(R.drawable.header_model8);
//						LLChild13.setBackgroundResource(R.drawable.header_model8);
//						LLChild14.setBackgroundResource(R.drawable.header_model8);
//						LLChild15.setBackgroundResource(R.drawable.header_model8);
//						LLChild16.setBackgroundResource(R.drawable.header_model8);
//						LLChild17.setBackgroundResource(R.drawable.header_model8);
//						LLChild18.setBackgroundResource(R.drawable.header_model8);
//
//
//
//					}
//
//		}

//		((TextView) rowView.findViewById(R.id.TextViewiOPBookNo)).setText(items.get(position).getBookNo());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild1)).setText(items.get(position).getJobNo());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild2)).setText(items.get(position).getDate());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild3)).setText(items.get(position).getType());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild4)).setText(items.get(position).getName());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild5)).setText(items.get(position).getA());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild6)).setText(items.get(position).getC());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild7)).setText(items.get(position).getI());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild8)).setText(items.get(position).getFlight());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild9)).setText(items.get(position).getFlightTime());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild10)).setText(items.get(position).getPickupTime());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild11)).setText(items.get(position).getHotel());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild12)).setText(items.get(position).getTPTRemarks());


//		String temp;
//		try {
//			temp = items.get(position).getBookingRemarks();
//			if(temp.length()>18){
//				temp = temp.substring(0, 14) + "... ";
//			}
//		} catch (Exception e1) {
//			temp = items.get(position).getBookingRemarks();
//		}

//		((TextView) rowView.findViewById(R.id.TextViewiOPChild13)).setText(temp);
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild14)).setText(items.get(position).getArrDep());
//		//((TextView) rowView.findViewById(R.id.TextViewiOPChild15)).setText(items.get(position).getAgent());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild16)).setText(items.get(position).getAgent());
//		((TextView) rowView.findViewById(R.id.TextViewiOPChild18)).setText(items.get(position).getStatus());


//		((Button) rowView.findViewById(R.id.buttonReassign)).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				//ReAssignDialog.show(context, act,Integer.parseInt(items.get(position).getJobNo()),fragment);
//
//			}
//		});

//		((Button) rowView.findViewById(R.id.ButtonServe)).setOnClickListener(new OnClickListener() {
//			   @Override
//			   public void onClick(View v) {
//
//				   Intent intent = new Intent(context, WelcomeIoperationActivity.class);
//				   intent.putExtra(Util.JOBNO_KEY, items.get(position).getJobNo());
//				   //intent.putExtra(Util.PASSENGER_KEY, items.get(position).getName());
//				   intent.putExtra(Util.PASSENGERNAME1_KEY, items.get(position).getWelcometext1());
//				   intent.putExtra(Util.PASSENGERNAME2_KEY, items.get(position).getWelcometext2());
//				   SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
//				   SharedPreferences.Editor editor = preferences.edit();
//				   editor.putBoolean(Util.EXIT_NOSHOW_KEY, false);
//				   editor.putBoolean(Util.EXIT_SHOW_KEY, false);
//				   editor.commit();
//				   context.startActivity(intent);
//
//			   }
//		});

//		cbSelect = (CheckBox) rowView.findViewById(R.id.checkButtoniOPSelect);
//		cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//				if (isChecked) {
//					Log.v("pos", String.valueOf(position));
//					ris.get(position).setSelected(!ris.get(position).isSelected());
//					showPopupWindow(context,position);
//					//selectedItem = position;
//					//cbSelect.setChecked(true);
//					//iOperationAdapter.this.notifyDataSetChanged();
//				}
//			}
//		});

//	try {
//		if(ris.get(position).isSelected())
//			cbSelect.setChecked(true);
//		else
//			cbSelect.setChecked(false);
//	} catch (Exception e) {


//	}
        iOP_Model iOP_model = items.get(position);
        final String jobno = iOP_model.getJobNo();


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WelcomeIoperationActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("searchResult", items);
                intent.putExtra("jobNOIntent", jobno);
                context.startActivity(intent);

            }
        });
        return rowView;
    }


    public void perform_click(int position) {


        Intent intent = new Intent(context, WelcomeIoperationActivity.class);
        intent.putExtra(Util.JOBNO_KEY, items.get(position).getJobNo());
        //intent.putExtra(Util.PASSENGER_KEY, items.get(position).getName());
        intent.putExtra(Util.PASSENGERNAME1_KEY, items.get(position).getWelcometext1());
        intent.putExtra(Util.PASSENGERNAME2_KEY, items.get(position).getWelcometext2());
        SharedPreferences preferences = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Util.EXIT_NOSHOW_KEY, false);
        editor.putBoolean(Util.EXIT_SHOW_KEY, false);
        editor.commit();
        context.startActivity(intent);

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