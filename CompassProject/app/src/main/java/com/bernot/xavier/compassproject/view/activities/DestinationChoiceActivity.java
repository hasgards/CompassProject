package com.bernot.xavier.compassproject.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;

import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.model.CSession;
import com.bernot.xavier.compassproject.R;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Xavier on 20/05/2015.
 * Destination choice activity
 */
public class DestinationChoiceActivity extends CActivity
{
    private EditText m_LatEditText;
    private EditText m_LonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_choice);

        //Toolbar standard
        Toolbar toolbar = (Toolbar)findViewById(R.id.compassToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.destchoice_title);

        m_LatEditText = (EditText)findViewById(R.id.latitudeEditText);
        m_LonEditText = (EditText)findViewById(R.id.longitudeEditText);

        registerEditText(m_LatEditText);
        registerEditText(m_LonEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

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
     * @param view
     */
    public void okOnClick(View view)
    {
        CGeoCoordinates coordinates = new CGeoCoordinates();

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

        CSession.getInstance().setDestinationCoordinates(coordinates);
        finish();
    }

    /**
     * Click on cancel button
     * @param view
     */
    public void cancelOnClick(View view)
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

}
