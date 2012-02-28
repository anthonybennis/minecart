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
	public String getImageTileName() 
	{
		return "BackgroundTile01.jpg";
	}
}