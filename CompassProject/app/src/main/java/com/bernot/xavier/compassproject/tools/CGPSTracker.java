package com.bernot.xavier.compassproject.tools;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
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
    //private boolean m_CanGetLocation = false;

    // Flag for GPS status
    private boolean m_IsGPSStarted = false;
    public boolean isGPSStarted() {
        return m_IsGPSStarted;
    }


    private Location m_Location; // Location
    private double m_Latitude; // Latitude
    private double m_Longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5; // 5 seconds

    private onLocationChangedListener m_OnLocationChangedListener;
    public void setOnLocationChangedListener(onLocationChangedListener pOnLocationChangedListener) {
        this.m_OnLocationChangedListener = pOnLocationChangedListener;
    }

    // Declaring a Location Manager
    protected LocationManager m_LocationManager;

    /**
     * CTOR from context, will automatically get last known location
     * @param pContext
     */
    public CGPSTracker(Context pContext)
    {
        this.m_Context = pContext;
        getLocation();
    }

    /**
     * CTOR from context, will get last know location if pGetLocation = true
     * @param pContext
     * @param
     */
    private CGPSTracker(Context pContext, boolean pGetLocation)
    {
        this.m_Context = pContext;
        if(pGetLocation)
        {
            getLocation();
        }
    }


    /**
     * Returns the last known location from GPS
     * @return last known location
     */
    public Location getLocation() {
        try
        {
            LocationManager locationManager = (LocationManager) m_Context.getSystemService(LOCATION_SERVICE);

            if (!canGetLocation(locationManager))
            {
                // No network provider is enabled
            }
            else
            {
                if (m_IsNetworkEnabled)
                {
                    CGPSTracker gpsTracker = new CGPSTracker(m_Context, false);

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, gpsTracker);

                    if (locationManager != null)
                    {
                        m_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (m_Location != null)
                        {
                            m_Latitude = m_Location.getLatitude();
                            m_Longitude = m_Location.getLongitude();
                        }
                    }
                    locationManager.removeUpdates(gpsTracker);
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (m_IsGPSEnabled)
                {
                    if (m_Location == null)
                    {
                        CGPSTracker gpsTracker = new CGPSTracker(m_Context, false);
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, null, null);

                        if (locationManager != null)
                        {
                            m_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (m_Location != null)
                            {
                                m_Latitude = m_Location.getLatitude();
                                m_Longitude = m_Location.getLongitude();
                            }
                        }
                        locationManager.removeUpdates(gpsTracker);
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
     * Starts to use the GPS Listener
     */
    public void startUsingGPS(){

        if(m_IsGPSStarted)
        {
            return;
        }

        if(m_LocationManager == null)
        {
            m_LocationManager = (LocationManager) m_Context.getSystemService(LOCATION_SERVICE);

            if(m_LocationManager == null)
            {
                return;
            }
        }

        /*Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);//true if required
        criteria.setBearingRequired(false);//true if required
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);*/
        m_LocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);

        m_LocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);

        m_IsGPSStarted = true;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS
     * */
    public void stopUsingGPS(){
        if(m_LocationManager != null){
            m_LocationManager.removeUpdates(CGPSTracker.this);
            m_LocationManager.removeUpdates(CGPSTracker.this);
            m_IsGPSStarted = false;
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
        try
        {
            LocationManager locationManager = (LocationManager) m_Context.getSystemService(LOCATION_SERVICE);
            return canGetLocation(locationManager);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function to check GPS/Wi-Fi enabled using a particular locationManager
     * @param pLocationManager location manager
     * @return boolean
     * */
    private boolean canGetLocation(LocationManager pLocationManager)
    {
        try
        {
            if(pLocationManager == null)
            {
                return false;
            }

            // Getting GPS status
            m_IsGPSEnabled = pLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            m_IsNetworkEnabled = pLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!m_IsGPSEnabled && !m_IsNetworkEnabled) {
                // No network provider is enabled
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (this.m_OnLocationChangedListener != null)
        {
            m_Location = location;
            this.m_OnLocationChangedListener.callback(location);
        }
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

    public interface onLocationChangedListener {
        public void callback(Location pLocation);
    }
}
