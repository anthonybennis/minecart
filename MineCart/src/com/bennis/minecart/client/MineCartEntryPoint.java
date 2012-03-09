package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.Playlist;
import com.bennis.minecart.client.engine.model.Scene;
import com.bennis.minecart.client.engine.ui.AGameEntryPoint;
import com.google.gwt.canvas.client.Canvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MineCartEntryPoint extends AGameEntryPoint
{
	@Override
	public AGame createGame(Canvas bufferCanvas, Canvas canvas, Scene scene) 
	{
		Playlist playList = new MineCartPlayList();
		AGame game = new MineCartGame(bufferCanvas, canvas, scene, this.getHTMLContainerName(),playList);
		return game;
	}
	
	public String getHTMLContainerName()
	{
		return GUIConstants.CONTAINER_NAME;
	}
	
	

	@Override
	public int[] getCanvasSize() 
	{
		int[] size = new int[2];
		size[0] = GUIConstants.WIDTH;
		size[1] = GUIConstants.HEIGHT;
		
		return size;
	}

	@Override
	public String getSplashScreenContainerName() 
	{
		return "loading";
	}

	@Override
	public void createPanels(AGame game) 
	{
		new ButtonPanel(game);
	}
}