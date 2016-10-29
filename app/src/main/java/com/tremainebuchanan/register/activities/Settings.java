package com.tremainebuchanan.register.activities;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.tremainebuchanan.register.R;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        addPreferencesFromResource(R.xml.preferences);
    }
}
