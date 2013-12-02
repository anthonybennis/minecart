package com.anthonybennis.homeheatingcosttracker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 
 * @author abennis
 */
public class SettingsFragment extends PreferenceFragment // implements OnSharedPreferenceChangeListener
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

//	/**
//	 * onSharedPreferenceChanged
//	 */
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
//	{
//	    Preference pref = findPreference(key);
//	    
//	    if (pref instanceof ListPreference)
//	    {
//	        ListPreference listPref = (ListPreference) pref;
//	        pref.setSummary(listPref.getEntry());
//	    }
//	    else if (pref instanceof EditTextPreference)
//	    {
//	    	EditTextPreference editTextPref = (EditTextPreference)pref;
//	    	editTextPref.setSummary(editTextPref.getText());
//	    }
//	    
//	}
}
