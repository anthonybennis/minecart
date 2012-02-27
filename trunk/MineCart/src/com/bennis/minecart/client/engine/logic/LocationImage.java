package com.bennis.minecart.client.engine.logic;

import com.bennis.minecart.client.engine.model.Vector;
import com.google.gwt.dom.client.ImageElement;


/**
 * GWT Image wrapper.
 * Gives the image class some location capabilities.
 * @author Anthony
 *
 */
public class LocationImage 
{
	private ImageElement _gwtImage;
	private Vector _location;
	/**
	 * Image wrapper
	 * @param gwtImage
	 */
	public LocationImage(ImageElement gwtImage)
	{
		_gwtImage = gwtImage;
		_location = new Vector();
	}
	
	public ImageElement getImage()
	{
		return _gwtImage;
	}
	
	public Vector getLocation()
	{
		return _location;
	}
	
	public int getX()
	{
		return (int)_location.x;
	}
	
	public int getY()
	{
		return (int)_location.y;
	}
	
	public void setLocation(Vector location)
	{
		_location = location;
	}
	
	public void setLocation(int x, int y)
	{
		_location.x = x;
		_location.y = y;
	}
	
	public void setX(int x)
	{
		_location.x = x;
	}
	
	public void setY(int y)
	{
		_location.y = y;
	}
}
