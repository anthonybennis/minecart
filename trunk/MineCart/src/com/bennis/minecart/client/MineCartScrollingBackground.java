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
import com.bennis.minecart.client.engine.model.ScrollingBackground;

/**
 * 
 * @author abennis
 */
public class MineCartScrollingBackground extends ScrollingBackground 
{
	public MineCartScrollingBackground(ImageLoader imageLoader)
	{
		super(imageLoader);
	}

	@Override
	public String[] getImageNames() 
	{
		String[] names = new String[1];
		names[0] = "images/BackgroundTile01.jpg"; 
		return names;
	}
	
	@Override
	public int getScrollingSpeed() 
	{
		return GUIConstants.SLOW_SCROLL_SPEED;
	}
	
	@Override
	public int getStartingYPosition() 
	{
		return 0;
	}
	
}