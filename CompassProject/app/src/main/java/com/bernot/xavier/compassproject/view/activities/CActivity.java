package com.bernot.xavier.compassproject.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bernot.xavier.compassproject.R;

/**
 * Created by Xavier on 20/05/2015.
 * Base activity that contains handly functions and inflates a default menu
 */
public class CActivity extends ActionBarActivity
{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //The menu_main is the most used, so it is choosen by default
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            switchActivity(AboutActivity.class);
            return true;
        }
        else if(id == R.id.action_instructions)
        {
            switchActivity(HelpActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * permit to change activity quickly
     * @param pClass Activity class to start
     */
    public void switchActivity(Class<?> pClass)
    {
        Intent i = new Intent(getApplicationContext(), pClass);
        startActivity(i);
    }

    /**
     * Show popup with ok button
     * @param pTitre title of the message
     * @param pMessage message
     */
    protected void raiseAlertMessage(String pTitre, String pMessage)
    {
        new AlertDialog.Builder(this)
                .setTitle(pTitre)
                .setMessage(pMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
