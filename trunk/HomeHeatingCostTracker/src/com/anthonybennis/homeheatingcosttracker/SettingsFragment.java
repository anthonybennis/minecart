package com.anthonybennis.homeheatingcosttracker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 
 * @author abennis
 */
public class SettingsFragment extends PreferenceFragment 
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
