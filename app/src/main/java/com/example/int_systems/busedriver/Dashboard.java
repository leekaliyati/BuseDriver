package com.example.int_systems.busedriver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.int_systems.busedriver.Services.DatabaseHelper;
import com.example.int_systems.busedriver.Services.locationService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Dashboard extends AppCompatActivity {
    DatabaseHelper myDb;
    String status;
    String driverID;
    String myStatus;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context;
        Switch switchButton, switchButton2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        myDb = new DatabaseHelper(this);
        switchButton = (Switch) findViewById(R.id.simpleSwitch);
        getStatus();
        getService();
        SendCodinates();

        Intent serviceIntent = new Intent(this, locationService.class);
        this.startService(serviceIntent);

        status= getStatus();

        if (status.equals("false")){
            switchButton.setChecked(false);
        }


        if (status.equals("true")){
            switchButton.setChecked(true);
            }


          Toast.makeText(this,status,Toast.LENGTH_LONG).show();
       // switchButton.setChecked(true);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    updateTrue();
                   } else {

                    updateFalse();
                }
            }
        });



    }
    public String getStatus(){
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            status = res.getString(2);
        }
        return status;

    }

    // get the id of the driver
    public String theDriver(){
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            driverID = res.getString(1);
        }
        return driverID;

    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            driverID=theDriver();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("driverID", driverID);
            params.put("latitude",latitude);
            params.put("longitude",longitude);

            client.post("http://192.168.1.2/BuseTaxi/Android/updateStatus.php", params, new TextHttpResponseHandler() {


                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("++++++++++Error+++++++++++");

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    System.out.println("++++++++++++Success+++++++++++++");
                    System.out.println(responseString);

                }
            });

        }


    };

    private void getService() {
        this.registerReceiver(broadcastReceiver, new IntentFilter(locationService.str_receiver));
    }

    private void updateTrue() {
        myDb.updateMethod();
    }
    private void updateFalse(){
        myDb.updateFalse();

    }
    public  void SendCodinates(){

        driverID=theDriver();
        System.out.println("+++++++++Driver Id++++++++");
        System.out.println(driverID);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("driverID", driverID);
        params.put("latitude",latitude);
        params.put("longitude",longitude);

        client.post("http://172.16.2.208/BuseTaxi/Android/updateStatus.php", params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });
    }






}
