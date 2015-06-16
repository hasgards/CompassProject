package com.bernot.xavier.compassproject.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;

import static java.lang.String.format;

/**
 * Created by Xavier on 24/05/2015.
 * fragment that contains target information : distance, position, current position
 */
public class TargetInformationsFragment extends Fragment
{

    private TextView m_TargetTextView;
    private TextView m_DistanceTextView;
    private TextView m_PositionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_target_infos, container, false);

        m_TargetTextView = (TextView)view.findViewById(R.id.targetTextView);
        m_DistanceTextView = (TextView)view.findViewById(R.id.distanceTextView);
        m_PositionTextView = (TextView)view.findViewById(R.id.positionTextView);

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
    }

    /**
     * update the distance from current location to CGeoCoordinates provided
     * @param pCurrentLocation current location
     * @param pDestination destination location
     */
    public void updateDistanceText(CGeoCoordinates pCurrentLocation, CGeoCoordinates pDestination)
    {
        if(pCurrentLocation != null)
        {
            float distance = pCurrentLocation.distanceTo(pDestination) / 1000;

            if (m_DistanceTextView != null)
            {
                m_DistanceTextView.setText(format(getString(R.string.compass_distance), distance));
            }
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
}
