package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.ScrollingSprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;

/**
 * Simple coin sprite that scrolls across the screen
 * and adds to the game points.
 * 
 * @author abennis
 */
public class CoinSprite extends ScrollingSprite 
{
	public CoinSprite(Layers layer, ImageLoader imageLoader)
	{
		super(layer, imageLoader);
		this.setLocation(GUIConstants.WIDTH, 50);
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

	@Override
	protected String getImageName() 
	{
		/*
		 * TODO AB Move images into images folder.
		 */
		return "SpinningCoin.gif";
	}
}
