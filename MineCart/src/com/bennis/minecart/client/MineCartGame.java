package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.Playlist;
import com.bennis.minecart.client.engine.logic.SpriteFactory;
import com.bennis.minecart.client.engine.model.GamePointCounterSprite;
import com.bennis.minecart.client.engine.model.Scene;
import com.google.gwt.canvas.client.Canvas;

/**
 * 
 * @author abennis
 *
 */
public class MineCartGame extends AGame 
{
	private String _containerName;
	private GamePointCounterSprite _livesCounter;
	private GamePointCounterSprite _scoreCounter;
	
	public MineCartGame(Canvas bufferCanvas, Canvas canvas,Scene scene, String containerName, Playlist playlist)	
	{
		super(bufferCanvas, canvas, scene, playlist);
		_containerName = containerName;
	}

	@Override
	public SpriteFactory getInitialSpriteFactory(Scene scene) 
	{
		return new MineCartSpriteFactory(scene);
	}
	
	public String getContainerName()
	{
		return _containerName;
	}

	/**
	 * Create all the counters needed
	 * @return
	 */
	@Override
	protected GamePointCounterSprite[] createGamePointerSprites() 
	{
		final int counterRow = 30;
		
		GamePointCounterSprite[] counters = new GamePointCounterSprite[2];
		_scoreCounter = new GamePointCounterSprite(GUIConstants.GAME_POINTS_COUNTER_NAME, 10, counterRow);
		_livesCounter = new GamePointCounterSprite(GUIConstants.GAME_LIVES_COUNTER_NAME, (GUIConstants.WIDTH - 100), counterRow);
		_livesCounter.incrementValue(GUIConstants.DEFAULT_NUMBER_OF_LIVES);
		counters[0] = _scoreCounter;
		counters[1] = _livesCounter;
		
		return counters;
	}
}
