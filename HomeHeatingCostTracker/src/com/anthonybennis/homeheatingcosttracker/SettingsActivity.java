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
	 
//	 @Override
//	 public boolean onOptionsItemSelected(MenuItem item) 
//	 {
//		 /*
//		  * Allows the user to exit the SettingsActivity and Navigate back to
//		  * home by pressing the app icon on the ActionBar.
//		  */
//	     switch (item.getItemId()) 
//	     {
//		     // Respond to the action bar's Up/Home button
//		     case android.R.id.home:
//		     {
//		    	 navigateUpToFromChild(this, )
//		         return true;
//		     }
//	     }
//	     return super.onOptionsItemSelected(item);
//	 }
}
