package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String MY_CHANNEL_ID = "MY_CHANNEL_ID7";

@SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
    int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
            WifiManager.WIFI_STATE_UNKNOWN);
// משפט switch שיגיב למצבים השונים
    switch (wifiStateExtra) {
        case WifiManager.WIFI_STATE_ENABLED:
            Toast.makeText(context, "WiFi is ON", Toast.LENGTH_LONG).show();
            break;
        case WifiManager.WIFI_STATE_DISABLED:
            Toast.makeText(context, "WiFi is OFF", Toast.LENGTH_LONG).show();
            break;
    }
}
}


//NotificationManagerCompat notificationManager =
//                NotificationManagerCompat.from(context);
//        NotificationCompat.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "My Notification Channel";
//            String description = "Channel for handling all notifications";
//            int importance = NotificationManager.IMPORTANCE_LOW;
//            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            notificationManager.createNotificationChannel(channel);
//            builder = new NotificationCompat.Builder(context, MY_CHANNEL_ID);
//        } else {
//            builder = new NotificationCompat.Builder(context);
//        }
//        Intent activityIntent = new Intent(context, Splash.class);
//        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                activityIntent, PendingIntent.FLAG_IMMUTABLE );
//        builder
//                .setSmallIcon(R.drawable.ic_stat_name)
//                .setContentTitle("my title")
//                .setContentText("my content")
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//         notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(1, builder.build());
//
//    }
//}
