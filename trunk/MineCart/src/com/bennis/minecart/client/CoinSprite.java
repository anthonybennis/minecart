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
		super(layer, imageLoader, Type.GOODIE);
		this.setLocation(GUIConstants.WIDTH, 350);
	}

	@Override
	public void handleCollision(ISprite collisionSprite) 
	{
		// TODO AB - if MineKart, Update Game Points and dispose
	}
	

	@Override
	protected String[] getImageNames() 
	{
		String[] names = new String[12];
		names[0] = "images/SpinningCoin01.gif";
		names[1] = "images/SpinningCoin02.gif";
		names[2] = "images/SpinningCoin03.gif";
		names[3] = "images/SpinningCoin04.gif";
		names[4] = "images/SpinningCoin05.gif";
		names[5] = "images/SpinningCoin06.gif";
		names[6] = "images/SpinningCoin07.gif";
		names[7] = "images/SpinningCoin08.gif";
		names[8] = "images/SpinningCoin09.gif";
		names[9] = "images/SpinningCoin10.gif";
		names[10] = "images/SpinningCoin11.gif";
		names[11] = "images/SpinningCoin12.gif";

		return names;
	}
}
