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
	public static final String COST_PER_LITRE = "costperlitre";
	public static final String BER = "houseinsulation";
	public static final String CURRENCY_SYMBOL = "currency";
	
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
		String preference = preferences.getString(preferenceKey, ""); // Default String is empty.
		
		return preference;
	}
	
	/**
	 * 
	 * @param preferenceKey
	 * @param context
	 * @return
	 */
	public static long loadPreferenceAsLong(String preferenceKey, Context context)
	{
    	long longPreference = -1;
    	
    	String preferenceAsString = PreferencesUtil.loadPreference(preferenceKey, context);
    	if (!preferenceAsString.equals(""))
    	{
    		longPreference = Long.parseLong(preferenceAsString);
    	}
    	
    	return longPreference;
	}
	
	/**
	 * 
	 * @param preferenceKey
	 * @param context
	 * @return
	 */
	public static double loadPreferenceAsDoubleg(String preferenceKey, Context context)
	{
    	double doublePreference = -1;
    	
    	String preferenceAsString = PreferencesUtil.loadPreference(preferenceKey, context);
    	if (!preferenceAsString.equals(""))
    	{
    		doublePreference = Double.parseDouble(preferenceAsString);
    	}
    	
    	return doublePreference;
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