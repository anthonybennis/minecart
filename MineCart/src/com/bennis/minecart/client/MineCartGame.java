package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.Playlist;
import com.bennis.minecart.client.engine.logic.SpriteFactory;
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
}
