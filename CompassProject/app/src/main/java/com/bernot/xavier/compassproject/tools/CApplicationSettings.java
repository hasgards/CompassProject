package com.bernot.xavier.compassproject.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

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
