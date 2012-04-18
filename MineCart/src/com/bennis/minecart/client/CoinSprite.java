/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.ScrollingSprite;

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
		super(layer, imageLoader, Type.GOODIE, 350);
		
	}

	@Override
	public void handleCollision(ISprite collisionSprite,Collision collisionType) 
	{
		// Nothing to do here. All collision handled by MineSprite
	}

	@Override
	protected String[] getImageNames() 
	{
		String[] names = new String[17];
		names[0] = "images/coin/0001.png";
		names[1] = "images/coin/0002.png";
		names[2] = "images/coin/0003.png";
		names[3] = "images/coin/0004.png";
		names[4] = "images/coin/0005.png";
		names[5] = "images/coin/0006.png";
		names[6] = "images/coin/0007.png";
		names[7] = "images/coin/0008.png";
		names[8] = "images/coin/0009.png";
		names[9] = "images/coin/0010.png";
		names[10] = "images/coin/0011.png";
		names[11] = "images/coin/0012.png";
		names[12] = "images/coin/0013.png";
		names[13] = "images/coin/0014.png";
		names[14] = "images/coin/0015.png";
		names[15] = "images/coin/0016.png";
		names[16] = "images/coin/0017.png";
		

		return names;
	}
}
