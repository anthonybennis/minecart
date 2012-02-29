package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.Layer.Layers;

/**
 * A sprite that scrolls across the scene.
 * Like the coins in Mario, or the rings in Sonic.
 * 
 * @author abennis
 */
abstract public class ScrollingSprite extends BasicSprite
{
	// TODO AB - Make speed setable. Faster when levels progress.
	private static final int SCROLL_SPEED = 3;

	/**
	 * Contructor
	 * @param layer
	 * @param imageLoader
	 */
	public ScrollingSprite(Layers layer, ImageLoader imageLoader)
	{
		super(layer,imageLoader);
	}

	@Override
	public void update() 
	{
		if (!this.isDisposed() && this.haveAllImagesLoaded())
		{
			double x = this.getLocation().x;
			x = x - SCROLL_SPEED; // TODO AB Move Scroll speeds to GUIConstants class.
			
			/*
			 * Dispose Sprite if it's left the screen.
			 */
			if (x < (0 - this.getImageElements()[0].getWidth()))
			{
				/*
				 * TEMP. This creates an infinite loop of Sprites.
				 */
				x = GUIConstants.WIDTH + 10;
//				this.dispose();
			}
			
			this.setLocation(x, this.getLocation().y);
		}
	}
}