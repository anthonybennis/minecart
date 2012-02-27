package com.bennis.minecart.client.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent Geometric shape.
 * 
 * @author abennis
 *
 */
public class Shape 
{
	List<Vector> _Vectors = new ArrayList<Vector>();
	
	public Shape()
	{
		
	}
	
	public Shape(List<Vector> Vectors)
	{
		_Vectors = Vectors;
	}

	/**
	 * Return the Rectangular bounds of a Shape.
	 * @return
	 */
	public int[] getBounds()
	{
		// TODO AB
		int[] bounds = new int[4];
		bounds[0] = 0;
		bounds[1] = 0;
		bounds[2] = 100;
		bounds[3] = 100;
		
		return bounds;
	}
	
	/**
	 * Determines if shape is contained within this object.
	 * @param shape
	 * @return true, if shape is contained (partially and/or fully) in this shape.
	 */
	public boolean contains(Shape shape)
	{
		return false;// TODO ABS
	}
}
