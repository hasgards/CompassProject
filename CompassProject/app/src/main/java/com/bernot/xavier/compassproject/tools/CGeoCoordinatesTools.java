package com.bernot.xavier.compassproject.tools;

import android.content.Context;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;

import java.util.ArrayList;

/**
 * Created by Xavier on 20/06/2015.
 */
public class CGeoCoordinatesTools
{
    /**
     * Returns the initial list of places
     * @param pContext application context
     * @return initial list
     */
    public static final ArrayList<CGeoCoordinates> getCitiesInitialList(final Context pContext)
    {
        return new ArrayList<CGeoCoordinates>()
        {
            {
                add(new CGeoCoordinates(pContext.getString(R.string.place_alexandria), 31.2003861, 27.1258081));
                add(new CGeoCoordinates(pContext.getString(R.string.place_amsterdam), 52.3702157, 4.8951679));
                add(new CGeoCoordinates(pContext.getString(R.string.place_auckland), -36.8927479, 174.7634173));
                add(new CGeoCoordinates(pContext.getString(R.string.place_berlin), 52.5177608, 13.4087306));
                add(new CGeoCoordinates(pContext.getString(R.string.place_beijing), 39.9042110, 116.4073950));
                add(new CGeoCoordinates(pContext.getString(R.string.place_buenos_aires), -34.6870063, -58.3652513));
                add(new CGeoCoordinates(pContext.getString(R.string.place_cape_town), -33.9248685, 18.4240553));
                add(new CGeoCoordinates(pContext.getString(R.string.place_dublin), -53.3498053, -6.2603097));
                add(new CGeoCoordinates(pContext.getString(R.string.place_geneva), 46.1983922, 6.1422961));
                add(new CGeoCoordinates(pContext.getString(R.string.place_hong_kong), 22.2819490, 114.1742133));
                add(new CGeoCoordinates(pContext.getString(R.string.place_kuala_lumpur), 3.1390030, 101.6868550));
                add(new CGeoCoordinates(pContext.getString(R.string.place_london), 51.5073509, -0.1277583));
                add(new CGeoCoordinates(pContext.getString(R.string.place_los_angeles), 34.0522342, -118.2436849));
                add(new CGeoCoordinates(pContext.getString(R.string.place_madrid), 40.4167754, -3.7037902));
                add(new CGeoCoordinates(pContext.getString(R.string.place_mexico_city), 19.4326077, -99.1332080));
                add(new CGeoCoordinates(pContext.getString(R.string.place_moscow), 55.7558260, 37.6173000));
                add(new CGeoCoordinates(pContext.getString(R.string.place_nairobi), -1.2920659, 36.8219462));
                add(new CGeoCoordinates(pContext.getString(R.string.place_new_dehli), 28.6139391, 77.2090212));
                add(new CGeoCoordinates(pContext.getString(R.string.place_new_york), 40.7127837, -74.0059413));
                add(new CGeoCoordinates(pContext.getString(R.string.place_paris), 48.8566140, 2.3522219));
                add(new CGeoCoordinates(pContext.getString(R.string.place_rabat), 33.9666076, -6.8515295));
                add(new CGeoCoordinates(pContext.getString(R.string.place_rio_de_janeiro), -22.9091395, -43.1978732));
                add(new CGeoCoordinates(pContext.getString(R.string.place_riyadh), 24.6333330, 46.7166670));
                add(new CGeoCoordinates(pContext.getString(R.string.place_rome), 41.9027835, 12.4963655));
                add(new CGeoCoordinates(pContext.getString(R.string.place_san_francisco), 37.7749295, -122.4194155));
                add(new CGeoCoordinates(pContext.getString(R.string.place_sydney), -33.8830218, 151.1955747));
                add(new CGeoCoordinates(pContext.getString(R.string.place_tokyo), 35.6894875, 139.6917064));
                add(new CGeoCoordinates(pContext.getString(R.string.place_toronto), 43.6532260, -79.3831843));
                add(new CGeoCoordinates(pContext.getString(R.string.place_vienna), 48.2081743, 16.3738189));
                add(new CGeoCoordinates(pContext.getString(R.string.place_warsaw), 52.2296756, 21.0122287));
            }
        };
    }
}
