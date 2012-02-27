package com.bennis.minecart.client.engine.logic;

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
	
	public AGame(Canvas bufferCanvas, Canvas canvas, Scene scene)
	{
		_spriteManager = this.createSpriteManager(bufferCanvas, canvas);
		this.setSpriteFactory(this.getInitialSpriteFactory(scene));
	}
	
	public SpriteManager createSpriteManager(Canvas bufferCanvas, Canvas canvas) 
	{
		return new SpriteManager(bufferCanvas, canvas);
	}
	
	public void update()
	{
		_spriteManager.update();
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
}
