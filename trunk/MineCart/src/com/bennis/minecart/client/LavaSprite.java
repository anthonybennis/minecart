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
		super(layer, imageLoader, Type.ENEMY);
//		this.alignToGround();
		this.setLocation(GUIConstants.WIDTH,446);
	}

	@Override
	public void handleCollision(ISprite collisionSprite, Collision collisionType) 
	{
		// Nothing to do here.
	}

	@Override
	protected String[] getImageNames() 
	{
		String[] names = new String[30];
		
		names[0] = "images/lava/0001.png";
		names[1] = "images/lava/0002.png";
		names[2] = "images/lava/0003.png";
		names[3] = "images/lava/0004.png";
		names[4] = "images/lava/0005.png";
		names[5] = "images/lava/0006.png";
		names[6] = "images/lava/0007.png";
		names[7] = "images/lava/0008.png";
		names[8] = "images/lava/0009.png";
		names[9] = "images/lava/0010.png";
		names[10] = "images/lava/0011.png";
		names[11] = "images/lava/0012.png";
		names[12] = "images/lava/0013.png";
		names[13] = "images/lava/0014.png";
		names[14] = "images/lava/0015.png";
		names[15] = "images/lava/0016.png";
		names[16] = "images/lava/0017.png";
		names[17] = "images/lava/0018.png";
		names[18] = "images/lava/0019.png";
		names[19] = "images/lava/0020.png";
		names[20] = "images/lava/0021.png";
		names[21] = "images/lava/0022.png";
		names[22] = "images/lava/0023.png";
		names[23] = "images/lava/0024.png";
		names[24] = "images/lava/0025.png";
		names[25] = "images/lava/0026.png";
		names[26] = "images/lava/0027.png";
		names[27] = "images/lava/0028.png";
		names[28] = "images/lava/0029.png";
		names[29] = "images/lava/0030.png";
		
		return names;
	}
}