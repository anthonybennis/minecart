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

import java.util.ArrayList;

import com.bennis.minecart.client.engine.model.GamePointCounterSprite;

/**
 * This Singleton manages the counters for a game.
 * Made Singleton s Sprites can update it easily without wundering how
 * to get a reference to it.
 * 
 * @author abennis
 */
public class CounterManager 
{
	private static CounterManager _instance;
	private ArrayList<GamePointCounterSprite> _counters;
	
	/**
	 * Private Constructor.
	 */
	private CounterManager()
	{
		_counters = new ArrayList<GamePointCounterSprite>();
	}
	
	public void addCounter(GamePointCounterSprite counter)
	{
		_counters.add(counter);
	}
	
	public GamePointCounterSprite getCounter(String name)
	{
		GamePointCounterSprite counter = null;
		for (GamePointCounterSprite sprite:_counters) 
		{
			if (sprite.getName() == name) // Use == as NAME should be a constant. Opimisation.
			{
				counter = sprite;
				break;
			}
		}
		
		return counter;
	}

	/**
	 * Singleton instance.
	 * @return
	 */
	public static CounterManager getInstance()
	{
		if (_instance == null)
		{
			_instance = new CounterManager();
		}
		
		return _instance;
	}
	
	public  ArrayList<GamePointCounterSprite> getAllCounters()
	{
		return _counters;
	}
}
