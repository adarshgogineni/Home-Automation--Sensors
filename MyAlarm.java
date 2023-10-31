// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta

package com.example.shaan.hazardapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MyAlarm extends BroadcastReceiver {


    private Timer timer = new Timer();

    @Override
    public void onReceive(Context context, Intent intent) {



        final MediaPlayer mediaPlayer = MediaPlayer.create(context,Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();






        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                mediaPlayer.stop();
            }
        },10000);

    }





}
