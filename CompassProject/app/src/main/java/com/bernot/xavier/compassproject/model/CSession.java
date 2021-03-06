package com.bernot.xavier.compassproject.model;

/**
 * Created by Xavier on 21/05/2015.
 * Sigleton that represent Current session, it can contain variables that are exchanged between activities
 */
public class CSession
{
    private static CSession ourInstance = new CSession();

    public static CSession getInstance() {
        return ourInstance;
    }

    private CSession()
    {
    }

    private CGeoCoordinates m_DestinationCoordinates;
    public CGeoCoordinates getDestinationCoordinates() {
        return m_DestinationCoordinates;
    }
    public void setDestinationCoordinates(CGeoCoordinates pDestinationCoordinates) {
        this.m_DestinationCoordinates = pDestinationCoordinates;
    }

    private int m_CoordinatesToEditPosition = -1;
    public int getCoordinatesToEditPosition() {
        return m_CoordinatesToEditPosition;
    }
    public void setCoordinatesToEditPosition(int pCoordinatesToEditPosition) {
        this.m_CoordinatesToEditPosition = pCoordinatesToEditPosition;
    }
}
