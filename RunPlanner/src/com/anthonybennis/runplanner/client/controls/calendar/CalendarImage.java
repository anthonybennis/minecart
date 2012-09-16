package com.anthonybennis.runplanner.client.controls.calendar;

import com.anthonybennis.runplanner.client.Resources;
import com.google.gwt.user.client.ui.Image;

/**
 * 
 * @author Anthony
 *
 */
public class CalendarImage 
{
	/**
	 * 
	 * @return
	 */
	protected static Image createFastPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getFastRunImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	protected static Image createSlowPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getWalkImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	protected static Image createComfortablePACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getRunImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	protected static Image createMixPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getMixImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	protected static Image createRestPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
}
