package com.bennis.minecart.client.engine.model;

/**
 * A Line is made up of two points.
 * @author abennis
 */
public class Line 
{
	private int _x;
	private int _y;
	private int _x1;
	private int _y1;

	public Line()
	{
		
	}
	
	public Line(int x, int y, int x1, int y1)
	{
		_x = x;
		_y = y;
		_x1 = x1;
		_y1 = y1;
	}
	
	public void setX(int x)
	{
		_x = x;
	}
	
	public void setY(int y)
	{
		_y = y;
	}
	
	public void setX1(int x1)
	{
		_x1 = x1;
	}
	
	public void setY1(int y1)
	{
		_y1 = y1;
	}
	
	public int getX()
	{
		return _x;
	}
	
	public int getY()
	{
		return _y;
	}
	
	public int getX1()
	{
		return _x1;
	}
	
	public int getY1()
	{
		return _y1;
	}
	
}
