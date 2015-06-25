package com.bernot.xavier.compassproject.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bernot.xavier.compassproject.Enums.ELengthUnit;
import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.tools.CApplicationSettings;

import static java.lang.String.format;

/**
 * Created by Xavier on 24/05/2015.
 * fragment that contains target information : distance, position, current position
 */
public class TargetInformationsFragment extends Fragment
{
    private final static String ACCURACY_100_FORMAT = "%1.1f";
    private final static String ACCURACY_10_FORMAT = "%1.2f";
    private final static String ACCURACY_1_FORMAT = "%1.3f";

    private TextView m_DistanceTextView;
    private TextView m_TargetNameTextView;
    private TextView m_PositionTextView;
    private TextView m_TargetTextView;

    private static final float MILE_KM_RATIO = 1.609344f;

    private float m_PreviousDistance;
    private float m_PreviousAccuracy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_target_infos, container, false);

        m_TargetTextView = (TextView)view.findViewById(R.id.targetTextView);
        m_TargetNameTextView = (TextView)view.findViewById(R.id.targetNameTextView);
        m_DistanceTextView = (TextView)view.findViewById(R.id.distanceTextView);
        m_PositionTextView = (TextView)view.findViewById(R.id.positionTextView);

        m_DistanceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchUnit(view.getContext());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onResume();
    }

    /**
     * Updates the target textview using CGeoCoordinates provided
     * @param pDestination destination coordinates
     */
    public void updateTargetTextView(CGeoCoordinates pDestination)
    {
        if(m_TargetTextView != null)
        {
            m_TargetTextView.setText(format(getString(R.string.compass_coords),
                    pDestination.getLatitude(),
                    pDestination.getLongitude()));
        }
        if(m_TargetNameTextView != null)
        {
            m_TargetNameTextView.setText(format(getString(R.string.compass_to),
                    pDestination.getName()));
        }
    }

    /**
     * Updates the distances and position texts
     * @param pCurrentLocation current position
     * @param pDestination destionation positionq
     */
    public void updateDistanceText(CGeoCoordinates pCurrentLocation, CGeoCoordinates pDestination)
    {
        if(pCurrentLocation != null && pDestination != null)
        {
            m_PreviousDistance = pCurrentLocation.distanceTo(pDestination) / 1000;
            m_PreviousAccuracy = pCurrentLocation.getAccuracy();

            updateDistanceText(m_PreviousDistance, m_PreviousAccuracy);

            if (m_PositionTextView != null)
            {
                m_PositionTextView.setText(format(getString(R.string.compass_coords),
                        pCurrentLocation.getLatitude(),
                        pCurrentLocation.getLongitude()));
            }
        }
        else
        {
            if (m_DistanceTextView != null)
            {
                m_DistanceTextView.setText(getString(R.string.compass_distance_default));
            }
            if (m_PositionTextView != null)
            {
                m_PositionTextView.setText(getString(R.string.compass_search));
            }
        }
    }

    /**
     * update the distance from current location to CGeoCoordinates provided
     * @param pDistanceInKm distance in KM
     */
    private void updateDistanceText(float pDistanceInKm, float pAccuracy)
    {
        float distance = pDistanceInKm;
        if(CApplicationSettings.getInstance().getUnit() == ELengthUnit.MILE)
        {
            pAccuracy = pAccuracy / MILE_KM_RATIO;
            distance = pDistanceInKm / MILE_KM_RATIO;
        }

        String distanceFormat;
        if(pAccuracy > 200)
        {
            distanceFormat = ACCURACY_100_FORMAT;
        }
        else if(pAccuracy > 20)
        {
            distanceFormat = ACCURACY_10_FORMAT;
        }
        else
        {
            distanceFormat = ACCURACY_1_FORMAT;
        }

        if (m_DistanceTextView != null)
        {
            if(CApplicationSettings.getInstance().getUnit() == ELengthUnit.KILOMETER)
            {
                m_DistanceTextView.setText(format(getString(R.string.compass_distance_km), format( distanceFormat,distance)));
            }
            else
            {
                m_DistanceTextView.setText(format(getString(R.string.compass_distance_mi), format(distanceFormat,distance)));
            }
        }

    }


    /**
     * Switches the distance unit
     * @param pContext : Context of application
     */
     public void switchUnit(Context pContext){

        if(CApplicationSettings.getInstance().getUnit() == ELengthUnit.KILOMETER)
        {
            CApplicationSettings.getInstance().setUnit(ELengthUnit.MILE, pContext);
        }
        else
        {
            CApplicationSettings.getInstance().setUnit(ELengthUnit.KILOMETER, pContext);
        }

        updateDistanceText(m_PreviousDistance, m_PreviousAccuracy);
    }
}
