package com.bernot.xavier.compassproject.view.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.support.v7.widget.RecyclerView;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.model.CSession;
import com.bernot.xavier.compassproject.tools.CApplicationSettings;
import com.bernot.xavier.compassproject.view.adapters.DestinationListAdapter;

import static java.lang.String.format;

/**
 * Created by Xavier on 16/06/2015.
 */
public class DestinationsListActivity extends CActivity
{
    private RecyclerView m_CoordRecyclerView;
    private Toolbar m_Toolbar;

    private CGeoCoordinates m_CoordinatesSelected;
    private int m_PositionCoordinatesSelected = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations_list);

        m_Toolbar = (Toolbar)findViewById(R.id.destListToolbar);
        m_Toolbar.setTitle(R.string.destList_title);
        setSupportActionBar(m_Toolbar);

        m_CoordRecyclerView = (RecyclerView)findViewById(R.id.coordinatesRecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_CoordRecyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(m_CoordRecyclerView);

        DestinationListAdapter coordAdapter = new DestinationListAdapter(this, CApplicationSettings.getInstance().getDestinationList());

        coordAdapter.setOnClickListener(new DestinationListAdapter.onClickListener() {
            @Override
            public void callback(View view, int position, CGeoCoordinates item) {
                CSession.getInstance().setDestinationCoordinates(item);
                finish();
            }
        });

        coordAdapter.setOnlongClickListener(new DestinationListAdapter.onLongClickListener() {
            @Override
            public void callback(View view, int position, CGeoCoordinates item) {

                m_CoordinatesSelected = item;
                m_PositionCoordinatesSelected = position;
            }
        });

        m_CoordRecyclerView.setAdapter(coordAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        m_CoordRecyclerView.getAdapter().notifyDataSetChanged();
        CApplicationSettings.saveSettings(this);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //edit
        if(item.getItemId() == R.id.action_edit)
        {
            CSession.getInstance().setCoordinatesToEditPosition(m_PositionCoordinatesSelected);
            switchActivity(DestinationChoiceActivity.class);
        }
        //delete
        else if(item.getItemId() == R.id.action_delete)
        {
            CApplicationSettings.getInstance().getDestinationList().remove(m_PositionCoordinatesSelected);
            m_CoordRecyclerView.getAdapter().notifyDataSetChanged();
            CApplicationSettings.saveSettings(this);
        }
        //move up
        else if(item.getItemId() == R.id.action_move_up)
        {
            if(m_PositionCoordinatesSelected > 0)
            {
                CApplicationSettings.getInstance().getDestinationList().remove(m_PositionCoordinatesSelected);
                CApplicationSettings.getInstance().getDestinationList().set(m_PositionCoordinatesSelected - 1, m_CoordinatesSelected);
                m_CoordRecyclerView.getAdapter().notifyDataSetChanged();
                CApplicationSettings.saveSettings(this);
            }
        }
        //move down
        else if(item.getItemId() == R.id.action_move_down)
        {
            if(m_PositionCoordinatesSelected < CApplicationSettings.getInstance().getDestinationList().size() - 1)
            {
                CApplicationSettings.getInstance().getDestinationList().remove(m_PositionCoordinatesSelected);
                CApplicationSettings.getInstance().getDestinationList().set(m_PositionCoordinatesSelected, m_CoordinatesSelected);
                m_CoordRecyclerView.getAdapter().notifyDataSetChanged();
                CApplicationSettings.saveSettings(this);
            }
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dest_list_item, menu);
    }

    /**
     * Click on "+" button
     * @param view
     */
    public void addDestButtonOnclick(View view)
    {
        switchActivity(DestinationChoiceActivity.class);
    }
}
