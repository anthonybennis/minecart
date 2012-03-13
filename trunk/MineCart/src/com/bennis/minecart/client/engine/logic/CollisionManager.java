package com.bennis.minecart.client.engine.logic;

import java.util.ArrayList;
import java.util.List;

import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.ISprite.Collision;
import com.bennis.minecart.client.engine.model.ISprite.Type;
import com.bennis.minecart.client.engine.model.Scene;

/**
 * 
 * @author abennis
 */
public class CollisionManager 
{
	/**
	 * 
	 * @param scene
	 */
	public void handleCollisions(Scene scene)
	{
		List<ISprite> sprites = this.getCollidableSprites(scene);
		Collision collision;
		/*
		 * Check every sprite against every other sprite to see if they
		 * collide.
		 */
		for (ISprite iSprite : sprites) 
		{
			for (ISprite sprite : sprites) 
			{
				if (iSprite != sprite)
				{
					collision = iSprite.getCollisionType(sprite); 
					if (collision != Collision.NONE)
					{
						iSprite.handleCollision(sprite,collision);
					}
				}
			}
		}
	}
	
	/**
	 * Only certain layers, and certain types of Sprites are
	 * capabile of colliding. This method puts them all in
	 * one List.
	 * Note: I'm returning ArrayList as opposed to List, as this
	 * is better for performance with GWT.
	 * 
	 * @param scene
	 * @return a List of all Sprites that can collide.
	 */
	private ArrayList<ISprite> getCollidableSprites(Scene scene)
	{
		ArrayList<ISprite> collidableSpritesList = new ArrayList<ISprite>();
		
		collidableSpritesList = this.addCollidableSprites(collidableSpritesList, scene.getBackLayer());
		collidableSpritesList = this.addCollidableSprites(collidableSpritesList, scene.getMiddleLayer());
		collidableSpritesList = this.addCollidableSprites(collidableSpritesList, scene.getFrontLayer());
		
		return collidableSpritesList;
	}
	
	/**
	 * Creates a sub list of sprites that can collide from source list.
	 * @param listOfSprites
	 * @return
	 */
	private ArrayList<ISprite> addCollidableSprites(ArrayList<ISprite> collidableSpritesList, List<ISprite> listOfSprites)
	{
		for (ISprite iSprite : listOfSprites) 
		{
			if (iSprite.getType() != Type.DECORATION)
			{
				collidableSpritesList.add(iSprite);
			}
		}
		
		return collidableSpritesList;
	}
}
