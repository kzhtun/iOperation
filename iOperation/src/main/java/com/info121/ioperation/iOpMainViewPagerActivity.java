package com.info121.ioperation;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Toast;

import com.info121.constant.Constants;
import com.info121.ioperation.fragment.FeedbackFragment;
import com.info121.ioperation.fragment.iBiddingFragment;
import com.info121.ioperation.fragment.iOperationFragment;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import com.viewpagerindicator.TitlePageIndicator;

public class iOpMainViewPagerActivity extends RootWithIdleCheckActivity {

    private static final String[] CONTENT = new String[]{"IOPS Assignment", "IOPS Bidding", "Feed back"};

    public static iOpMainViewPagerActivity thisactivity;

    public static ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainviewpager_layout);
        thisactivity = this;

        FragmentPagerAdapter adapter = new TaskAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        pager.setCurrentItem(getIntent().getExtras().getInt(Constants.intentKey_iOp) - 1);

    }


    class TaskAdapter extends FragmentPagerAdapter {
        public TaskAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return iOperationFragment.newInstance(CONTENT[position
                            % CONTENT.length]);

                case 1:
                    return iBiddingFragment.newInstance(CONTENT[position
                            % CONTENT.length]);

                case 2:
                    return FeedbackFragment.newInstance(CONTENT[position
                            % CONTENT.length]);

            }

            return iOperationFragment.newInstance(CONTENT[position
                    % CONTENT.length]);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // cancel any notification we may have received from TestBroadcastReceiver
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(123456);

        refreshDisplay();

        // This demonstrates how to dynamically create a receiver to listen to the location updates.
        // You could also register a receiver in your manifest.
        final IntentFilter lftIntentFilter = new IntentFilter(LocationLibraryConstants.getLocationChangedPeriodicBroadcastAction());
        registerReceiver(lftBroadcastReceiver, lftIntentFilter);
    }

    @Override
    public void onPause() {
        super.onResume();

        unregisterReceiver(lftBroadcastReceiver);
    }

    private void refreshDisplay() {
        refreshDisplay(new LocationInfo(this));
    }

    private void refreshDisplay(final LocationInfo locationInfo) {

        if (locationInfo.anyLocationDataReceived()) {

            Toast.makeText(getApplicationContext(), "Location " + LocationInfo.formatTimeAndDay(locationInfo.lastLocationUpdateTimestamp, true) + " -> Lat : " + Float.toString(locationInfo.lastLat) + " Long: " + Float.toString(locationInfo.lastLong) + " Accuracy: " + Integer.toString(locationInfo.lastAccuracy) + "m", Toast.LENGTH_SHORT).show();


//            buttonShowMap.setVisibility(View.VISIBLE);
//            buttonShowMap.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + locationInfo.lastLat + "," + locationInfo.lastLong + "?q=" + locationInfo.lastLat + "," + locationInfo.lastLong + "(" + locationInfo.lastAccuracy + "m at " + LocationInfo.formatTimeAndDay(locationInfo.lastLocationUpdateTimestamp, true) + ")"));
//                    startActivity(intent);
//                }
//            });
        } else {
            Toast.makeText(getApplicationContext(), "No locations recorded yet", Toast.LENGTH_SHORT).show();

        }
    }

    private final BroadcastReceiver lftBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // extract the location info in the broadcast
            final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
            // refresh the display with it
            refreshDisplay(locationInfo);
        }
    };


}