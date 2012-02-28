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
}