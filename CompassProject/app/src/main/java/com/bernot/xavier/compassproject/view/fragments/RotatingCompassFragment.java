package com.bernot.xavier.compassproject.view.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.tools.CGPSTracker;

/**
 * Created by Xavier on 22/05/2015.
 * Compass rotatable with device orientation and target orientation
 */
public class RotatingCompassFragment extends Fragment
{
    //value in degree that represent difference between two measurment to get an update of compass_old
    private static final float UPDATE_STEP = 1.5f;

    private ImageView m_CompassImage;
    private ImageView m_ArrowImage;

    private float m_PreviousOrientation;

    private boolean m_IsActive = false;

    private CGeoCoordinates m_LastKnownLocation;
    public CGeoCoordinates getLastKnownLocation()
    {
        return m_LastKnownLocation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rotating_compass, container, false);

        m_CompassImage = (ImageView)view.findViewById(R.id.compassImage);
        m_ArrowImage = (ImageView)view.findViewById(R.id.arrowImage);

        updateCompassUI(0, 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        m_IsActive = true;
    }

    @Override
    public void onPause() {
        super.onResume();

        m_IsActive = false;
    }


    /**
     * get de the last orientation
     * @return last orientation
     */
    public float getOrientation()
    {
        return m_PreviousOrientation;
    }
    /**
     * Rotate the compass from current location, destination location and device orientation
     * @param pTracker GPS tracker that permit to get current device information
     * @param pDestination Destionation coordinates
     * @param pDeviceOrientation currentorientation of device in degrees (from -180 to +180)
     * @return true if the compass orientation really changed
     */
    public boolean setOrientation(CGPSTracker pTracker, CGeoCoordinates pDestination, float pDeviceOrientation)
    {
        //Update UI only if difference of step
        if(Math.abs(pDeviceOrientation - m_PreviousOrientation) > UPDATE_STEP
                && pTracker != null
                && pDestination != null
                && pDeviceOrientation <= 180f && pDeviceOrientation >= -180f)
        {

            Location currentLocation = pTracker.getLocation();
            if(currentLocation == null)
            {
                return false;
            }

            m_LastKnownLocation = new CGeoCoordinates(currentLocation);

            float destOrientation = m_LastKnownLocation.bearingTo(pDestination) + pDeviceOrientation;

            m_PreviousOrientation = pDeviceOrientation;

            //UI update
            updateCompassUI(pDeviceOrientation, destOrientation);

            return true;
        }

        return false;
    }



    /**
     * Set compass rotation from device orientation and destination orientation
     * @param pDeviceOrientation device orientation
     * @param pDestOrientation destination oreintation
     */
    private void updateCompassUI(float pDeviceOrientation, float pDestOrientation)
    {
        if(!m_IsActive)
        {
            return;
        }

        m_CompassImage.setRotation(pDeviceOrientation);
        m_ArrowImage.setRotation(pDestOrientation);
    }

    /**
     * update only destination orientation, use when destination change
     * @param pDestinationCoordinates new destination coordinates
     * @return true if destination orientation really changed
     */
    public boolean updateDestinationOrientation(CGeoCoordinates pDestinationCoordinates)
    {
        if(pDestinationCoordinates != null && m_LastKnownLocation != null)
        {
            float destOrientation = m_LastKnownLocation.bearingTo(pDestinationCoordinates) + m_PreviousOrientation;

            //UI update
            updateCompassUI(m_PreviousOrientation, destOrientation);

            return true;
        }
        return false;
    }
}
