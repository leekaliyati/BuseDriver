package com.example.int_systems.busedriver.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.int_systems.busedriver.MainActivity;
import com.example.int_systems.busedriver.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class CheckBooking extends Service {
    DatabaseHelper myDb;
    String driverID;
    String myStatus;
    Context context;
    private int NOTIFICATION_ID = 100;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        theDriver();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
               printMessage();

            }
        }, 0, 50000);




    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notifyM() {

        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.index);
        mBuilder.setContentTitle("Booking Request");
        mBuilder.setContentText("Hi, you have a booking request!");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public String theDriver(){
        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            driverID = res.getString(1);

        }
        return driverID;

    }
    public String userStatus(){
        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            myStatus =res.getString(2);

        }
        return myStatus;

    }
    private void printMessage() {
        driverID=theDriver();
        myStatus =userStatus();
        SyncHttpClient client = new SyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driverID", driverID);
        params.put("status",myStatus);

        client.post("http://192.168.1.2/BuseTaxi/Android/checkBooking.php", params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if (responseString.equalsIgnoreCase("true")) {

                    notifyM();

                }

            }

        });

    }
}