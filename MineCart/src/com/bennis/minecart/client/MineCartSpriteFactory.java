package com.bennis.minecart.client;

import java.util.HashMap;
import java.util.Map;

import com.bennis.minecart.client.engine.logic.SpriteFactory;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
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
			 * TODO AB
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
		ScrollingBackground background = new MineCartScrollingBackground(this.getImageLoader());
		scene.storeSprite(background);
		// TODO AB - Create MineCart sprite
		// TODO AB - Create Game Score Sprite 
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
		
		
		spiteMap.put((long)1, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
		spiteMap.put((long)150, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
		spiteMap.put((long)160, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
		spiteMap.put((long)161, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
		spiteMap.put((long)200, new CoinSprite(Layers.MIDDLE, this.getImageLoader()));
		
		// TODO AB - Create Scrolling Sprite.
		// TODO AB - Ensure it's initial location is set.
		
		return spiteMap;
	}
}
