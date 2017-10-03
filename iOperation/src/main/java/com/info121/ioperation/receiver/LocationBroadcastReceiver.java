package com.info121.ioperation.receiver;

import com.info121.constant.StaticLocationData;
import com.info121.ioperation.R;
import com.info121.ioperation.iOpMainViewPagerActivity;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LocationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.d("LocationBroadcastReceiver", "onReceive: received location update");
        
        final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
       
        // The broadcast has woken up your app, and so you could do anything now - 
        // perhaps send the location to a server, or refresh an on-screen widget.
        // We're gonna create a notification.
        
        // Construct the notification.
        Notification notification = new Notification(R.drawable.iop_logo, "Locaton updated " + locationInfo.getTimestampAgeInSeconds() + " seconds ago", System.currentTimeMillis());

        Intent contentIntent = new Intent(context, iOpMainViewPagerActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        notification.setLatestEventInfo(context, "Location update broadcast received", "Timestamped " + LocationInfo.formatTimeAndDay(locationInfo.lastLocationUpdateTimestamp, true), contentPendingIntent);
        
        // Trigger the notification.
       // ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(123456, notification);

     
		StaticLocationData.latitude = Float.toString(locationInfo.lastLat);
		StaticLocationData.longitude = Float.toString(locationInfo.lastLong);
		StaticLocationData.accuracy = Integer.toString(locationInfo.lastAccuracy);
    }
}