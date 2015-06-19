package com.bernot.xavier.compassproject.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;

import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.model.CSession;
import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.tools.CApplicationSettings;
import com.bernot.xavier.compassproject.tools.CGPSTracker;
import com.bernot.xavier.compassproject.view.fragments.RotatingCompassFragment;
import com.bernot.xavier.compassproject.view.fragments.TargetInformationsFragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static java.lang.String.format;

/**
 * Created by Xavier on 20/05/2015.
 * Destination choice activity
 */
public class DestinationChoiceActivity extends CActivity
{
    private EditText m_NameEditText;
    private EditText m_LatEditText;
    private EditText m_LonEditText;

    private int m_IndexToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_choice);

        //Toolbar standard
        Toolbar toolbar = (Toolbar)findViewById(R.id.compassToolbar);
        setSupportActionBar(toolbar);

        m_NameEditText = (EditText)findViewById(R.id.nameEditText);
        m_LatEditText = (EditText)findViewById(R.id.latitudeEditText);
        m_LonEditText = (EditText)findViewById(R.id.longitudeEditText);

        registerEditText(m_NameEditText);
        registerEditText(m_LatEditText);
        registerEditText(m_LonEditText);

        m_IndexToEdit = CSession.getInstance().getCoordinatesToEditPosition();
        if(m_IndexToEdit >= 0)
        {
            toolbar.setTitle(format(getString(R.string.destchoice_title), getString(R.string.destchoice_edit)));

            CSession.getInstance().setCoordinatesToEditPosition(-1);

            CGeoCoordinates coordsToEdit = CApplicationSettings.getInstance().getDestinationList().get(m_IndexToEdit);

            m_NameEditText.setText(coordsToEdit.getName());
            m_LatEditText.setText(String.valueOf(coordsToEdit.getLatitude()));
            m_LonEditText.setText(String.valueOf(coordsToEdit.getLongitude()));
        }
        else
        {
            toolbar.setTitle(format(getString(R.string.destchoice_title), getString(R.string.destchoice_new)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_dest_choice, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok)
        {
            okOnClick();
            return true;
        }
        else if(id == R.id.action_cancel)
        {
            cancelOnClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Register location choice EditTexts
     * @param pEditText EditText to register
     */
    public void registerEditText(final EditText pEditText) {

        pEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                pEditText.setError(null);
            }
        });
    }

    /**
     * Click on OK button
     */
    public void okOnClick()
    {
        CGeoCoordinates coordinates = new CGeoCoordinates();

        if(!coordinates.setName(m_NameEditText.getText().toString()))
        {
            m_NameEditText.setError(getString(R.string.destchoice_error_name));
            return;
        }

        if(!coordinates.setLatitude(m_LatEditText.getText().toString()))
        {
            m_LatEditText.setError(getString(R.string.destchoice_error_lat));
            return;
        }

        if(!coordinates.setLongitude(m_LonEditText.getText().toString()))
        {
            m_LonEditText.setError(getString(R.string.destchoice_error_lon));
            return;
        }

        if(m_IndexToEdit >= 0)
        {
            CApplicationSettings.getInstance().getDestinationList().set(m_IndexToEdit, coordinates);
        }
        else
        {
            CApplicationSettings.getInstance().getDestinationList().add(0,coordinates);
        }

        //CSession.getInstance().setDestinationCoordinates(coordinates);
        finish();
    }

    /**
     * Click on cancel button
     */
    public void cancelOnClick()
    {
        finish();
    }

    /**
     * Click on poznan Ratusz coordinates
     * @param view
     */
    public void poznanPresetOnClick(View view) {

        m_LatEditText.setText(String.valueOf(CGeoCoordinates.RATUSZ_POZNAN.getLatitude()));
        m_LonEditText.setText(String.valueOf(CGeoCoordinates.RATUSZ_POZNAN.getLongitude()));
    }

    /**
     * Click on strasbourg cathedral coordinates
     * @param view
     */
    public void strasbourgPresetOnClick(View view)
    {
        m_LatEditText.setText(String.valueOf(CGeoCoordinates.STRASBOURG_CATHEDRAL.getLatitude()));
        m_LonEditText.setText(String.valueOf(CGeoCoordinates.STRASBOURG_CATHEDRAL.getLongitude()));
    }

    public void currentPositionOnClick(View view)
    {
        CGPSTracker tracker = new CGPSTracker(DestinationChoiceActivity.this);

        //Check if GPS enabled
        if(!tracker.canGetLocation()) {
            raiseAlertMessage(getString(R.string.common_warning), getString(R.string.common_warning_geoloc));
            return;
        }

        m_LatEditText.setText(String.valueOf(tracker.getLatitude()));
        m_LonEditText.setText(String.valueOf(tracker.getLongitude()));

        tracker.stopUsingGPS();
    }
}
