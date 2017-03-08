package com.ilungj.android.shoppingtraffic.com.example.ilung.myapplication.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ilungj.android.shoppingtraffic.Application;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Il Ung on 1/8/2017.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    List<Location> locationList;

    String androidID;
    int storeID;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("LOC_SERVICE", "ONSTARTCOMMAND IS CALLED");
        if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }

        /*
        if(intent != null) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.CHECK_LOC")) {
                Log.d("LOC_SERVICE", "ACTION: CHECK_LOC CALLED");
                test2();
            }
        }
        */

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("LOC_SERVICE", "ONDESTROY IS CALLED");
        googleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        //test();
        Log.d("LOC_SERVICE", "ONCREATE IS CALLED");
        setUp();
        super.onCreate();
    }

    /*
    private void test2() {
        try {
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("LOC_SERVICE", "lastLocation's latitude: " + lastLocation.getLatitude());
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    */

    private void setUp() {
        Log.d("LOC_SERVICE", "SETUP IS CALLED");
        androidID = Settings.Secure.getString(Application.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        googleApiClient = new GoogleApiClient.Builder(Application.getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(29999);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        RequestQueue requestQueue = RequestSingleton.getInstance().getRequestQueue();
        String url = "http://www.ilungj.com/jsontest.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                List<CustomLocation> customLocationList = Arrays.asList(gson.fromJson(response, CustomLocation[].class));
                locationList = new ArrayList<>();
                for(CustomLocation cl : customLocationList) {
                    Location l = new Location("");
                    l.setLatitude(cl.getLatitude());
                    l.setLongitude(cl.getLongitude());
                    locationList.add(l);
                }
                Log.d("TEST", "LOCATIONLIST POPULATED" + locationList.get(0).getLatitude());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("LOC_SERVICE", "STARTLOCATIONREQUEST IS CALLED");
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //implemented by connection callbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("LOC_SERVICE", "ONCONNECTED IS CALLED");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //implemented by location listener
    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOC_SERVICE", "LOCATION CHANGED");
        updateUser(location);
        Toast.makeText(Application.getContext(), "Current Store ID: " + storeID, Toast.LENGTH_SHORT).show();
    }

    private void updateUser(Location location) {
        boolean temp = false;
        for(Location l : locationList) {
            if(location.distanceTo(l) < 1609) {
                storeID = locationList.indexOf(l);
                temp = true;
            }
        }
        if(!temp) {
            storeID = 999;
        }

        RequestQueue requestQueue = RequestSingleton.getInstance().getRequestQueue();
        String url = "http://www.ilungj.com/updateuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("androidID", androidID);
                params.put("storeID", Integer.toString(storeID));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private class CustomLocation {
        private transient String imgUrl;
        private double latitude;
        private double longitude;
        private transient int num;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}

