package com.example.sebastian.stoper.Faragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.example.sebastian.stoper.MainActivity;
import com.example.sebastian.stoper.R;

/**
 * Created by Sebastian on 2016-07-09.
 */
public class StoperService  extends Service
{
public static String key= "abcd";
String Time;
    private int TIME_Seconds = 0;
    private int TIME_HOURS = 0;
    private int TIME_Minutes = 0;
    private static boolean isThreadRunning=false;


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {

    }


    private void Notyfikacja() {
        NotificationCompat.Builder bulider = new NotificationCompat.Builder(this);
        bulider.setAutoCancel(true);
        bulider.setSmallIcon(R.drawable.clock);
        bulider.setContentTitle("Stoper");
        bulider.setContentText("Stoper działa w tle : "+Time);
        Intent intent = new Intent(this,MainActivity.class);

        intent.putExtra(key,TIME_Seconds);
        intent.putExtra("prawda",true);
        TaskStackBuilder stack= TaskStackBuilder.create(this);
        stack.addParentStack(MainActivity.class);
        stack.addNextIntent(intent);
        PendingIntent pendingIntent= stack.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        bulider.setContentIntent(pendingIntent);
        NotificationManager NM= (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0,bulider.build());




    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        int k=intent.getIntExtra("abc",1);
            TIME_Seconds=k;
if(!isThreadRunning) {
    final Handler handler = new Handler();
    handler.post(new Runnable() {
        @Override
        public void run() {
isThreadRunning=true;
            TIME_HOURS = TIME_Seconds / 3600;
            TIME_Minutes = (TIME_Seconds % 3600) / 60;
            int sec = TIME_Seconds % 60;
            Time = String.format("%d:%02d:%02d", TIME_HOURS, TIME_Minutes, sec);

            System.out.println("Service time" + Time);
            TIME_Seconds++;
            Notyfikacja();


            handler.postDelayed(this, 1000);
        }


    });

}


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
