package com.anthonybennis.homeheatingcosttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.anthonybennis.homeheatingcosttracker.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainContentFullScreenActivity extends Activity 
{
	private CostView _contentView;
	private final Handler _timerHandler = new Handler();
	private final SecondUpdateRunnable _runnable = new SecondUpdateRunnable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		setContentView(R.layout.activity_main_content_full_screen);
		_contentView = (CostView)findViewById(R.id.fullscreen_content);
	
		/*
		 * Set up Runnable that updates every second.
		 * (Runnable will start when start button is pressed)
		 */
		_runnable.setView(_contentView);
		_runnable.setHandler(_timerHandler);
	
	}

	/**
	 * Starts calculating
	 * @param view
	 */
	public void startCalculatingCosts(View view)
	{
		long startTime = System.currentTimeMillis();
		PreferencesUtil.savePreference(PreferencesUtil.START_TIME, String.valueOf(startTime), this);
		
		_timerHandler.postDelayed(_runnable, 1000);
	}
	
	/**
	 * Starts calculating
	 * @param view
	 */
	public void stopCalculatingCosts(View view)
	{
		PreferencesUtil.savePreference(PreferencesUtil.START_TIME, "", this); // RESET Start Time
		_timerHandler.removeCallbacks(_runnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main_activity, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case R.id.action_settings:
			{
				this.openSettings();
				break;
			}
			case R.id.calc_action_settings:
			{
				this.openCalculator();
				break;
			}
			default:
			{
				// Do nothing if we don't know what menu item was clicked.
				break;
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 */
	private void openSettings()
	{
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 
	 */
	private void openCalculator()
	{
		Intent intent = new Intent(this, CalculatorActivity.class);
	    startActivity(intent);
	}

	@Override
	protected void onResume() 
	{
		_timerHandler.postDelayed(_runnable, 1000);
		super.onResume();
	}
	
	
}