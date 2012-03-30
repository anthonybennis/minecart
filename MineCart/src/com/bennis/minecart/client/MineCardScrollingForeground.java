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

public class MineCardScrollingForeground extends ScrollingBackground 
{
	public MineCardScrollingForeground(ImageLoader imageLoader)
	{
		super(imageLoader);
	}

	@Override
	protected String[] getImageNames() 
	{
		String[] imageNames = new String[1];
		imageNames[0] = "images/Foreground01.jpg";
		return imageNames;
	}

	@Override
	public int getScrollingSpeed() 
	{
		return GUIConstants.MEDIUM_SCROLL_SPEED;
	}

	@Override
	public int getStartingYPosition() 
	{
		return 502;
	}
}
