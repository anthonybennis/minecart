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
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;

/**
 * This enemy flies around the screen for a set amount of time.
 * Contact with the mine cart will take a life.
 * @author Anthony
 *
 */
public class BlimpSprite extends BasicSprite 
{
	/**
	 * Constructor
	 */
	public BlimpSprite(Layers layer, ImageLoader imageloader)
	{
		super(layer,imageloader, Type.ENEMY);
	}

	@Override
	public void update(InputEvent event) 
	{
		// TODO AB Update Blimp Sprite
	}

	@Override
	public void handleCollision(ISprite collisionSprite, Collision collisionType) 
	{
		// TODO AB Blimp Collision 
	}

	@Override
	protected String[] getImageNames() 
	{
		// TODO AB Get Blimp Image
		return null;
	}
}