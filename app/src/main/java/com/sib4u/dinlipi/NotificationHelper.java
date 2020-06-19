package com.sib4u.dinlipi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
public static final String PRIMARY_CHANNEL="default";
public static final String SECOND_CHANNEL="second";
    private NotificationManager notificationManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);
        NotificationChannel channel1=new NotificationChannel(PRIMARY_CHANNEL,"chanel1",NotificationManager.IMPORTANCE_HIGH);
        channel1.setLightColor(Color.GREEN);
        channel1.enableLights(true);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManeger().createNotificationChannel(channel1);
    }
   public NotificationManager getManeger() {

       if(notificationManager==null)
        {
            notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
   public NotificationCompat.Builder getChannelNotification(String message){
        return  new NotificationCompat.Builder(getApplicationContext(),PRIMARY_CHANNEL)
                .setContentTitle("Time to do")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL));
   }
}
