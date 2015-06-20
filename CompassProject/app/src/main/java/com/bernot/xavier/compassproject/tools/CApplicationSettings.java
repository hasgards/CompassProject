package com.bernot.xavier.compassproject.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.bernot.xavier.compassproject.Enums.ELengthUnit;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier on 21/05/2015.
 * Simple Settings singleton class with integrated load/save
 */
public class CApplicationSettings {

    private final static String APP_SETTING_PREFS = "appSettings";
    private final static String APP_DATA = "appData";

    /*
    *Singleton
    * */
    private static CApplicationSettings ourInstance = new CApplicationSettings();
    public static CApplicationSettings getInstance() {
        return ourInstance;
    }
    private CApplicationSettings() {
    }

    /*
    * First launch
    * */
    private boolean m_IsFirstLaunch = true;
    public boolean isFirstLaunch() {
        return m_IsFirstLaunch;
    }
    public void setIsFirstLaunch(boolean pIsFirstLaunch, Context pContext) {
        this.m_IsFirstLaunch = pIsFirstLaunch;
        saveSettings(pContext);
    }

    /*
   * destination list
   * */
    private ArrayList<CGeoCoordinates> m_DestinationsList;
    public ArrayList<CGeoCoordinates> getDestinationList() {
        return m_DestinationsList;
    }
    public void setDestinationList(ArrayList<CGeoCoordinates> pDestinationsList, Context pContext) {
        this.m_DestinationsList = pDestinationsList;
        saveSettings(pContext);
    }

    /**
     * Last destination choosen by user
     */
    private CGeoCoordinates m_LastDestinationChoosen;
    public CGeoCoordinates getLastDestinationChoosen() {
        return m_LastDestinationChoosen;
    }
    public void setLastDestinationChoosen(CGeoCoordinates pLastDestinationChoosen, Context pContext) {
        this.m_LastDestinationChoosen = pLastDestinationChoosen;
        saveSettings(pContext);
    }

    /**
     * Unit of distance
     */
    private ELengthUnit m_Unit = ELengthUnit.KILOMETER;
    public ELengthUnit getUnit() {
        return m_Unit;
    }
    public void setUnit(ELengthUnit pUnit, Context pContext) {
        this.m_Unit = pUnit;
        saveSettings(pContext);
    }

    /**
     * Save the settings
     * @param pContext context of application
     */
    public static void saveSettings(Context pContext)
    {
        Type type = new TypeToken<CApplicationSettings>(){}.getType();
        String json = new Gson().toJson(CApplicationSettings.getInstance(), type);
        System.out.println(json);

        SharedPreferences sharedPref = pContext.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pContext.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit();
        prefEditor.putString( APP_SETTING_PREFS, json );
        prefEditor.commit();
    }

    /**
     * Load the settings
     * @param pContext context of the application
     */
    public static void loadSettings(Context pContext)
    {
        try {
            SharedPreferences prefs = pContext.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
            String json = prefs.getString(APP_SETTING_PREFS, null);

            Type type = new TypeToken<CApplicationSettings>() {}.getType();
            CApplicationSettings appSetting = new Gson().fromJson(json, type);

            if (appSetting == null) {
                ourInstance = new CApplicationSettings();
                saveSettings(pContext);
            } else {
                ourInstance = appSetting;
            }
        }
        catch (Exception e)
        {
            ourInstance = new CApplicationSettings();
            saveSettings(pContext);
        }
    }

}
