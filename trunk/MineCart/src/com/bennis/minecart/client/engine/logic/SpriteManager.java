package com.bennis.minecart.client.engine.logic;

import java.util.ArrayList;
import java.util.List;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Scene;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This class delegates the creation of Sprites. 
 * Once created, they are added to a List, depending on they
 * layer. Their paint call is then called in order, depending
 * on what layer they are in.
 * 
 * @author Anthony
 *
 */
public class SpriteManager 
{
	private SpriteFactory _spriteFactory;
	private Canvas _canvas;
	private Canvas _bufferCanvas;
	private CollisionManager _collisionManager;
	/*
	 * Debug: Frame rate.
	 */
	private long _numberOfFrames;
	private long _starttime;
	private long _frameRate;
	
	/**
	 * Constructor
	 * @param back
	 * @param front
	 */
	public  SpriteManager(Canvas bufferCanvas, Canvas canvas)
	{
		_bufferCanvas = bufferCanvas;
		_canvas = canvas;
		_starttime = System.currentTimeMillis();
		_collisionManager = new CollisionManager();
	}
	
	/**
	 * This action should be used carefully. It will dispose
	 * all ISprites and start a new.
	 * @param spriteFactory
	 */
	public void setSpriteFactory(SpriteFactory spriteFactory)
	{
		this.disposeAllSprites();
		_spriteFactory = spriteFactory;
	}
	
	/**
	 * Update all Sprites, and request all Sprites to render themselves
	 * @param back
	 * @param front
	 */
	public void update(InputEvent event)
	{
		if (_spriteFactory != null)
		{
			_spriteFactory.update();
		}
		/*
		 * Clean up.
		 * Remove dead sprites.
		 */
		this.removeDisposedSpritesFromLayers();
		
		/*
		 * TODO AB If there are sprites in the scene, we continue...
		 * Or is there a different way to end game?
		 */
		
		/*
		 * UPDATE
		 */
		this.updateLayer(event, this.getScene().getBackgroundLayer());
		this.updateLayer(event,this.getScene().getBackLayer());
		this.updateLayer(event,this.getScene().getMiddleLayer());
		this.updateLayer(event,this.getScene().getFrontLayer());
		this.updateLayer(event,this.getScene().getGlassLayer());
		
		/*
		 * Handle collisions
		 */
		_collisionManager.handleCollisions(getScene());
		
		/*
		 * DRAW
		 */
		this.draw();
	}
	
	/**
	 * 
	 */
	public void draw()
	{	
		/*
		 * Clear previous
		 */
		this.clearCanvas(_bufferCanvas);
		
		/*
		 * Draw all layers
		 */
		this.drawLayer(this.getScene().getBackgroundLayer(),_bufferCanvas);
		this.drawLayer(this.getScene().getBackLayer(),_bufferCanvas);
		this.drawLayer(this.getScene().getMiddleLayer(),_bufferCanvas);
		this.drawLayer(this.getScene().getFrontLayer(),_bufferCanvas);
		this.drawLayer(this.getScene().getGlassLayer(),_bufferCanvas);
		
		/*
		 * Buffering complete. Render to canvas.
		 */
		this.renderToCanvas();
	}
	
	/**
	 * 
	 * @param canvas
	 */
	private void clearCanvas(Canvas canvas)
	{
		canvas.getContext2d().setFillStyle(GUIConstants.BLACK);
		canvas.getContext2d().fillRect(0, 0, GUIConstants.WIDTH, GUIConstants.HEIGHT);
	}
	
	/**
	 * Dispose everything
	 */
	private void disposeAllSprites()
	{
		Scene scene = this.getScene();
		
		if (scene != null)
		{
			this.disposeLayer(scene.getBackgroundLayer());
			this.disposeLayer(scene.getBackLayer());
			this.disposeLayer(scene.getMiddleLayer());
			this.disposeLayer(scene.getFrontLayer());
			this.disposeLayer(scene.getGlassLayer());
		}
	}
	
	/**
	 * Goes through each layer and removes any disposed sprites.
	 */
	private void removeDisposedSpritesFromLayers()
	{
		this.disposeSprite(this.getScene().getBackgroundLayer());
		this.disposeSprite(this.getScene().getBackLayer());
		this.disposeSprite(this.getScene().getMiddleLayer());
		this.disposeSprite(this.getScene().getFrontLayer());
		this.disposeSprite(this.getScene().getGlassLayer());
	}
	
	/**
	 * 
	 * @param layer
	 */
	private void disposeSprite(List<ISprite> layer)
	{
		List<ISprite> spritesToRemove = new ArrayList<ISprite>();
		/*
		 * Gather all ISprites to remove
		 */
		for (ISprite iSprite : layer) 
		{
			if (iSprite.isDisposed())
			{
				spritesToRemove.add(iSprite);
			}
		}
		/*
		 * Remove Sprites
		 */
		for (ISprite iSprite : spritesToRemove) 
		{
			layer.remove(iSprite);
		}
	}
	
	/**
	 * 
	 * @param event
	 * @param layer
	 */
	private void updateLayer(InputEvent event, List<ISprite> layer)
	{
		for (ISprite iSprite : layer) 
		{
			iSprite.update(event);
		}
	}
	
	/**
	 * Disposes all Sprites and empties the Layer list.
	 * @param layer
	 */
	private void disposeLayer(List<ISprite> layer)
	{
		for (ISprite iSprite : layer) 
		{
			iSprite.dispose();
		}
		
		layer.clear();
	}
	
	/**
	 * 
	 * @param layer
	 * @param bufferCanvas
	 */
	private void drawLayer(List<ISprite> layer, Canvas bufferCanvas)
	{
		for (ISprite iSprite : layer) 
		{
			iSprite.draw(bufferCanvas);
		}
	}
	
	/**
	 * We use double buffering. This is the final step in renering where the offscreen
	 * painting is move to the front context in one method.
	 * @param back
	 * @param front
	 */
	private void renderToCanvas()
	{
		Context2d frontContext = _canvas.getContext2d();
		Context2d backContext = _bufferCanvas.getContext2d();
		
		frontContext.drawImage(backContext.getCanvas(), 0, 0);
		
		/*
		 * Debug: Print Frame Rate
		 */
		frontContext.save();
		_numberOfFrames++;
		long timeRunning = System.currentTimeMillis() - _starttime;
		
		if (timeRunning > 1000)
		{
			_frameRate = timeRunning/_numberOfFrames;
			_starttime = System.currentTimeMillis(); // Reset
			_numberOfFrames = 0;
		}
		
		frontContext.setFillStyle("white");
		frontContext.fillText("Frame rate: " + _frameRate, 5, 10);
	}
	
	/**
	 * 
	 * @return
	 */
	private Scene getScene()
	{
		Scene scene = null;
		if (_spriteFactory != null)
		{
			scene = _spriteFactory.getScene();
		}
		return scene;
	}
	
	/**
	 * 
	 * @return
	 */
	public SpriteFactory getSpriteFactory()
	{
		return _spriteFactory;
	}
}
