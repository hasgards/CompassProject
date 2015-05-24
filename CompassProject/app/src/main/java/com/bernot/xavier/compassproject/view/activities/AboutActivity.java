package com.bernot.xavier.compassproject.view.activities;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.bernot.xavier.compassproject.R;

import static java.lang.String.format;

/**
 * Created by Xavier on 20/05/2015.
 * Simple activity that shows infos and version
 */
public class AboutActivity extends CActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        Toolbar toolbar = (Toolbar)findViewById(R.id.aboutToolbar);
        toolbar.setTitle(R.string.about_title);
        setSupportActionBar(toolbar);

        TextView aboutSubTitleTextView = (TextView)findViewById(R.id.aboutSubtitle);
        aboutSubTitleTextView.setText(format(getString(R.string.about_subtitle), getString(R.string.app_name)));

        TextView versionTextView = (TextView)findViewById(R.id.aboutVersionTextView);

        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            versionTextView.setText(format(getString(R.string.about_version), version));
        }
        catch(Exception e)
        {
            versionTextView.setText(getString(R.string.about_version_error));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //here we use the special about menu
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

}
