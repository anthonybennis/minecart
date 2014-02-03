package com.anthonybennis.homeheatingcosttracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Utility for easily working with Utilities.
 * @author abennis
 */
public class PreferencesUtil 
{
	public static final String START_TIME = "StartTime";
	
	/**
	 * Util method for loading Preferences.
	 * 
	 * @param preferenceKey
	 * @return
	 */
	public static String loadPreference(String preferenceKey, Context context)
	{
		Activity activity = (Activity)context;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		boolean vontains = preferences.contains("housesize");
		String preference = preferences.getString(preferenceKey, ""); // Default String is empty.
		
		return preference;
	}
	
	/**
	 * Util method for saving Preferences.
	 * 
	 * @param preferenceKey
	 * @param preferenceValue
	 * @param activity
	 */
	public static void savePreference(String preferenceKey, String preferenceValue, Context context)
	{
		Activity activity = (Activity)context;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor edit = preferences.edit();
		edit.putString(preferenceKey, preferenceValue);
		edit.apply(); 
	}
}