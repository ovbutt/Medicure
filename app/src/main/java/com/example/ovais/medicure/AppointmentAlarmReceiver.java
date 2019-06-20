package com.example.ovais.medicure;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import static com.example.ovais.medicure.AddAppointmentAlert.RQS_1;


/**
 * Created by Ovais Butt on 4/27/2018.
 */

public class AppointmentAlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

//        if (intent.getAction() != null && context != null) {
//            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Toast.makeText(context, "Alarm Received!", Toast.LENGTH_LONG).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
                mediaPlayer.start();


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, AppointmentAlertActivity.class);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Appointment Alert")
                .setContentText("You have an appointment with the doctor")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, AppointmentAlertActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
                .build();

        notificationManager.notify(RQS_1, notification);
            }

}

//    }
//}

