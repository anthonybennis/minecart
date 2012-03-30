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
import java.util.List;

import com.bennis.minecart.client.engine.model.Line;
import com.bennis.minecart.client.engine.model.Platform;

/**
 * 
 * @author abennis
 */
public class RailwayTrack extends Platform 
{
	
	@Override
	public List<Line> createLineSegments() 
	{
		/*
		 * We'll keep a basic flat platform for v 0.1
		 */
		List<Line> lineSegments = new ArrayList<Line>();
		int x = 0;
		int y = 500;
		int x1 = GUIConstants.WIDTH*2;
		int y1 = y;
		lineSegments.add(new Line(x, y, x1, y1)); 
		
		
		/*
		 * Example of how you'd create a bump on the track
		 */
//		lineSegments.add(new Line(x, y, x1, y1)); // Line 1
//		x = x1; y1= y1-50;x1= x1+1000;
//		lineSegments.add(new Line(x, y, x1, y1)); // Line 2
//		x = x1; x1= x1+100; y = y1;
//		lineSegments.add(new Line(x, y, x1, y1)); // Line 3
//		x = x1;  y=y1; y1= y1+50;x1= x1+300;
//		lineSegments.add(new Line(x, y, x1, y1)); // Line 4
//		x = x1;  y=y1;x1= x1+100;
//		lineSegments.add(new Line(x, y, x1, y1)); // Line 5
		
		return lineSegments;
	}
}