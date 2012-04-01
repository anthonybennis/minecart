package com.bennis.minecart.client.engine.model;

import com.google.gwt.touch.client.Point;

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
	
	/**
	 * Fine a point where two lines intersect.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	 public static Point getLineLineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
	      double det1And2 = det(x1, y1, x2, y2);
	      double det3And4 = det(x3, y3, x4, y4);
	      double x1LessX2 = x1 - x2;
	      double y1LessY2 = y1 - y2;
	      double x3LessX4 = x3 - x4;
	      double y3LessY4 = y3 - y4;
	      double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
	      
	      if (det1Less2And3Less4 == 0)
	      {
	         // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
	         return null;
	      }
	      
	      double x = (det(det1And2, x1LessX2,
	            det3And4, x3LessX4) /
	            det1Less2And3Less4);
	      
	      double y = (det(det1And2, y1LessY2,
	            det3And4, y3LessY4) /
	            det1Less2And3Less4);
	      
	      return new Point(x, y);
	   }
	 
	 /**
	  * DET function for Line Intersection.
	  * 
	  * @param a
	  * @param b
	  * @param c
	  * @param d
	  * @return
	  */
	   protected static double det(double a, double b, double c, double d) 
	   {
	      return a * d - b * c;
	   }
}
