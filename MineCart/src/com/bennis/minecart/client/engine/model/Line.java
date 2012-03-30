package com.bennis.minecart.client.engine.model;

/**
 * A Line is made up of two points.
 * @author abennis
 */
public class Line 
{
	private double _x;
	private double _y;
	private double _x1;
	private double _y1;

	public Line()
	{
		
	}
	
	public Line(double x, double y, double x1, double y1)
	{
		_x = x;
		_y = y;
		_x1 = x1;
		_y1 = y1;
	}
	
	public Line(int x, int y, int x1, int y1)
	{
		_x = x;
		_y = y;
		_x1 = x1;
		_y1 = y1;
	}
	
	public void setX(double x)
	{
		_x = x;
	}
	
	public void setY(double y)
	{
		_y = y;
	}
	
	public void setX1(double x1)
	{
		_x1 = x1;
	}
	
	public void setY1(double y1)
	{
		_y1 = y1;
	}
	
	public double getX()
	{
		return _x;
	}
	
	public double getY()
	{
		return _y;
	}
	
	public double getX1()
	{
		return _x1;
	}
	
	public double getY1()
	{
		return _y1;
	}
	
}
