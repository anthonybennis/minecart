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
	private boolean _updateFrame = false;
	private Type _type;
	private Rectangle _bounds;
	
	/**
	 * Constructor.
	 * 
	 * @param layer
	 * @param imageLoader
	 */
	public BasicSprite(Layers layer, ImageLoader imageLoader,Type type)
	{
		_layer = layer;
		_imageLoader = imageLoader;
		_type = type;
		this.createBounds();
	}
	
	protected ImageLoader getImageLoader()
	{
		return _imageLoader;
	}
	
	public ImageElement[] getImageElements()
	{
		return _imageElements;
	}
	
	public void setImageElements(ImageElement[] imageElements)
	{
		_imageElements = imageElements;
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
				if (_imageFrame >= this.getImageElements().length)
				{
					_imageFrame = 0; //Reset animation.
				}
				
				ImageElement currentFrame = this.getImageElements()[_imageFrame];
				canvas.getContext2d().drawImage(currentFrame, this.getLocation().x, this.getLocation().y);
				_updateFrame = !_updateFrame; // Update animation every second refresh.
				if (_updateFrame)
				{
					_imageFrame++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Generic ImageSequence method.
	 * 
	 * @param imageNames
	 * @return
	 */
	protected ImageElement[] createAnimationSequence(String[] imageNames)
	{
		ImageElement[] animationSequence = new ImageElement[imageNames.length];
		
		for (int i = 0; i < imageNames.length; i++) 
		{
			animationSequence[i] = this.getImageLoader().getImage(imageNames[i]);
		}
		
		return animationSequence;
	}
	
	/**
	 * Checks the basic sprites Image array.
	 * @return
	 */
	protected boolean haveAllImagesLoaded()
	{
		return this.haveAllImagesLoaded(_imageElements);
	}

	/**
	 * Allows you to check any image array to see if the images
	 * are loaded.
	 * @param images
	 * @return
	 */
	protected boolean haveAllImagesLoaded(ImageElement[] images)
	{
		boolean imagesHaveLoaded = true;
			
			if (images != null && images.length > 0)
			{
				for (ImageElement image: images) 
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
	
	@Override
	public Collision getCollisionType(ISprite sprite) 
	{
		Collision collision = Collision.NONE;
		
		Rectangle rectangle = this.getBounds();
		boolean collides = rectangle.intersects(sprite.getBounds());
		
		if (collides)
		{
			Vector thisSpritesCenter = rectangle.getCenter();
			Vector collidingSpritesCenter = sprite.getBounds().getCenter();
			
			if (thisSpritesCenter.x > collidingSpritesCenter.x
					&& thisSpritesCenter.y > collidingSpritesCenter.y)
			{
				
			}
			
			collision = Collision.RIGHT;
		}
		
		return collision;
	}
	
	@Override
	public Type getType() 
	{
		return _type;
	}
	
	private Rectangle createBounds()
	{
		_bounds = new Rectangle();
		_bounds = this.getBounds();
		
		return _bounds;
	}

	/**
	 * Calcualtes the current bounds of this object.
	 * @see com.bennis.minecart.client.engine.model.ISprite#getBounds()
	 */
	@Override
	public Rectangle getBounds() 
	{

		_bounds.setX(this.getLocation().x);
		_bounds.setY(this.getLocation().y);
		/*
		 * Optimised: Calculate Image width and height only once!
		 */
		if (_bounds.getWidth() <= 0 || _bounds.getHeight() <= 0)
		{
			ImageElement[] images = this.getImageElements();
			
			if (images != null && images.length > 0 && images[0] != null)
			{
				ImageElement element = images[0];
				_bounds.setWidth(element.getWidth());
				_bounds.setHeight(element.getHeight());
			}
		}
		
		return _bounds;
	}

	abstract protected String[] getImageNames();
}
