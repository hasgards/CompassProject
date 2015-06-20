package com.bernot.xavier.compassproject.view.adapters;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bernot.xavier.compassproject.R;
import com.bernot.xavier.compassproject.model.CGeoCoordinates;
import com.bernot.xavier.compassproject.view.activities.DestinationsListActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Xavier on 16/06/2015.
 */

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener
{
    ArrayList<CGeoCoordinates> m_GeoCoordinatesList;
    private static Context sContext;

    private boolean m_IsInEditMode = false;

    private onClickListener m_OnClickListener;
    public void setOnClickListener(onClickListener pOnClickListener) {
        this.m_OnClickListener = pOnClickListener;
    }

    private onLongClickListener m_OnlongClickListener;
    public void setOnlongClickListener(onLongClickListener pOnlongClickListener) {
        this.m_OnlongClickListener = pOnlongClickListener;
    }

    // Adapter's Constructor
    public DestinationListAdapter(Context context, ArrayList<CGeoCoordinates> pGeoCoordinatesList) {
        m_GeoCoordinatesList = pGeoCoordinatesList;
        sContext = context;
    }

    // Create new views. This is invoked by the layout manager.
    @Override
    public DestinationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_destinations_list_item, parent, false);

        // Set the view to the ViewHolder
        ViewHolder holder = new ViewHolder(v);

        holder.m_RootView.setOnClickListener(DestinationListAdapter.this);
        holder.m_RootView.setOnLongClickListener(DestinationListAdapter.this);
        holder.m_RootView.setOnTouchListener(DestinationListAdapter.this);
        holder.m_RootView.setTag(holder);

        return holder;
    }

    // Replace the contents of a view. This is invoked by the layout manager.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CGeoCoordinates o = m_GeoCoordinatesList.get(position);
        if (o != null) {

            holder.m_TvShortName.setText(o.getShortName());
            holder.m_TvName.setText(String.valueOf(o.getName()));
            holder.m_TvCoordinates.setText(format(sContext.getString(R.string.compass_coords),
                    o.getLatitude(),
                    o.getLongitude()));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return m_GeoCoordinatesList.size();
    }

    // Implement OnClick listener.
    @Override
    public void onClick(View view) {

        if(m_IsInEditMode)
        {
            m_IsInEditMode = false;
            return;
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        if (this.m_OnClickListener != null)
        {
            this.m_OnClickListener.callback(view, holder.getAdapterPosition(), m_GeoCoordinatesList.get(holder.getAdapterPosition()));
        }
    }

    // Implement OnLongClick listener.
    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        m_IsInEditMode = true;

        if(this.m_OnlongClickListener != null)
        {
            this.m_OnlongClickListener.callback(view, holder.getAdapterPosition(), m_GeoCoordinatesList.get(holder.getAdapterPosition()));
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            TransitionDrawable transition = (TransitionDrawable) view.getBackground();
            transition.startTransition(500);

        } else if (motionEvent.getAction() == android.view.MotionEvent.ACTION_UP
                || motionEvent.getAction() == MotionEvent.ACTION_SCROLL
                || motionEvent.getAction() == MotionEvent.ACTION_HOVER_EXIT
                || motionEvent.getAction() == MotionEvent.ACTION_HOVER_MOVE
                || motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP
                || motionEvent.getAction() == MotionEvent.ACTION_CANCEL
                || motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
            TransitionDrawable transition = (TransitionDrawable) view.getBackground();
            transition.reverseTransition(200);
        }
        return false;
    }

    // Create the ViewHolder class to keep references to your views
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView m_TvShortName;
        TextView m_TvName;
        TextView m_TvCoordinates;
        View m_RootView;

        /**
         * Constructor
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            m_TvShortName = (TextView) v.findViewById(R.id.dest_short_name);
            m_TvName = (TextView) v.findViewById(R.id.dest_name);
            m_TvCoordinates = (TextView) v.findViewById(R.id.dest_coordinates);
            m_RootView = (View)v.findViewById(R.id.adapter_dest_list_item_root);
        }
    }

    public interface onClickListener {
        public void callback(View view, int position, CGeoCoordinates item);
    }

    public interface onLongClickListener {
        public void callback(View view, int position, CGeoCoordinates item);
    }
}

