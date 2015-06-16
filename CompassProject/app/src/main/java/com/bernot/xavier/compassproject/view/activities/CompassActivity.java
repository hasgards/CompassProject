package com.bernot.xavier.compassproject.view.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.model.CSession;
import com.bernot.xavier.compassproject.tools.CApplicationSettings;
import com.bernot.xavier.compassproject.tools.CGPSTracker;
import com.bernot.xavier.compassproject.view.fragments.RotatingCompassFragment;
import com.bernot.xavier.compassproject.view.fragments.TargetInformationsFragment;

import static java.lang.String.format;

/**
 * Created by Xavier on 23/05/2015.
 * Compass activity gets compass position and gps position
 */
public class CompassActivity extends CActivity implements SensorEventListener
{
    private RotatingCompassFragment m_CompassFragment;
    private TargetInformationsFragment m_TargetInfosFragment;

    //Number of measures required before updating distance text
    private static final int DISTANCE_UPDATE_STEP = 1;

    private boolean m_IsActive = false;

    //GPS
    private CGPSTracker m_GPSTracker;

    private CGeoCoordinates m_LastPosition = null;

    //Compass
    private SensorManager m_SensorManager;
    private float[] m_GData = new float[3];
    private float[] m_MData = new float[3];
    private float[] m_R = new float[16];
    private float[] m_I = new float[16];
    private float[] m_Orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        //Toolbar set
        Toolbar toolbar = (Toolbar)findViewById(R.id.compassToolbar);
        setSupportActionBar(toolbar);

        //Sensors
        m_SensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        m_GPSTracker = new CGPSTracker(CompassActivity.this);

        m_CompassFragment = (RotatingCompassFragment)getFragmentManager().findFragmentById(R.id.rotatingCompassFragment);
        m_TargetInfosFragment = (TargetInformationsFragment)getFragmentManager().findFragmentById(R.id.targetInfosFragment);

        /*m_TargetTextView = (TextView)findViewById(R.id.targetTextView);
        m_DistanceTextView = (TextView)findViewById(R.id.distanceTextView);
        m_PositionTextView = (TextView)findViewById(R.id.positionTextView);*/

        //Check if GPS enabled
        if(!m_GPSTracker.canGetLocation()) {
            raiseAlertMessage(getString(R.string.common_warning), getString(R.string.common_warning_geoloc));
        }

        CApplicationSettings.loadSettings(getApplicationContext());

        //Instructions if first launch
        if(CApplicationSettings.getInstance().isFirstLaunch())
        {
            CApplicationSettings.getInstance().setIsFirstLaunch(false, getApplicationContext());
            switchActivity(HelpActivity.class);
        }

        //Register listeners
        Sensor gsensor = m_SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = m_SensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        m_SensorManager.registerListener(this, gsensor, SensorManager.SENSOR_DELAY_GAME);
        m_SensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Display localisation
        m_TargetInfosFragment.updateTargetTextView(CSession.getInstance().getDestinationCoordinates());
        //Update destination if changed
        m_CompassFragment.updateDestinationOrientation(CSession.getInstance().getDestinationCoordinates());

        m_IsActive = true;
    }

    @Override
    protected void onPause()
    {
        super.onResume();
        //Deactivate GPS
        m_GPSTracker.stopUsingGPS();
        m_IsActive = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(!m_IsActive)
        {
            //Do nothing if not active (avoid background CPU use)
            return;
        }

        int type = sensorEvent.sensor.getType();
        float[] data;

        if (type == Sensor.TYPE_ACCELEROMETER)
        {
            data = m_GData;
        }
        else if (type == Sensor.TYPE_MAGNETIC_FIELD)
        {
            data = m_MData;
        }
        else
        {
            // we should not be here.
            return;
        }

        //copy sensorEvent.values into data
        System.arraycopy(sensorEvent.values, 0, data, 0, 3);

        SensorManager.getRotationMatrix(m_R, m_I, m_GData, m_MData);
        SensorManager.getOrientation(m_R, m_Orientation);
        final float rad2deg = (float)(180.0f/Math.PI);
        float orientation = -1 * m_Orientation[0] * rad2deg;

        //Update only if orientation really changed, compass fragment can provide last known location in this case
        if (m_CompassFragment != null && m_CompassFragment.setOrientation(m_GPSTracker, CSession.getInstance().getDestinationCoordinates(), orientation))
        {
            m_TargetInfosFragment.updateDistanceText(m_CompassFragment.getLastKnownLocation(), CSession.getInstance().getDestinationCoordinates());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    /**
     * Click on destination choice
     * @param view
     */
    public void destChoiceButtonOnclick(View view)
    {
        switchActivity(DestinationChoiceActivity.class);
    }
}
