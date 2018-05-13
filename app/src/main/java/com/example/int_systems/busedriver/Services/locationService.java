package com.example.int_systems.busedriver.Services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.int_systems.busedriver.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class locationService extends Service implements LocationListener {

    DatabaseHelper myDb;
    String driverID;
    String myStatus;

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private android.os.Handler mHandler = new android.os.Handler();
    private Timer mTimer = null;
    long notify_interval = 1000;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
        myDb = new DatabaseHelper(this);

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 1000, notify_interval);
        intent = new Intent(str_receiver);


        fn_getlocation();
    }


    @Override
    public void onLocationChanged(Location location) {
       

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {

                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }

            }


            if (isGPSEnable) {
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);

                    }
                }
            }
         //   updateSever(location);


        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                //  userStatus();
                //  theDriver();
                    fn_getlocation();

                }
            });

        }
    }
    // get the current status of the driver
    public String userStatus(){
        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            myStatus =res.getString(2);

        }
        return myStatus;

    }
    // get the id of the driver
    public String theDriver(){
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            driverID = res.getString(1);
        }
        return driverID;

    }

    private void fn_update(Location location){

        System.out.println("+++++++i send and intent+++++++");
        intent.putExtra("latutide",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        sendBroadcast(intent);
    }

    }

  // 5000ms delay

