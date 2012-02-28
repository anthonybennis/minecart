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
	private ImageElement[] _imageElements;
	private int _imageFrame = 0;
	
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
	
	public ImageElement[] getImageElements()
	{
		return _imageElements;
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
	
	/**
	 * Loads all images needed to animate this Sprite.
	 * @return
	 */
	protected ImageElement[] loadImages()
	{
		String[] imageNames = this.getImageNames();
		ImageElement[] images = new ImageElement[imageNames.length];
		
		for (int i = 0; i < images.length; i++) 
		{
			images[i] =  this.getImageLoader().getImage(imageNames[i]);
		}
		
		_imageElements = images;
		
		return images;
	}
	
	@Override
	public void draw(Canvas canvas) 
	{		
		try
		{	
			/*
			 * Ensure image is loaded before painting anything
			 */
			if (!this.haveAllImagesLoaded()) 
			{
				this.loadImages();
			}
			else // Draw Sprite
			{
				canvas.getContext2d().save();
				
				if (_imageFrame >= this.getImageElements().length)
				{
					_imageFrame = 0; //Reset animation.
				}
				
				ImageElement currentFrame = this.getImageElements()[_imageFrame];
				canvas.getContext2d().drawImage(currentFrame, this.getLocation().x, this.getLocation().y);
				_imageFrame++;
				
				
				canvas.getContext2d().restore();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean haveAllImagesLoaded()
	{
		boolean imagesHaveLoaded = true;
		
		if (_imageElements != null)
		{
			for (ImageElement image: _imageElements) 
			{
				if (image == null)
				{
					imagesHaveLoaded = false;
					break;
				}
			}
		}
		else
		{
			imagesHaveLoaded = false;
		}
		
		return imagesHaveLoaded;
	}
	
	abstract protected String[] getImageNames();
}
