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
	private int _x;
	private int _y;
	private int _x1;
	private int _y1;
	
	@Override
	public List<Line> createLineSegments() 
	{
		_x = 0;
		_y = 500;
		_x1 = 500;
		_y1 = _y;
		
		/*
		 * We'll keep a basic flat platform for v 0.1
		 */
		List<Line> lineSegments = new ArrayList<Line>();		
		this.createHill(lineSegments, 50,200,100,300,1000);
		this.createHill(lineSegments, 100,300,300,300,200);
		this.createHill(lineSegments, 20,200,100,300,500);
		this.createHill(lineSegments, 40,200,100,300,500);
		
		return lineSegments;
	}
	
	private void createHill(List<Line> lineSegments, int hillHeight, int startLength, int uphillLength, int downhillLength, int endLength)
	{
		this.addNewLine(lineSegments); // Line 1 
		_x = _x1; _y1= _y1-hillHeight;_x1= _x1+startLength;
		this.addNewLine(lineSegments); // Line 2
		_x = _x1; _x1= _x1+uphillLength; _y = _y1;
		this.addNewLine(lineSegments); // Line 3
		_x = _x1;  _y=_y1; _y1= _y1+hillHeight;_x1= _x1+downhillLength;
		this.addNewLine(lineSegments); // Line 4
		_x = _x1;  _y=_y1;_x1= _x1+endLength;
		this.addNewLine(lineSegments); // Line 5
	}
	
	private void addNewLine(List<Line> lineSegments)
	{
		lineSegments.add(new Line(_x, _y, _x1, _y1));
	}
}