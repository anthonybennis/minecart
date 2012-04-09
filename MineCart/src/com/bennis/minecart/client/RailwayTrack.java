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
import com.bennis.minecart.client.engine.model.Rectangle;

/**
 * 
 * @author abennis
 */
public class RailwayTrack extends Platform 
{

	public RailwayTrack(Rectangle screenSize)
	{
		super(screenSize);	
	}
	

	@Override
	public List<Line> createLineSegments() 
	{
		/*
		 * Start off, ceate List and first line
		 */
		List<Line> lineSegments = new ArrayList<Line>();
		Line line = new Line(0,500,500,500);
		lineSegments.add(line);
		
		/*
		 * Now create all other lines
		 */
		this.createNewLine(lineSegments,500, 500);
		this.createNewLine(lineSegments,100, 480);
		this.createNewLine(lineSegments,200, 480);
		this.createNewLine(lineSegments,500, 500);
		this.createNewLine(lineSegments,800, 450);
		this.createNewLine(lineSegments,400, 450);
		this.createNewLine(lineSegments,200, 420);
		this.createNewLine(lineSegments,300, 380);
		this.createNewLine(lineSegments,600, 500);
		this.createNewLine(lineSegments,500, 500);
		this.createNewLine(lineSegments,500, 500);
		
		return lineSegments;
	}
	
	private void createNewLine(List<Line> lineSegments, int length, int height)
	{
		Line lastLineAdded = lineSegments.get(lineSegments.size()-1);
		Line line = new Line(lastLineAdded.getX1(), lastLineAdded.getY1(),(lastLineAdded.getX1() + length), height);
		lineSegments.add(line);
	}
	
//	@Override
//	public List<Line> createLineSegments() 
//	{
//		_x = 0;
//		_y = 500;
//		_x1 = 500;
//		_y1 = _y;
//		
//		/*
//		 * We'll keep a basic flat platform for v 0.1
//		 */
//		List<Line> lineSegments = new ArrayList<Line>();		
//		this.createHill(lineSegments, 50,200,100,300,1000);
//		this.createHill(lineSegments, 100,300,300,300,800);
//		this.createHill(lineSegments, 400,90,100,300,3500);
//		this.createHill(lineSegments, 400,20,100,300,2500);
//		this.createHill(lineSegments, 550,800,500,100,1500);
//		// TODO AB - Make last segment flat so the track ends well...
//		
//		return lineSegments;
//	}
	
//	private void createHill(List<Line> lineSegments, int hillHeight, int startLength, int uphillLength, int downhillLength, int endLength)
//	{
//		this.addNewLine(lineSegments); // Line 1 
//		_x = _x1; _y1= _y1-hillHeight;_x1= _x1+startLength;
//		this.addNewLine(lineSegments); // Line 2
//		_x = _x1; _x1= _x1+uphillLength; _y = _y1;
//		this.addNewLine(lineSegments); // Line 3
//		_x = _x1;  _y=_y1; _y1= _y1+hillHeight;_x1= _x1+downhillLength;
//		this.addNewLine(lineSegments); // Line 4
//		_x = _x1;  _y=_y1;_x1= _x1+endLength;
//		this.addNewLine(lineSegments); // Line 5
//	}
//	
//	private void addNewLine(List<Line> lineSegments)
//	{
//		lineSegments.add(new Line(_x, _y, _x1, _y1));
//	}
}