package com.bernot.xavier.compassproject.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.bernot.xavier.compassproject.R;

/**
 * Created by Xavier on 21/05/2015.
 * Instructuions activity
 */
public class HelpActivity extends CActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar)findViewById(R.id.helpToolbar);
        toolbar.setTitle(R.string.help_title);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /**
     * Click on OK button
     * @param view
     */
    public void onButtonOkClick(View view)
    {
        finish();
    }

}
