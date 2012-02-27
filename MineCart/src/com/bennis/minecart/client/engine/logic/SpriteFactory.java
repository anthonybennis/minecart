package com.bennis.minecart.client.engine.logic;

import com.bennis.minecart.client.engine.model.Scene;

abstract public class SpriteFactory 
{
	private Scene _scene;
	private ImageLoader _imageLoader;
	/**
	 * This class creates the Sprites. The Scene
	 * stores them for access anywhere in the game.
	 * @param scene
	 */
	public SpriteFactory(Scene scene)
	{
		_scene = scene;
		_imageLoader = new ImageLoader();
	}
	
	protected Scene getScene()
	{
		return _scene;
	}
	
	protected ImageLoader getImageLoader()
	{
		return _imageLoader;
	}
	
	/**
	 * Called every loop. The Implementation of this
	 * sub class needs to decide on every loop call whether
	 * it should create new ISprites or not.
	 */
	public abstract void update();
}
