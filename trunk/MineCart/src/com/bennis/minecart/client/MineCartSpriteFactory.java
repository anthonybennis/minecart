/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bennis.minecart.client.engine.logic.CounterManager;
import com.bennis.minecart.client.engine.logic.SpriteFactory;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.GamePointCounterSprite;
import com.bennis.minecart.client.engine.model.Platform;
import com.bennis.minecart.client.engine.model.Scene;
import com.bennis.minecart.client.engine.model.ScrollingBackground;

public class MineCartSpriteFactory extends SpriteFactory 
{
	/*
	 * This variable keeps track of which sprite to
	 * create next in the list.
	 */
	private long _updateCounter;
	
	private Map<Long, ISprite> _spriteMap; // All Sprites, in order of how they are to be shown on screen.
	
	public MineCartSpriteFactory(Scene scene)
	{
		super(scene);
		this.createInitialSprites(scene);
		_spriteMap = this.createAllInGameSprites();
	}

	@Override
	public void update() 
	{
		_updateCounter++;
		
		ISprite sprite = _spriteMap.get(_updateCounter);
		
		if (sprite != null)
		{
			if (sprite instanceof LavaSprite)
			{
				
			}
			
			/*
			 * New sprite to add on screen.
			 */
			this.getScene().storeSprite(sprite);
			/*
			 * Remove Sprite from Map
			 */
			_spriteMap.remove(sprite);
		}
		else if (_spriteMap.size() == 0)
		{
			/*
			 * TODO AB End Game!
			 * Notify Game that there are no more ISprites to create.
			 * Once all onscreen sprites are disposed, game should end or move on to the next level.
			 */
		}
	}
	
	/**
	 * Create Sprites that are needed from the very start of the game.
	 * @param scene
	 */
	private void createInitialSprites(Scene scene)
	{
		/*
		 * Background
		 */
		ScrollingBackground background = new MineCartScrollingBackground(this.getImageLoader());
		scene.storeSprite(background);
		/*
		 * Foreground
		 */
		ScrollingBackground foreGround = new MineCardScrollingForeground(this.getImageLoader());
		scene.storeSprite(foreGround);
		/*
		 * 1st Platform (Railway)
		 */
		Platform platform = new RailwayTrack();
		scene.storeSprite(platform);
		
		/*
		 * Main sprite - The Mine Cart
		 */
		MineCartSprite mineCart = new MineCartSprite(this.getImageLoader(), scene);
		scene.storeSprite(mineCart);
		
		/*
		 * Add Game counters to scene
		 * Note: These are not created in this factory as we may want to add the same counters to a number
		 * of Levels.
		 */
		ArrayList<GamePointCounterSprite> counters = CounterManager.getInstance().getAllCounters();
		for (GamePointCounterSprite gamePointCounterSprite : counters) 
		{
			scene.storeSprite(gamePointCounterSprite);
		}
	}
	
	/**
	 * Creates all sprites used in the game. Sprites are created
	 * in a specified order, i.e. as they appear in the array. This
	 * means every time the user plays the game the level will be
	 * the same.
	 * 
	 * The map created contains when (the int counter) and what
	 * will be created in the game.
	 * 
	 * @return array of all Sprites used in game.
	 */
	private Map<Long, ISprite> createAllInGameSprites()
	{
		Map<Long, ISprite> spiteMap = new HashMap<Long, ISprite>();
		
		this.addCoin(1, spiteMap, 4);
		this.addCoin(200, spiteMap, 3);
		this.addCoin(400, spiteMap, 3);
		spiteMap.put((long)50, new LavaSprite(Layers.FRONT, this.getImageLoader()));
		this.addCoin(600, spiteMap, 5);
		this.addCoin(800, spiteMap, 3);
		this.addCoin(1000, spiteMap, 3);
		this.addCoin(1200, spiteMap, 3);
		this.addCoin(1400, spiteMap,3);
		this.addCoin(1600, spiteMap,1);
		this.addCoin(1800, spiteMap,5);
		this.addCoin(2000, spiteMap,5);
		this.addCoin(2200, spiteMap,3);
		this.addCoin(2400, spiteMap,3);
		this.addCoin(2600, spiteMap,3);
		this.addCoin(2800, spiteMap,4);
		this.addCoin(3000, spiteMap,2);
		this.addCoin(3200, spiteMap,3);
		
		return spiteMap;
	}
	
	/**
	 * Creates a number of coins together.
	 * 
	 * @param creationFrame
	 * @param spiteMap
	 * @param numberOfCoins
	 */
	private void addCoin(int creationFrame, Map<Long, ISprite> spiteMap, int numberOfCoins)
	{
		long frame = (long)creationFrame;
		
		for (int i = 0; i < numberOfCoins; i++) 
		{
			spiteMap.put(frame, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
			frame = frame+20;
		}
	}
}
