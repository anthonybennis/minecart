package com.bennis.minecart.client.engine.model;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;

/**
 * A sprite that scrolls across the scene.
 * Like the coins in Mario, or the rings in Sonic.
 * 
 * @author abennis
 */
public class ScrollingSprite extends BasicSprite
{
	public ScrollingSprite(Layers layer, ImageLoader imageLoader)
	{
		super(layer,imageLoader);
	}

	@Override
	public void draw(Canvas canvas) 
	{
		// TODO AB
	}

	@Override
	public void update() 
	{
		// TODO AB
	}

	@Override
	public void handleCollision(ISprite collisionSprite) 
	{
		// TODO AB
	}

	@Override
	public void dispose() 
	{
		// TODO AB
	}
}