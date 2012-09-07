package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.DistancePanelManager.DISTANCE;
import com.anthonybennis.runplanner.client.DistancePanelManager.DISTANCE_UNIT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class DistanceButton 
{
	private Image _image;
	private DISTANCE_UNIT _unit;
	private DISTANCE _value;
	private ImageResource _enabledIR;
	private ImageResource _disabledIR;
	
	public DistanceButton(DISTANCE_UNIT unit, DISTANCE value, ImageResource enabledIR, ImageResource disabledIR) 
	{
		_unit = unit;
		_enabledIR = enabledIR;
		_disabledIR = disabledIR;
		_image = new Image(_enabledIR);
		_value = value;
	}
	
	protected Image getImage() 
	{
		return _image;
	}

	protected void setImage(Image image) 
	{
		this._image = image;
	}

	protected DISTANCE_UNIT getUnit() 
	{
		return _unit;
	}

	protected void setUnit(DISTANCE_UNIT unit) 
	{
		this._unit = unit;
	}

	protected ImageResource getEnabledIR() 
	{
		return _enabledIR;
	}

	protected void setEnabledURL(ImageResource enabledIR) 
	{
		this._enabledIR = enabledIR;
	}

	protected ImageResource getDisabledIR() 
	{
		return _disabledIR;
	}

	protected void setDisabled(ImageResource disabledIR) 
	{
		this._disabledIR = disabledIR;
	}
	
	public void enable()
	{
		this.getImage().setResource(this.getEnabledIR());
	}
	
	public void disable()
	{
		this.getImage().setResource(this.getDisabledIR());
	}
	
	public DISTANCE getDistance()
	{
		return _value;
	}
}
