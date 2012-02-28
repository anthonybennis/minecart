package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;

/**
 * Basic implementation of common ISpite functionality.
 * @author abennis
 *
 */
abstract public class BasicSprite implements ISprite 
{
	private Vector _location = new Vector();
	private boolean _selected = false;
	private boolean _disposed = false;
	private Layers _layer;
	private ImageLoader _imageLoader;
	private ImageElement _imageElement;
	
	/**
	 * Constructor.
	 * 
	 * @param layer
	 * @param imageLoader
	 */
	public BasicSprite(Layers layer, ImageLoader imageLoader)
	{
		_layer = layer;
		_imageLoader = imageLoader;
	}
	
	protected ImageLoader getImageLoader()
	{
		return _imageLoader;
	}
	
	/*
	 * TODO AB Handle animations.
	 */
	public ImageElement getImageElement()
	{
		return _imageElement;
	}

	@Override
	public Vector getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Vector vector) 
	{
		_location = vector;
	}
	
	public void setLocation(double x, double y) 
	{
		_location.x = x;
		_location.y = y;
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
	
	
	
	@Override
	public void dispose() 
	{
		_disposed = true;	
	}

	@Override
	public Layers getLayer() 
	{
		return _layer;
	}
	
	protected ImageElement loadImage()
	{
		String imageName = this.getImageName();
		return this.getImageLoader().getImage(imageName);
	}
	
	@Override
	public void draw(Canvas canvas) 
	{		
		try
		{	
			/*
			 * Ensure image is loaded before painting anything
			 */
			if (this.getImageElement() == null) 
			{
				_imageElement = this.loadImage();
			}
			else // Draw Sprite
			{
				canvas.getContext2d().save();
				canvas.getContext2d().drawImage(this.getImageElement(), this.getLocation().x, this.getLocation().y);	
				canvas.getContext2d().restore();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	abstract protected String getImageName();
}
