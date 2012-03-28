package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.ScrollingSprite;

/**
 * Bubble of lave that erupts from the ground.
 * 
 * @author Anthony
 *
 */
public class LavaSprite extends ScrollingSprite 
{
	/**
	 * Enemy Sprite.
	 * 
	 * @param layer
	 * @param imageLoader
	 */
	public LavaSprite(Layers layer, ImageLoader imageLoader)
	{
		super(layer, imageLoader, Type.ENEMY, 372);
	}

	@Override
	public void handleCollision(ISprite collisionSprite, Collision collisionType) 
	{
		// Nothing to do here.
	}

	@Override
	protected String[] getImageNames() 
	{
		String[] names = new String[1];
		names[0] = "images/enemies/AnimatedLava.gif";
		return names;
	}
}