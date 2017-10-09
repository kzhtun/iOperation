package com.info121.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.info121.ioperations.MainActivity;
import com.info121.ioperations.R;

/**
 * Created by KZHTUN on 7/28/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingService";
    private static final String EXTRA_MESSAGE = "extra.message";
    public final String text = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Toast.makeText(getApplicationContext(), "Message Received !" , Toast.LENGTH_SHORT).show();

//       if(remoteMessage.getNotification() != null) {
//           String title = (remoteMessage.getNotification().getTitle() != null) ? remoteMessage.getNotification().getTitle() : "";
//           String body = (remoteMessage.getNotification().getBody() != null) ? remoteMessage.getNotification().getBody() : "";
//
//           //showNotification(title, remoteMessage);
//           Log.e("Notification : ", body);
//       }

        if (remoteMessage.getData() != null) {
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }

    }

    private void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_mail_white_48dp)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(this, R.color.COLOR_BG))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

}
