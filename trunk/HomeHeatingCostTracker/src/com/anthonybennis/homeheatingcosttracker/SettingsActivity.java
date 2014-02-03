package com.anthonybennis.homeheatingcosttracker;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author abennis
 */
public class SettingsActivity extends Activity
{
	
	 @Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	      super.onCreate(savedInstanceState);
	      this.getActionBar().setDisplayHomeAsUpEnabled(true);
	      getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	 }
}
