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
	/**
	 * Contructor
	 * @param layer
	 * @param imageLoader
	 */
	public ScrollingSprite(Layers layer, ImageLoader imageLoader, Type type)
	{
		super(layer,imageLoader,type);
	}

	@Override
	public void update(InputEvent event) 
	{
		/*
		 * TODO AB
		 * X position should be managed by one ScrollingController, that
		 * updates all speeds. 
		 * This way, we can implement automatic scrolling, as in MineCart,
		 * Or scrolling based on Sprites position, user movements.
		 */
		if (!this.isDisposed() && this.haveAllImagesLoaded())
		{
			double x = this.getLocation().x;
			// TODO AB - Make speed setable. Faster when levels progress.
			x = x - GUIConstants.MEDIUM_SCROLL_SPEED;
			
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
}