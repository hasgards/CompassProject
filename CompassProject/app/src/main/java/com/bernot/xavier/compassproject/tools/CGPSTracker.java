package com.bernot.xavier.compassproject.tools;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.content.Context;


/**
 * Created by Xavier on 15/04/2015.
 * GPS tracker class, can give last know location of user
 */
public class CGPSTracker  extends Service implements LocationListener {

    private final Context m_Context;

    // Flag for GPS status
    private boolean m_IsGPSEnabled = false;

    // Flag for network status
    private boolean m_IsNetworkEnabled = false;

    // Flag for GPS status
    private boolean m_CanGetLocation = false;

    private Location m_Location; // Location
    private double m_Latitude; // Latitude
    private double m_Longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minut

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public CGPSTracker(Context context)
    {
        this.m_Context = context;
        getLocation();
    }

    /**
     * Returns the last known location from GPS
     * @return lastKnown location
     */
    public Location getLocation() {
        try
        {
            locationManager = (LocationManager) m_Context.getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            m_IsGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            m_IsNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!m_IsGPSEnabled && !m_IsNetworkEnabled)
            {
                // No network provider is enabled
            }
            else
            {
                this.m_CanGetLocation = true;
                if (m_IsNetworkEnabled)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null)
                    {
                        m_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (m_Location != null)
                        {
                            m_Latitude = m_Location.getLatitude();
                            m_Longitude = m_Location.getLongitude();
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (m_IsGPSEnabled)
                {
                    if (m_Location == null)
                    {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null)
                        {
                            m_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (m_Location != null)
                            {
                                m_Latitude = m_Location.getLatitude();
                                m_Longitude = m_Location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return m_Location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(CGPSTracker.this);
        }
    }


    /**
     * Function to get latitude
     * @return lastKnown location latitude
     * */
    public double getLatitude()
    {
        if(m_Location != null){
            m_Latitude = m_Location.getLatitude();
        }

        // return latitude
        return m_Latitude;
    }


    /**
     * Function to get longitude
     * @return lastKnown location longitude
     * */
    public double getLongitude()
    {
        if(m_Location != null){
            m_Longitude = m_Location.getLongitude();
        }

        // return longitude
        return m_Longitude;
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.m_CanGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
