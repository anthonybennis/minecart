package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.Layer.Layers;

/**
 * A sprite that scrolls across the scene.
 * Like the coins in Mario, or the rings in Sonic.
 * 
 * @author abennis
 */
abstract public class ScrollingSprite extends BasicSprite
{
	private int _scrollSpeed = GUIConstants.MEDIUM_SCROLL_SPEED; //Default
	/**
	 * Contructor
	 * @param layer
	 * @param imageLoader
	 */
	public ScrollingSprite(Layers layer, ImageLoader imageLoader, Type type, double yStartingLocation)
	{
		super(layer,imageLoader,type);
		this.setLocation(GUIConstants.WIDTH, yStartingLocation);
	}
	
	public ScrollingSprite(Layers layer, ImageLoader imageLoader, Type type)
	{
		super(layer,imageLoader,type);
	}

	@Override
	public void update(InputEvent event) 
	{
		if (!this.isDisposed() && this.haveAllImagesLoaded())
		{
			double x = this.getLocation().x;
			x = x - _scrollSpeed;
			
			/*
			 * Dispose Sprite if it's left the screen.
			 */
			if (x < (0 - this.getImageElements()[0].getWidth()))
			{
				this.dispose();
			}
			
			this.setLocation(x, this.getLocation().y);
		}
	}
	
	/**
	 * Change the speed of this sprite as it
	 * scrolls from left to right.
	 * 
	 * No need to set, as it defaults to <code>GUIConstants.MEDIUM_SCROLL_SPEED;</code>
	 * 
	 * @param speed
	 */
	public void setScrollSpeed(int speed)
	{
		_scrollSpeed = speed;
	}
	
	protected void alignToGround()
	{
		double height = this.getBounds().getHeight();
		this.getLocation().y = (GUIConstants.PERMANENT_PLATFORM_HEIGHT);// - height;
	}
}