package com.bernot.xavier.compassproject.model;

import android.location.Location;

/**
 * Created by Xavier on 21/05/2015.
 */
public class CGeoCoordinates extends Location
{
    public static final CGeoCoordinates STRASBOURG_CATHEDRAL = new CGeoCoordinates(48.581892,7.751037);
    public static final CGeoCoordinates RATUSZ_POZNAN = new CGeoCoordinates(52.408586,16.933999);

    private String m_Name = "";
    public String getName() {
        return m_Name;
    }
    public boolean setName(String pName) {
        if(!pName.equals(""))
        {
            this.m_Name = pName;
            return true;
        }
        return false;
    }

    public boolean setLatitude(String pLatitude)
    {
        try
        {
            this.setLatitude(Double.parseDouble(pLatitude));
            return isLatitudeValid();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean setLongitude(String pLongitude)
    {
        try
        {
            setLongitude(Double.parseDouble(pLongitude));
            return isLongitudeValid();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //true if latitude is valid
    public boolean isLatitudeValid(){
        return getLatitude() <= 90 && getLatitude() >= -90;
    }

    //true if longitude is valid
    public boolean isLongitudeValid(){
        return getLongitude() <= 180 && getLongitude() >= -180;
    }

    //CTORs
    public CGeoCoordinates(){
        super("");
    }

    public CGeoCoordinates(Location pLocation)
    {
        super(pLocation);
    }

    public CGeoCoordinates(double pLatitude, double pLongitude)
    {
        super("");
        setLatitude(pLatitude);
        setLongitude(pLongitude);
    }

    public CGeoCoordinates(String pName, double pLatitude, double pLongitude)
    {
        super("");
        setName(pName);
        setLatitude(pLatitude);
        setLongitude(pLongitude);
    }
}
