package com.example.waterreminder.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.waterreminder.R;
import com.example.waterreminder.TabActivity;

public class MyService extends Service {

    public static boolean isServiceRunning;
    private final ScreenLockReceiver screenLockReceiver;
    private final String CHANNEL_ID = "NOTIFICATION_CHANNEL";
    int intervalTime = 1;
    Handler handler;

    public MyService() {
        Log.e("MyService", "constructor called");
        isServiceRunning = false;
        screenLockReceiver = new ScreenLockReceiver();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyService", "onCreate called");
        isServiceRunning = true;
        createNotificationChannel();
        // register receiver to listen for screen on events
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenLockReceiver, filter);

        handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                handler.postDelayed(this, intervalTime * 60 * 1000);
                Log.e("My service", "onReceive called:interval time " + intervalTime + " Min");
//                    new Util().setRandomWallpaper(MyService.this);
            }
        }, 0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService", "onStartCommand called");
        Intent notificationIntent = new Intent(this, TabActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Its a time to drink water.")
                .setContentText("Tap here after drink.")
                .setSmallIcon(R.drawable.bluecup8)
                .setColor(getResources().getColor(R.color.blue))
                .setContentIntent(pendingIntent)
                .build();
        /*
         * A started service can use the startForeground API to put the service in a foreground state,
         * where the system considers it to be something the user is actively aware of and thus not
         * a candidate for killing when low on memory.
         */
        startForeground(1, notification);

        // does not work as expected though, even START_NOT_STICKY gives same result
        // device specific issue?
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("MyService", "onDestroy called");
        isServiceRunning = false;
        stopForeground(true);

        // unregister receiver
        unregisterReceiver(screenLockReceiver);
        // call MyReceiver which will restart this service via a worker
        Intent broadcastIntent = new Intent(this, MyReceiver.class);
        sendBroadcast(broadcastIntent);

        super.onDestroy();
    }

    // Not getting called on Xiaomi Redmi Note 7S even when autostart permission is granted
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("MyService", "onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String appName = getString(R.string.app_name);
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    appName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
