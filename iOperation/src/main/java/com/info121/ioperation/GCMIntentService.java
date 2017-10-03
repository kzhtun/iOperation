package com.info121.ioperation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.info121.ioperation.R;
import com.info121.ioperation.fragment.FeedbackFragment;
import com.info121.ioperation.fragment.iBiddingFragment;
import com.info121.ioperation.fragment.iOperationFragment;
import com.info121.ioperation.util.Util;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(Util.SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        Util.displayMessage(context, getString(R.string.gcm_registered));
        Util.gcm_register_id = registrationId;
        iOPLoginActivity.updateGCMText();
        //Util.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        Util.displayMessage(context, getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            //Util.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        //String message = getString(R.string.gcm_message);
        
        String message = intent.getExtras().getString("message").toString();
        
        try {
			if(ChooseTaskActivity.isActive){
				ChooseTaskActivity.thisFragment.CallTheJob();
			}
			
			 Util.displayMessage(context, message);
		        // notifies user
		     generateNotification(ChooseTaskActivity.context, message);
		} catch (Exception e) {
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(context, message);
		}
        
        try {
			if(iOperationFragment.isActive){
				iOperationFragment.thisFragment.CallTheJob();
			}
			
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(context, message);
		} catch (Exception e) {
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(iOperationFragment.thisFragment.getActivity(), message);
		}
        
        try {
			if(iBiddingFragment.isActive){
				iBiddingFragment.thisFragment.CallTheJob();
			}
			
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(iBiddingFragment.thisFragment.getActivity(), message);
		} catch (Exception e) {
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(context, message);
		}
        
        try {
			if(FeedbackFragment.isActive){
				FeedbackFragment.thisFragment.CallTheJob();
			}
			
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(FeedbackFragment.thisFragment.getActivity(), message);
		} catch (Exception e) {
			 Util.displayMessage(context, message);
		        // notifies user
		        generateNotification(context, message);
		}
        
       
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        Util.displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        Util.displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        Util.displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    public static void generateNotification(Context context, String message) {
        int icon = R.drawable.shlogo_notif;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        
        
        
        String checkLogin = context.getSharedPreferences(Util.SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString(Util.LOGIN_KEY, "");
		
        Intent notificationIntent;
		
		if(!(checkLogin.compareToIgnoreCase("")==0)){
		     notificationIntent = new Intent(context, ChooseTaskActivity.class);
			
		}else{
			notificationIntent = new Intent(context, iOPLoginActivity.class);
		}

       
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

}
