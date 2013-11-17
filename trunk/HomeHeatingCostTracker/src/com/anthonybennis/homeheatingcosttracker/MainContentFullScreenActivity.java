package com.anthonybennis.homeheatingcosttracker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
	
	private final Handler _timerHandler = new Handler();
	private final SecondUpdateRunnable _runnable = new SecondUpdateRunnable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_content_full_screen);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		_contentView = (CostView)findViewById(R.id.fullscreen_content);
	
		/*
		 * Set up Runnable that updates every second.
		 * (Runnable will start when start button is pressed)
		 */
		_runnable.setView(_contentView);
		_runnable.setHandler(_timerHandler);
	
	}



	private CostView _contentView;

	/**
	 * Starts calculating
	 * @param view
	 */
	public void startCalculatingCosts(View view)
	{
		long startTime = System.currentTimeMillis();
		startTime= startTime - 1960000;
		_contentView.setStartTime(startTime);
		_timerHandler.postDelayed(_runnable, 1000);
	}
	
	/**
	 * Starts calculating
	 * @param view
	 */
	public void stopCalculatingCosts(View view)
	{
		_timerHandler.removeCallbacks(_runnable);
	}
}