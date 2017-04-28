package com.actiknow.famdent.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 16-03-2017.
 */

public class LocationManager extends Activity implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 0 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60 * 1000; // 1 second
    public static String LOG = "Log";

    //   JSONParser jsonParser = new JSONParser ();
    private final Context mContext;
    // Declaring a Location Manager
    protected android.location.LocationManager locationManager;
    //    DatabaseHandler db;
    Calendar cur_cal = Calendar.getInstance ();
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude


    public LocationManager (Context context) {
        this.mContext = context;
/*
        Utils.showLog (Log.INFO, "LOCATION SERVICE", "SERVICE STARTED", true);
        UserDetailsPref userDetailsPref = UserDetailsPref.getInstance ();
//        String user_login_key = userDetailsPref.getStringPref (context, UserDetailsPref.USER_LOGIN_KEY);
        Calendar c = Calendar.getInstance ();
        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format (c.getTime ());

        Calendar now = Calendar.getInstance ();
        int hour = now.get (Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get (Calendar.MINUTE);
        Date date = parseDate (hour + ":" + minute);
        Date dateCompareOne = parseDate (Constants.location_tagging_start_time);
        Date dateCompareTwo = parseDate (Constants.location_tagging_end_time);
        try {
            if (dateCompareOne.before (date) && dateCompareTwo.after (date)) {
                Utils.showLog (Log.INFO, "LOCATION SERVICE", "WITHIN TIME LIMITS", true);
                sendLocationDetailsToServer (context, String.valueOf (getLocation ().getLatitude ()), String.valueOf (getLocation ().getLongitude ()), formattedDate);
            } else {
                Utils.showLog (Log.INFO, "LOCATION SERVICE", "NOT WITHIN TIME LIMITS", true);
            }
        } catch (Exception e) {
            Utils.showLog (Log.ERROR, "EXCEPTION", "Exception in location", true);
        }
*/

    }

    public double getLatitude () {
        if (location != null) {
            latitude = location.getLatitude ();
        }
        // return latitude
        return latitude;
    }

    public double getLongitude () {
        if (location != null) {
            longitude = location.getLongitude ();
        }
        // return longitude
        return longitude;
    }

    public Location getLocation () {
        try {
            locationManager = (android.location.LocationManager) mContext.getSystemService (LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled (android.location.LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled (android.location.LocationManager.NETWORK_PROVIDER);
            if (! isGPSEnabled && ! isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    } else {
                        locationManager.requestLocationUpdates (android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Utils.showLog (Log.INFO, "LOCATION TYPE", "NETWORK", true);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation (android.location.LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude ();
                                longitude = location.getLongitude ();
                            }
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates (android.location.LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Utils.showLog (Log.INFO, "LOCATION TYPE", "GPS", true);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation (android.location.LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude ();
                                longitude = location.getLongitude ();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return location;
    }

    @Override
    public void onLocationChanged (Location location) {
        Utils.showLog (Log.INFO, "LOCATION SERVICE", "IN LOCATION CHANGED", true);
//        Context appCtx = AppController.getAppContext ();
//        LoginDetailsPref loginDetailsPref = LoginDetailsPref.getInstance ();
//        int auditor_id = loginDetailsPref.getIntPref (appCtx, LoginDetailsPref.AUDITOR_ID);
//        sendLocationDetailsToServer (auditor_id, String.valueOf (getLocation ().getLatitude ()), String.valueOf (getLocation ().getLongitude ()));
    }

    @Override
    public void onStatusChanged (String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled (String provider) {
    }

    @Override
    public void onProviderDisabled (String provider) {
    }


    private Date parseDate (String date) {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse (date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
