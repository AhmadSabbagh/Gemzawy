package com.am.gemzawy.fifa.Services;

/**
 * Created by Luminance on 5/4/2018.
 */


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.am.gemzawy.fifa.Notification_Page;
import com.am.gemzawy.fifa.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Date;


/**
 * Created by Ahmad on 08/05/19.

 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Date date ;
    private NotificationChannel mChannel;
    private NotificationManager notifManager;
    String TAG="TAG";
    String title,body,comp_id,message;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

         date=new Date();
        Log.e("Response >>>>", new Gson().toJson(remoteMessage.getData()));
        Log.e("response2 >>>>", new Gson().toJson(remoteMessage.getNotification()));
        //   getNotificationStatus(remoteMessage);
        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("message");

        body = remoteMessage.getData().get("body");
        if(body !=null && body.contains("inconvenience")) {
           // comp_id = remoteMessage.getData().get("comp_id");
           // FirebaseMessaging.getInstance ( ).unsubscribeFromTopic ("Fifa");

        }
        comp_id = remoteMessage.getData().get("comp_id");
        if(comp_id!=null)
        {
            FirebaseMessaging.getInstance ( ).unsubscribeFromTopic (comp_id+"_Fifa_comp");

        }

      /*  if(noti_status!=null)
        {
            if(noti_status.equals("1"))
                sendNotification(title, content);
            else if(noti_status.equals("0"))
            {

            }
        }
        else
        {
            attolSharedPreference.setKey1("Notification_status","1");
            sendNotification(title, content);
        }*/
      if(title!=null && body!=null) {
          sendNotification(title, body);
      }
      else
      {
          sendNotification("Auto generete Title", "Auto");

      }

    }





    private void sendNotification(String title, String content) {

        Intent intent = new Intent(getApplicationContext(), Notification_Page.class);//Notification Page
        intent .setAction(Intent.ACTION_MAIN);
        intent .addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("msg",content);
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (mChannel == null) {
                NotificationChannel mChannel = new NotificationChannel
                        ("0", title, importance);
                mChannel.setDescription (content);
                mChannel.enableVibration (true);
                mChannel.setVibrationPattern (new long[]
                        {100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel (mChannel);
            }
            builder = new NotificationCompat.Builder (this, "0");

            intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle (title)  // flare_icon_30
                    .setSmallIcon(R.mipmap.logo)
                    .setContentText (content)  // required
                    .setDefaults (Notification.DEFAULT_ALL)
                    .setAutoCancel (true)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo))
                    .setContentIntent (pendingIntent)
                    .setSound (RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate (new long[]{100, 200, 300, 400,
                            500, 400, 300, 200, 400});
        }

        else
        {
            builder = new NotificationCompat.Builder (this);

            intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity (this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle (title)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentText (content)  // required
                    .setDefaults (Notification.DEFAULT_ALL)
                    .setAutoCancel (true)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo))
                    .setContentIntent (pendingIntent)
                    .setSound (RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate (new long[]{100, 200, 300, 400, 500,
                            400, 300, 200, 400})
                    .setPriority (Notification.PRIORITY_HIGH);

        }

        Notification notification = builder.build ();
        notifManager.notify((int)date.getTime(), notification);

    }










}