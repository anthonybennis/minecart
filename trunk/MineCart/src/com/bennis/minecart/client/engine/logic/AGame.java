/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client.engine.logic;

import com.bennis.minecart.client.engine.model.GamePointCounterSprite;
import com.bennis.minecart.client.engine.model.Scene;
import com.google.gwt.canvas.client.Canvas;
/**
 * This class brings the game altogther. Here you
 * create your own sprites, background and handle the
 * loop update commands.
 * 
 * @author abennis
 *
 */
public abstract class AGame
{
	private SpriteManager _spriteManager;
	private InputEvent _inputEvent;
	
	public AGame(Canvas bufferCanvas, Canvas canvas, Scene scene, Playlist playlist)
	{
		_spriteManager = this.createSpriteManager(bufferCanvas, canvas);
		this.registerCounterSprites();
		this.setSpriteFactory(this.getInitialSpriteFactory(scene));
		/*
		 * TODO AB Is this the right place for Audio?
		 * Consider moving to a LevelManager.
		 */
		Audio audio = new Audio(playlist);
		audio.playBackingTrack();
	}
	
	public SpriteManager createSpriteManager(Canvas bufferCanvas, Canvas canvas) 
	{
		return new SpriteManager(bufferCanvas, canvas);
	}
	
	public void update()
	{
		if (_inputEvent != null && _inputEvent.isConsumed())
		{
			_inputEvent = null; // We don't want to use the same event twice	
		}
		else
		{
			_spriteManager.update(_inputEvent);
			if (_inputEvent != null)
			{
				_inputEvent.consume();
			}
		}
	}
	
	public abstract SpriteFactory getInitialSpriteFactory(Scene scene);
	public abstract String getContainerName();
	
	/**
	 * This can be called a number of times. Once per level for example.
	 * Different factories can also be used depending on the difficulty setting.
	 * @param factory
	 */
	public void setSpriteFactory(SpriteFactory factory)
	{
		_spriteManager.setSpriteFactory(factory);
	}
	
	public void setInput(InputEvent event)
	{
		_inputEvent = event;
	}
	
	public ImageLoader getImageLoader()
	{
		return this._spriteManager.getSpriteFactory().getImageLoader();
	}
	
	private void registerCounterSprites()
	{
		GamePointCounterSprite[] sprites = this.createGamePointerSprites();
		
		for (GamePointCounterSprite counter:sprites) 
		{
			CounterManager.getInstance().addCounter(counter);
		}
	}
	
	abstract protected GamePointCounterSprite[] createGamePointerSprites();
}
