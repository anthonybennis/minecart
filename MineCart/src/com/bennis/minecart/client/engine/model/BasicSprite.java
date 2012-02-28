package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.touch.client.Point;

/**
 * Basic implementation of common ISpite functionality.
 * @author abennis
 *
 */
abstract public class BasicSprite implements ISprite 
{
	private Point _location = new Point();
	private boolean _selected = false;
	private boolean _disposed = false;
	private Layers _layer;
	private ImageLoader _imageLoader;
	
	public BasicSprite(Layers layer, ImageLoader imageLoader)
	{
		_layer = layer;
		_imageLoader = imageLoader;
	}
	
	protected ImageLoader getImageLoader()
	{
		return _imageLoader;
	}

	@Override
	public Point getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Point point) 
	{
		_location = point;
	}

	@Override
	public boolean isSelected() 
	{		
		return _selected;
	}
	
	@Override
	public boolean isDisposed() 
	{
		return _disposed;
	}
	
	protected void setDisposed(boolean isDisposed)
	{
		_disposed = isDisposed;
	}
	
	@Override
	public Layers getLayer() 
	{
		return _layer;
	}
}
