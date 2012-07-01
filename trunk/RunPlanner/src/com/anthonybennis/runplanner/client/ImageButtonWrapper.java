package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.DistancePanelManager.DISTANCE;
import com.anthonybennis.runplanner.client.DistancePanelManager.DISTANCE_UNIT;
import com.google.gwt.user.client.ui.Image;

public class ImageButtonWrapper 
{
	private Image _image;
	private DISTANCE_UNIT _unit;
	private DISTANCE _value;
	private String _enabledURL;
	private String _disabledURL;
	
	public ImageButtonWrapper(DISTANCE_UNIT unit, DISTANCE value, String enabledURL, String disabledURL) 
	{
		_unit = unit;
		_enabledURL = enabledURL;
		_disabledURL = disabledURL;
		_image = new Image(_disabledURL); // TODO AB Load on start up (Use Resourced)
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

	protected String getEnabledURL() 
	{
		return _enabledURL;
	}

	protected void setEnabledURL(String enabledURL) 
	{
		this._enabledURL = enabledURL;
	}

	protected String getDisabledURL() 
	{
		return _disabledURL;
	}

	protected void setDisabled(String disabledURL) 
	{
		this._disabledURL = disabledURL;
	}
	
	public void enable()
	{
		this.getImage().setUrl(this.getEnabledURL());
	}
	
	public void disable()
	{
		this.getImage().setUrl(this.getDisabledURL());
	}
	
	public DISTANCE getDistance()
	{
		return _value;
	}
}
