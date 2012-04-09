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

import java.util.List;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.Line;
import com.bennis.minecart.client.engine.model.Platform;
import com.bennis.minecart.client.engine.model.Scene;
import com.bennis.minecart.client.engine.model.Vector;
import com.google.gwt.touch.client.Point;

/**
 * A collection of utility methods for
 * working with Platforms Sprites in a Scene.
 * 
 * @author abennis
 *
 */
public class PlatformUtility 
{
	/**
	 * THIS IS COMMENTED OUT, AS MINE CART ONLY HAS ONE PLATFORM,
	 * SO THERE IS A DUPLICATE METHOD BELOW.
	 * 
	 * Returns the nearest Platform Sprite for
	 * a given co-ordinate.
	 * 
	 * Platform will be null, if no platform is found on the point,
	 * or directly below or above it.
	 * 
	 * Note: Platforms are in the Middle layer by default.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
//	public static Platform getNearestPlatform(Scene scene, double x, double y, Layers layer)
//	{
//		Platform platform = null;
//		
//		List<ISprite> spritesList = scene.getLayer(layer);
//		Platform tempPlatform;
//		FIND_PLATFORM:
//		for (ISprite iSprite : spritesList) 
//		{
//			if (iSprite.getType() == ISprite.Type.PLATFORM)
//			{	
//				tempPlatform = (Platform)iSprite;
//				Rectangle platformBounds = tempPlatform.getBounds();
//				/*
//				 * Check if point intersects a Platform
//				 */
//				if (platformBounds.contains(x, y))
//				{
//					platform = tempPlatform;
//					break FIND_PLATFORM;
//				}
//				/*
//				 * Get platform directly under or over the current position.
//				 * Current position is in the same vertical space as the platform?
//				 */
//				if (x >= platformBounds.getX() && (x <= (platformBounds.getX() + platformBounds.getWidth())))
//				{
//					/*
//					 * TODO AB - Optimise. There may be multiple Platforms that pass this test. One below, 
//					 * or one above, for example. We want to get the nearest platform.
//					 */
//					platform = tempPlatform;
//					break FIND_PLATFORM;
//				}

//			}
//		}
//		
//		return platform;
//	}
	
	public static Platform getNearestPlatform(Scene scene, double x, double y, Layers layer)
	{
		Platform platform = null;	
		List<ISprite> spritesList = scene.getLayer(layer);

		for (ISprite iSprite : spritesList) 
		{
			if (iSprite.getType() == ISprite.Type.PLATFORM)
			{
				platform = (Platform)iSprite;
				break;
			}
		}
		
		return platform;
	}
	
	/**
	 * 
	 * @param vector
	 * @param platform
	 * @return
	 */
	public static Vector alignVectorToNearestPlatform(Scene sene, Vector vector)
	{
		return PlatformUtility.alignVectorToNearestPlatform(sene, vector.x, vector.y);
	}
	
	public static Vector alignVectorToNearestPlatform(Scene sene, double x, double y)
	{
		Vector aligendVector = null;
		
		Platform endPositionPlatform = PlatformUtility.getNearestPlatform(sene, x, y, Layers.MIDDLE);
		if (endPositionPlatform != null)
		{
			aligendVector = PlatformUtility.alignVectorToPlatform(x, y, endPositionPlatform);
		}
		
		return aligendVector;
	}
	
	/**
	 * 
	 * @param vector
	 * @param platform
	 * @return
	 */
	public static Vector alignVectorToPlatform(Vector vector, Platform platform)
	{
		return PlatformUtility.alignVectorToPlatform(vector.x, vector.y, platform);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param platform
	 * @return
	 */
	public static Vector alignVectorToPlatform(double x, double y, Platform platform)
	{
		Vector aligendVector = new Vector(x, y);

		Line verticalLineFromPoint = new Line(x,0,x,GUIConstants.HEIGHT);
		Point alignedPoint =  platform.getIntersectionPointOfVerticalLine(verticalLineFromPoint);
		aligendVector.y = alignedPoint.getY();
		return aligendVector;
	}
	
	public boolean doesPointIntersectWithPlatform(double x, double y)
	{
		boolean lineIntersects = false;
		
		// TODO AB
		
		return lineIntersects;
		
	}
	

}
