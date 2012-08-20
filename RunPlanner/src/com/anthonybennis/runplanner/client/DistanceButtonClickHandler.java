package com.anthonybennis.runplanner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;

/**
 * 
 * @author Anthony
 */
public class DistanceButtonClickHandler implements ClickHandler 
{
	private DistanceButton[] _imageSet;
	
	/**
	 * 
	 * @param enabledImages
	 * @param disabledImages
	 */
	public DistanceButtonClickHandler(DistanceButton[] imageSet)
	{
		_imageSet = imageSet;
	}
	
	@Override
	public void onClick(ClickEvent event) 
	{
		Image clickedImage = (Image)event.getSource();
		
		for (DistanceButton wrapper: _imageSet) 
		{
			if (clickedImage == wrapper.getImage())
			{
				wrapper.enable();
			}
			else
			{
				wrapper.disable();
			}
		}	
	}
}
