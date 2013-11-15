package com.anthonybennis.homeheatingcosttracker;

import android.os.Handler;
import android.view.View;

/**
 * 
 * @author Anthony
 */
public class SecondUpdateRunnable implements Runnable 
{
	private static final int ONE_SECOND = 1000;
	
	private View _view;
	private Handler _handler;
	
	/**
	 * Constructor.
	 */
	public SecondUpdateRunnable()
	{
	}
	
	protected void setView(View view)
	{
		_view = view;
	}
	
	protected void setHandler(Handler handler)
	{
		_handler = handler;
	}
	
	@Override
	public void run() 
	{
		if (_view != null && _handler != null)
		{
			_view.invalidate();
			_handler.postDelayed(this, ONE_SECOND);
		}
	}
}
