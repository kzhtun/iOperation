package com.info121.ioperations;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import com.info121.ioperations.adapter.IbiddingPagerAdapter;
import com.info121.ioperations.util.Constant;
import com.info121.ioperations.util.Util;
import com.viewpagerindicator.TitlePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 4/21/2016.
 */
public class WelcomeIbidingActivity extends Activity {
    private Context context;
    public static WelcomeIbidingActivity thisFragment;
    private int position;
    IbiddingPagerAdapter adapter;
    ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_ibidding);
        context = getApplicationContext();
        thisFragment = this;

//        inIt();


        list = new ArrayList<HashMap<String, String>>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");
            Log.e("JOSNLIST", "" + Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_JSONLIST));
            String result = Util.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_JSONLIST);
            JSONObject rootObj = null;
            JSONArray txtObj = null;
            try {
                rootObj = new JSONObject(result);
                if (rootObj.has("bidnewlist")) {
                    txtObj = rootObj.getJSONArray("bidnewlist");
                }
                if (rootObj.has("bidacceptlist")) {
                    txtObj = rootObj.getJSONArray("bidacceptlist");
                }
                if (rootObj.has("bidrejectlist")) {
                    txtObj = rootObj.getJSONArray("bidrejectlist");
                }
                if (rootObj.has("bidcloselist")) {
                    txtObj = rootObj.getJSONArray("bidcloselist");
                }

                for (int i = 0; i < txtObj.length(); i++) {
                    HashMap<String, String> hashmap = new HashMap<String, String>();
                    hashmap.put("jobno", txtObj.getJSONObject(i).getString("jobno"));
                    hashmap.put("noofadult", txtObj.getJSONObject(i).getString("noofadult"));
                    hashmap.put("agent", txtObj.getJSONObject(i).getString("agent"));
                    hashmap.put("BookingRemarks", txtObj.getJSONObject(i).getString("BookingRemarks"));
                    hashmap.put("noofchild", txtObj.getJSONObject(i).getString("noofchild"));
                    hashmap.put("assigndate", txtObj.getJSONObject(i).getString("assigndate"));
                    hashmap.put("flighttime", txtObj.getJSONObject(i).getString("flighttime"));
                    hashmap.put("flightno", txtObj.getJSONObject(i).getString("flightno"));
                    hashmap.put("hotel", txtObj.getJSONObject(i).getString("hotel"));
                    hashmap.put("client", txtObj.getJSONObject(i).getString("client"));
                    hashmap.put("pickuptime", txtObj.getJSONObject(i).getString("pickuptime"));

                    hashmap.put("price", txtObj.getJSONObject(i).getString("price"));
                    hashmap.put("alight", txtObj.getJSONObject(i).getString("alight"));
                    hashmap.put("phone", txtObj.getJSONObject(i).getString("phone"));
                    hashmap.put("description", txtObj.getJSONObject(i).getString("description"));


                    hashmap.put("TPTremarks", txtObj.getJSONObject(i).getString("TPTremarks"));
                    hashmap.put("type", txtObj.getJSONObject(i).getString("type"));
                    hashmap.put("bidno", txtObj.getJSONObject(i).getString("bidno"));

                    list.add(hashmap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Set the pager with an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        adapter = new IbiddingPagerAdapter(WelcomeIbidingActivity.this, list);
        pager.setAdapter(adapter);
        try {
            pager.setCurrentItem(position);
        } catch (NumberFormatException e) {
            Log.e("",""+e);
        }
//Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(pager);



    }

//    private void setData() {
//        tvDateTime.setText(date);
//        tvCustomer.setText(customer);
//        tvAdult.setText(adult);
//        tvChild.setText(child);
//        tvPicup.setText(pickup);
//        tvAlight.setText(alight);
//        tvHotel.setText(hotel);
//        tvFlight.setText(flight);
//        tvType.setText(type);
//
//        tvAmount.setText(amount);
//        tvContact.setText(customer);
//        tvPhone.setText(phone);
//        tvDesc.setText(description);
//        tvRemark.setText(remark);
//        tvTPTnote.setText(tptRemark);
//    }

//    private void inIt() {
//        tvDateTime = (TextView) findViewById(R.id.tvDateTime_WelcomeIbidding);
//        tvCustomer = (TextView) findViewById(R.id.tvCustomer_WelcomeIbidding);
//        tvAdult = (TextView) findViewById(R.id.tvAdult_WelcomeIbidding);
//        tvChild = (TextView) findViewById(R.id.tvChild_WelcomeIbidding);
//
//        tvPicup = (TextView) findViewById(R.id.tvPickup_WelcomeIbidding);
//        tvAlight = (TextView) findViewById(R.id.tvAlight_WelcomeIbidding);
//        tvHotel = (TextView) findViewById(R.id.tvHotel_WelcomeIbidding);
//        tvFlight = (TextView) findViewById(R.id.tvFlight_WelcomeIbidding);
//
//        tvType = (TextView) findViewById(R.id.tvType_WelcomeIbidding);
//        tvAmount = (TextView) findViewById(R.id.tvAmount_WelcomeIbidding);
//        tvContact = (TextView) findViewById(R.id.tvContact_WelcomeIbidding);
//        tvPhone = (TextView) findViewById(R.id.tvPhone_WelcomeIbidding);
//
//        tvDesc = (TextView) findViewById(R.id.tvDesc_WelcomeIbidding);
//        tvRemark = (TextView) findViewById(R.id.tvRemarks_WelcomeIbidding);
//        tvTPTnote = (TextView) findViewById(R.id.tvtptNote_WelcomeIbidding);
//
//
//        btReassing = (Button) findViewById(R.id.btReassign_WelcomeIbidding);
//        btReject = (Button) findViewById(R.id.btReject_WelcomeIbidding);
//
//        btnWelcome = (Button) findViewById(R.id.buttonWelcome);
//        btnNoSHow = (Button) findViewById(R.id.buttonNoSHow);
//        LLLoading = (LinearLayout) findViewById(R.id.LLRootLoading_WelcomeIbidding);
//
//        btReassing.setOnClickListener(this);
//        btReject.setOnClickListener(this);
//        btnWelcome.setOnClickListener(this);
//        btnNoSHow.setOnClickListener(this);
//    }


    @Override
    protected void onResume() {
        super.onResume();

        if (getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getBoolean(Util.EXIT_NOSHOW_KEY, false)) {
            SharedPreferences preferences = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Util.EXIT_NOSHOW_KEY, false);
            editor.commit();
            finish();
//            iOperationFragment.thisFragment.CallTheJob();
        }

        if (getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getBoolean(Util.EXIT_SHOW_KEY, false)) {
            SharedPreferences preferences = getSharedPreferences(Util.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Util.EXIT_SHOW_KEY, false);
            editor.commit();
            finish();
//            iOperationFragment.thisFragment.CallTheJob();
        }
    }

}
